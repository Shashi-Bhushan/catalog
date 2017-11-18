package in.shabhushan.catalog.services

import org.apache.felix.scr.annotations.Activate
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Modified
import org.apache.felix.scr.annotations.Properties
import org.apache.felix.scr.annotations.Property
import org.osgi.service.component.ComponentContext

/**
 * @author Shashi Bhushan
 *
 * Change History:
 * Revision         Date            Dev             Comment
 * ----------------------------------------------------------------------------------------
 *
 */
@Component(
    label = "Configuration Factory",
    immediate = true,
    metatype = true,
    enabled = true,
    description = "Sample Configuration Factory",
    configurationFactory = true
)
@Properties([
        @Property(
            name = SampleConfigurationFactory.CONFIGURATION_NAME,
            label = "Sample Config",
            description = "Sample Configuration for Factory"
        )
])
class SampleConfigurationFactory {

    public static final String CONFIGURATION_NAME = "config.name"

    String config

    @Activate
    @Modified
    void activate(final ComponentContext componentContext) {
        config = componentContext.properties.get(CONFIGURATION_NAME)
    }
}
