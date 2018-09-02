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
import com.day.cq.commons.inherit.ComponentInheritanceValueMap
import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap
import com.day.cq.commons.inherit.InheritanceValueMap
import com.day.cq.commons.jcr.JcrConstants
import com.day.cq.tagging.Tag
import com.day.cq.wcm.api.Page
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import in.shabhushan.catalog.commerce.framework.provider.product.CatalogCommerceProductImpl
import in.shabhushan.catalog.commerce.framework.provider.session.CatalogCommerceSessionImpl
import in.shabhushan.catalog.constants.CommonConstants
import org.apache.commons.collections.Predicate
import org.apache.jackrabbit.commons.JcrUtils
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.SlingHttpServletResponse
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.ResourceUtil

import javax.jcr.Node
import javax.jcr.RepositoryException

/**
 * Created by Shashi Bhushan
 *      on 13/3/17.
 */
@CompileStatic
@Slf4j
class CatalogCommerceServiceImpl extends AbstractJcrCommerceService implements CommerceService {

    private static final String REQUEST_ATTRIBUTE_NAME = CatalogCommerceServiceImpl.class.getName();

    public static final String VARIATION_AXIS = "variationAxis"

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

    /**
     * Set {@link CommonConstants#PROPERTY_HYBRIS_BASE_STORE} etc. at /content/catalog/en page
     *
     * FROM WHERE GET THESE
     * From Blueprint Page(/content/catalogs/catalog/en/base-catalog), get /var/commerce/products/catalog(Stored at filter/basePath)
     *  Get the properties from /var/commerce/products/catalog and set on /content/catalog/en
     * @param blueprint
     * @param catalog
     */
    @Override
    public void catalogRolloutHook(Page blueprint, Page catalog) {
        log.info "catalogRolloutHook initiated with $blueprint.path and $catalog.path"
        Resource blueprintResource = blueprint.getContentResource();
        InheritanceValueMap blueprintProps = new HierarchyNodeInheritanceValueMap(blueprintResource);
        String basePath = blueprintProps.getInherited("filter/basePath", "/var/commerce/products");

        try {
            Resource productDataRoot = blueprintResource.getResourceResolver().getResource(basePath);
            if (productDataRoot != null) {
                InheritanceValueMap productDataProps = new ComponentInheritanceValueMap(productDataRoot);
                Node catalogNode = catalog.getContentResource().adaptTo(Node.class);
                catalogNode.setProperty(CommonConstants.PROPERTY_HYBRIS_BASE_STORE,
                    productDataProps.getInherited(CommonConstants.PROPERTY_HYBRIS_BASE_STORE, ""));
                catalogNode.setProperty(CommonConstants.PROPERTY_HYBRIS_CATALOG_ID,
                    productDataProps.getInherited(CommonConstants.PROPERTY_HYBRIS_CATALOG_ID, ""));
            }
        } catch (RepositoryException e) {
            throw new CommerceException("Catalog rollout hook failed: ", e);
        }
        log.info "catalogRolloutHook initiated with $blueprint.path and $catalog.path"
    }

    @Override
    public void sectionRolloutHook(Page blueprint, Page section) {
        // NOP
    }

    @Override
    void productRolloutHook(Product productData, Page productPage, Product productReference) throws CommerceException {
        log.info "productRolloutHook initiated with $productData.path, $productPage.path and $productReference.path"
        Node productNode = productReference.adaptTo(Node.class);
        if (productData.axisIsVariant("color")) {
            if (!productNode.hasProperty(VARIATION_AXIS)) {
                productNode.setProperty(VARIATION_AXIS, "color");
                productNode.setProperty("variationTitle", "Color");
            }
        } else {
            if (productNode.hasProperty(VARIATION_AXIS) && productNode.getProperty(VARIATION_AXIS).getString().equals("color")) {
                productNode.setProperty(VARIATION_AXIS, "");
                productNode.setProperty("variationTitle", "");
            }
        }

        log.info "productRolloutHook finished"
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
