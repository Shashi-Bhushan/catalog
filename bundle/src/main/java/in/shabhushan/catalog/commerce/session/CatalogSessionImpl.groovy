package in.shabhushan.catalog.commerce.session;

import com.adobe.cq.commerce.api.CommerceException;
import com.adobe.cq.commerce.common.AbstractJcrCommerceService;
import com.adobe.cq.commerce.common.AbstractJcrCommerceSession;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Shashi Bhushan
 *      on 13/3/17.
 */
public class CatalogSessionImpl extends AbstractJcrCommerceSession {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogSessionImpl.class);

    public CatalogSessionImpl(AbstractJcrCommerceService commerceService, SlingHttpServletRequest request,
                              SlingHttpServletResponse response, Resource resource) throws CommerceException {
        super(commerceService, request, response, resource);
    }
}
