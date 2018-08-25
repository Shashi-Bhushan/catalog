package in.shabhushan.catalog.services.impl

import in.shabhushan.catalog.services.ResourceResolverService
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Reference
import org.apache.felix.scr.annotations.Service
import org.apache.sling.api.resource.ResourceResolver
import org.apache.sling.api.resource.ResourceResolverFactory

/**
 * @author Shashi Bhushan
 */
@Component(
    label = "Resource Resolver Service",
    immediate = true,
    metatype = false,
    enabled = true
)
@Service(ResourceResolverService.class)
class ResourceResolverServiceImpl implements ResourceResolverService {

    @Reference
    ResourceResolverFactory resourceResolverFactory

    /**
     * Get Service Resource Resolver associated with 'signupService'
     *
     * @return
     *      {@link ResourceResolver} of user associated with 'signupService'
     */
    @Override
    ResourceResolver getResourceResolver() {
        // Get Service Resource Resolver
        Map<String, Object> param = ["sling.service.subservice":"signupService"]

        return resourceResolverFactory.getServiceResourceResolver(param)
    }
}
