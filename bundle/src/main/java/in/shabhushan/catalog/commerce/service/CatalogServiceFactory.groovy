package in.shabhushan.catalog.commerce.service;

import com.adobe.cq.commerce.api.CommerceService;
import com.adobe.cq.commerce.api.CommerceServiceFactory;
import com.adobe.cq.commerce.common.AbstractJcrCommerceServiceFactory
import groovy.util.logging.Slf4j;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;

/**
 * Created by Shashi Bhushan
 *      on 13/3/17.
 */
@Component(metatype = true)
@Service
    @Property(name = "service.description", value = "Factory for reference implementation commerce service")
    @Property(name = "commerceProvider", value = "catalog", propertyPrivate = true)
@Slf4j
public class CatalogServiceFactory extends AbstractJcrCommerceServiceFactory implements CommerceServiceFactory {
    @Override
    public CommerceService getCommerceService(Resource resource) {
        return new CatalogServiceImpl(getServiceContext(), resource);
    }
}
