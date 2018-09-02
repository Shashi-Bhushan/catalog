package in.shabhushan.catalog.services.impl;

import in.shabhushan.catalog.services.TestService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;

/**
 * @author Shashi Bhushan
 */
@Component(
    label = "Catalog Create Product Node Service",
    description = "Creates Product Nodes by importing books from Goodreads",
    immediate = true,
    metatype = true,
    enabled = true
)
@Service(TestService.class)
@Properties({
    @Property(name = Constants.SERVICE_RANKING, intValue = 20)
})
public class TestServiceImplTwo implements TestService {
    @Override
    public String getName() {
        return this.getClass().getName();
    }
}

