package in.shabhushan.catalog.servlet

import in.shabhushan.catalog.services.SampleConfigurationFactory
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Properties
import org.apache.felix.scr.annotations.Property
import org.apache.felix.scr.annotations.ReferenceCardinality
import org.apache.felix.scr.annotations.ReferencePolicy
import org.apache.felix.scr.annotations.Service
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.SlingHttpServletResponse
import org.apache.sling.api.servlets.SlingSafeMethodsServlet
import org.apache.felix.scr.annotations.Reference
import org.apache.sling.commons.json.JSONObject
import org.osgi.service.cm.Configuration
import org.osgi.service.cm.ConfigurationAdmin
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.servlet.Servlet
import javax.servlet.ServletException

/**
 * @author Shashi Bhushan
 *
 * Change History:
 * Revision         Date            Dev             Comment
 * ----------------------------------------------------------------------------------------
 * 1.0              Oct 25, 2017    Shashi          Added Config Factory Consumer
 */
@Component(
    label = "Config Factory Consumer Servlet",
    description = "Fetched Configuration values from Factory",
    metatype = false,
    enabled = true,
    immediate = true
)
@Service(
    value= Servlet.class
)
@Properties([
    @Property(name = "sling.servlet.paths", value = [
        "/bin/servlet/configfactory"
    ], propertyPrivate = true)
])
class ConfigFactoryConsumerServlet extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigFactoryConsumerServlet.class);

    @Reference
    private ConfigurationAdmin configurationAdmin

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
        throws ServletException, IOException {
        JSONObject responseObject = new JSONObject()

        responseObject.put("Class", this.class.name)
        responseObject.put(SampleConfigurationFactory.CONFIGURATION_NAME, getConfigurationList(SampleConfigurationFactory.class.name))

        response.setContentType("application/json")
        response.setCharacterEncoding("UTF-8")
        response.writer.write(responseObject.toString())
    }

    private String getConfigurationList(String key) {
        List<String> configValues = []

        Configuration[] configurations = configurationAdmin.listConfigurations(
            "(" + ConfigurationAdmin.SERVICE_FACTORYPID + "="
            + key + ")"
        )

        for(Configuration configuration : configurations) {
            configValues.push(configuration.properties.get(SampleConfigurationFactory.CONFIGURATION_NAME).toString())
        }

        return configValues.join(",")
    }
}
