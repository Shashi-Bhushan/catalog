package in.shabhushan.catalog.commerce.service

import com.adobe.cq.commerce.api.*
import com.adobe.cq.commerce.common.AbstractJcrCommerceService
import com.adobe.cq.commerce.common.AbstractJcrProduct
import com.adobe.cq.commerce.common.ServiceContext
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import in.shabhushan.catalog.commerce.product.CatalogProductImpl
import in.shabhushan.catalog.commerce.session.CatalogSessionImpl
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.SlingHttpServletResponse
import org.apache.sling.api.resource.Resource

/**
 * Created by Shashi Bhushan
 *      on 13/3/17.
 */
@CompileStatic
@Slf4j
class CatalogServiceImpl extends AbstractJcrCommerceService implements CommerceService {

    private Resource resource

    protected CatalogServiceImpl(ServiceContext serviceContext, Resource resource) {
        super(serviceContext, resource)
        this.resource = resource
    }

    @Override
    public CommerceSession login(SlingHttpServletRequest slingHttpServletRequest, SlingHttpServletResponse slingHttpServletResponse) throws CommerceException {
        return new CatalogSessionImpl(this, slingHttpServletRequest, slingHttpServletResponse, resource)
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
    public Product getProduct(String path) throws CommerceException {
        Resource resource = resolver.getResource(path)
        if (resource != null
            && CatalogProductImpl.isAProductOrVariant(resource)
            && resource.isResourceType(AbstractJcrProduct.RESOURCE_TYPE_PRODUCT)) {

            return new CatalogProductImpl(resource)
        }
        return null
    }

    @Override
    public List<String> getCountries() throws CommerceException {
        return null
    }

    @Override
    public List<String> getCreditCardTypes() throws CommerceException {
        return null
    }

    @Override
    public List<String> getOrderPredicates() throws CommerceException {
        return null
    }
}
