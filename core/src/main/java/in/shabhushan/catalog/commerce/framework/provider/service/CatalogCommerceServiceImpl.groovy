/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package in.shabhushan.catalog.commerce.framework.provider.service

import com.adobe.cq.commerce.api.*
import com.adobe.cq.commerce.api.promotion.Voucher
import com.adobe.cq.commerce.common.AbstractJcrCommerceService
import com.adobe.cq.commerce.common.CommerceHelper
import com.adobe.cq.commerce.common.ServiceContext
import com.adobe.cq.commerce.common.promotion.AbstractJcrVoucher
import com.day.cq.commons.jcr.JcrConstants
import com.day.cq.tagging.Tag
import com.day.cq.wcm.api.Page
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import in.shabhushan.catalog.commerce.framework.provider.product.CatalogCommerceProductImpl
import in.shabhushan.catalog.commerce.framework.provider.session.CatalogCommerceSessionImpl
import org.apache.commons.collections.Predicate
import org.apache.jackrabbit.commons.JcrUtils
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.SlingHttpServletResponse
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.ResourceUtil

import javax.jcr.Node

/**
 * Created by Shashi Bhushan
 *      on 13/3/17.
 */
@CompileStatic
@Slf4j
class CatalogCommerceServiceImpl extends AbstractJcrCommerceService implements CommerceService {

    private static final String REQUEST_ATTRIBUTE_NAME = CatalogCommerceServiceImpl.class.getName();

    private Resource resource

    protected CatalogCommerceServiceImpl(ServiceContext serviceContext, Resource resource) {
        super(serviceContext, resource)
        this.resource = resource
    }

    @Override
    CommerceSession login(SlingHttpServletRequest slingHttpServletRequest, SlingHttpServletResponse slingHttpServletResponse) throws CommerceException {
        // This avoids that the session is instantiated multiple times by multiple components for the same request
        Object session = slingHttpServletRequest.getAttribute(REQUEST_ATTRIBUTE_NAME);
        if (session != null) {
            return (CommerceSession) session;
        }

        CommerceSession commerceSession = new CatalogCommerceSessionImpl(this, slingHttpServletRequest, slingHttpServletResponse, resource)
        slingHttpServletRequest.setAttribute(REQUEST_ATTRIBUTE_NAME, commerceSession)

        return commerceSession
    }

    /**
     * Checks if Commerce Provider Service is available or not
     *
     * @param serviceType
     *      Criteria to search for i.e. {@code serviceType} equals to {@link CommerceConstants#SERVICE_COMMERCE}
     * @return
     *      True if Commerce Provider is available according to the criteria
     */
    @Override
    boolean isAvailable(String serviceType) {
        CommerceConstants.SERVICE_COMMERCE == serviceType
    }

    @Override
    Product getProduct(String path) throws CommerceException {
        Resource resource = resolver.getResource(path)
        if (resource != null
            && CatalogCommerceProductImpl.isAProductOrVariant(resource)) {

            return new CatalogCommerceProductImpl(resource)
        }
    }

    @Override
    public Voucher getVoucher(final String path) throws CommerceException {
        Resource resource = resolver.getResource(path);
        if (resource != null) {
            // JCR-based vouchers are cq:Pages
            Resource contentResource = resource.getChild(JcrConstants.JCR_CONTENT);
            if (contentResource != null && contentResource.isResourceType(AbstractJcrVoucher.VOUCHER_RESOURCE_TYPE)) {
                return new AbstractJcrVoucher(resource);
            }
        }
        return null;
    }

    @Override
    public void catalogRolloutHook(Page blueprint, Page catalog) {
        // NOP
    }

    @Override
    public void sectionRolloutHook(Page blueprint, Page section) {
        // NOP
    }

    @Override
    public void productRolloutHook(Product productData, Page productPage, Product product) throws CommerceException {
        try {
            boolean changed = false;

            //
            // The out-of-the-box commerce components (such as commerce/components/product) support
            // two variant axes: "size", plus one (optional) user-defined axis.
            // The user-defined axis, if required, is specified using the "variationAxis" and
            // "variationTitle" properties.
            //
            // In the sample product set, the optional axis is always "color".
            //
            Node productNode = product.adaptTo(Node.class);
            if (productData.axisIsVariant("color")) {
                if (!productNode.hasProperty("variationAxis")) {
                    productNode.setProperty("variationAxis", "color");
                    productNode.setProperty("variationTitle", "Color");
                    changed = true;
                }
            } else {
                if (productNode.hasProperty("variationAxis") && productNode.getProperty("variationAxis").getString().equals("color")) {
                    productNode.setProperty("variationAxis", "");
                    productNode.setProperty("variationTitle", "");
                    changed = true;
                }
            }

            //
            // Copy we-retail namespaced tags from the product to the product page.
            //
            if (CommerceHelper.copyTags(productData, productPage.getContentResource(),
                new Predicate() {
                    public boolean evaluate(Object o) {
                        return ((Tag) o).getNamespace().getName().equals("we-retail");
                    }
                })) {
                changed = true;
            }

            //
            // Give product pages a product-specific thumbnail so they don't have to fall back to
            // the (generic) page_product template's thumbnail.  This greatly improves the usability
            // of the pages content finder tab.
            //
            if (!ResourceUtil.isA(productPage.getContentResource(), CommerceConstants.RT_PRODUCT_PAGE_PROXY)) {
                String productImageRef = "";
                Resource productImage = productData.getImage();
                if (productImage != null) {
                    productImageRef = ResourceUtil.getValueMap(productImage).get("fileReference", "");
                }
                Node contentNode = productPage.getContentResource().adaptTo(Node.class);
                Node pageImageNode = JcrUtils.getOrAddNode(contentNode, "image", "nt:unstructured");
                pageImageNode.setProperty("fileReference", productImageRef);
            }

            if (changed) {
                productPage.getPageManager().touch(productPage.adaptTo(Node.class), true, Calendar.getInstance(), false);
            }
        } catch(Exception e) {
            throw new CommerceException("Product rollout hook failed: ", e);
        }
    }

    @Override
    public List<ShippingMethod> getAvailableShippingMethods() throws CommerceException {
        return enumerateMethods("/var/commerce/shipping-methods/we-retail", ShippingMethod.class);
    }

    @Override
    public List<PaymentMethod> getAvailablePaymentMethods() throws CommerceException {
        return enumerateMethods("/var/commerce/payment-methods/we-retail", PaymentMethod.class);
    }

    @Override
    public List<String> getCountries() throws CommerceException {
        List<String> countries = new ArrayList<String>();

        // A true implementation would likely need to check with its payment processing and/or
        // fulfillment services to determine what countries to accept.  This implementation
        // simply accepts them all.
        countries.add("*");

        return countries;
    }

    @Override
    public List<String> getCreditCardTypes() throws CommerceException {
        List<String> ccTypes = new ArrayList<String>();

        // A true implementation would likely need to check with its payment processing
        // service to determine what credit cards to accept.  This implementation simply
        // accepts them all.
        ccTypes.add("*");

        return ccTypes;
    }

    @Override
    public List<String> getOrderPredicates() throws CommerceException {
        List<String> predicates = new ArrayList<String>();
        predicates.add(CommerceConstants.OPEN_ORDERS_PREDICATE);
        return predicates;
    }
}
