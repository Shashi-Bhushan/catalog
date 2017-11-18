package in.shabhushan.catalog.servlet

import in.shabhushan.catalog.services.SampleConfigurationFactory
import org.apache.felix.scr.annotations.ReferenceCardinality
import org.apache.felix.scr.annotations.ReferencePolicy
import org.apache.sling.api.servlets.SlingSafeMethodsServlet
import org.apache.felix.scr.annotations.Reference
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author Shashi Bhushan
 *
 * Change History:
 * Revision         Date            Dev             Comment
 * ----------------------------------------------------------------------------------------
 *
 */
class ConfigFactoryConsumerServlet extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigFactoryConsumerServlet.class);
    private List<String> configurationList
/**
 * Executed on Configuration Add event
 * @param config New configuration for factory
 */
    @Reference(name = "configurationFactory", cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    private SampleConfigurationFactory sampleConfigurationFactory

    protected synchronized void bindSampleConfigurationFactory(final SampleConfigurationFactory configurationFactory) {
        LOGGER.info "bindConfigurationFactory: "
        if (configurationList == null) {
            configurationList = []
        }
        configurationList.add(configurationFactory.config)
    }
    /**
     * Executed on Configuration Remove event
     * @param config New configuration for factory
     */
    protected synchronized void unbindConfigurationFactory(final SampleConfigurationFactory configurationFactory) {
        LOGGER.info "unbindConfigurationFactory: "
        configurationList.remove(configurationFactory.config)
    }
}
