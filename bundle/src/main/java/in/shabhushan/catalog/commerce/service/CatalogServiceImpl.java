package in.shabhushan.catalog.commerce.service;

import com.adobe.cq.commerce.api.CommerceException;
import com.adobe.cq.commerce.api.CommerceService;
import com.adobe.cq.commerce.api.CommerceSession;
import com.adobe.cq.commerce.api.Product;
import com.adobe.cq.commerce.common.AbstractJcrCommerceService;
import com.adobe.cq.commerce.common.ServiceContext;
import in.shabhushan.catalog.commerce.product.CatalogProductImpl;
import in.shabhushan.catalog.commerce.session.CatalogSessionImpl;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;

import java.util.List;

/**
 * Created by Shashi Bhushan
 *      on 13/3/17.
 */
public class CatalogServiceImpl extends AbstractJcrCommerceService implements CommerceService {

    private Resource resource;

    protected CatalogServiceImpl(ServiceContext serviceContext, Resource resource) {
        super(serviceContext, resource);
        this.resource = resource;
    }

    @Override
    public CommerceSession login(SlingHttpServletRequest slingHttpServletRequest, SlingHttpServletResponse slingHttpServletResponse) throws CommerceException {
        return new CatalogSessionImpl(this, slingHttpServletRequest, slingHttpServletResponse, resource);
    }

    @Override
    public boolean isAvailable(String s) {
        return false;
    }

    @Override
    public Product getProduct(String path) throws CommerceException {
        Resource resource = resolver.getResource(path);
        if (resource != null && CatalogProductImpl.isAProductOrVariant(resource)) {
            return new CatalogProductImpl(resource);
        }
        return null;
    }

    @Override
    public List<String> getCountries() throws CommerceException {
        return null;
    }

    @Override
    public List<String> getCreditCardTypes() throws CommerceException {
        return null;
    }

    @Override
    public List<String> getOrderPredicates() throws CommerceException {
        return null;
    }
}
