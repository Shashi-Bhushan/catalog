package in.shabhushan.catalog.servlet

import in.shabhushan.catalog.services.impl.PersonConfigurationImpl
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Property
import org.apache.felix.scr.annotations.Properties
import org.apache.felix.scr.annotations.Reference
import org.apache.felix.scr.annotations.Service
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.SlingHttpServletResponse
import org.apache.sling.api.servlets.SlingSafeMethodsServlet
import org.apache.sling.commons.json.JSONObject
import org.osgi.service.cm.Configuration
import org.osgi.service.cm.ConfigurationAdmin

import javax.servlet.Servlet
import javax.servlet.ServletException

/**
 * @author Shashi Bhushan
 *
 * Change History:
 * Revision         Date            Dev             Comment
 * ----------------------------------------------------------------------------------------
 * 1.0              Nov 11, 2017    Shashi          Created ConfigAdmin Servlet
 */
@Component(
    label = "Configuration Admin Servlet",
    description = "Fetched Configuration using Configuration Admin Service",
    metatype = false,
    enabled = true,
    immediate = true
)
@Service(
    value= Servlet.class
)
@Properties([
    @Property(name = "sling.servlet.paths", value = [
            "/bin/servlet/configadmin"
    ], propertyPrivate = true)
])
class ConfigAdminServlet extends SlingSafeMethodsServlet {

    @Reference
    private ConfigurationAdmin configurationAdmin

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
        throws ServletException, IOException {

        Configuration config = getConfig(PersonConfigurationImpl.class.name)

        Dictionary properties = config.properties

        // String.valueOf will also work
        String name             = (String) properties.get(PersonConfigurationImpl.STRING_CONFIG_KEY)
        String gender           = properties.get(PersonConfigurationImpl.DROPDOWN_CONFIG_KEY) as String
        String[] phoneNumbers   = properties.get(PersonConfigurationImpl.MULTIFIELD_CONFIG_KEY) as String[]
        int noOfChildren        = properties.get(PersonConfigurationImpl.NUMERIC_CONFIG_KEY) as int
        boolean isMarried       = properties.get(PersonConfigurationImpl.BOOLEAN_CONFIG_KEY) as boolean

        JSONObject responseObject = new JSONObject()

        responseObject.put("Class", this.class.name)
        responseObject.put(PersonConfigurationImpl.STRING_CONFIG_KEY, name)
        responseObject.put(PersonConfigurationImpl.DROPDOWN_CONFIG_KEY, gender)
        responseObject.put(PersonConfigurationImpl.MULTIFIELD_CONFIG_KEY, Arrays.toString(phoneNumbers))
        responseObject.put(PersonConfigurationImpl.NUMERIC_CONFIG_KEY, noOfChildren)
        responseObject.put(PersonConfigurationImpl.BOOLEAN_CONFIG_KEY, isMarried)

        response.setContentType("application/json")
        response.setCharacterEncoding("UTF-8")
        response.writer.write(responseObject.toString())
    }

    private Configuration getConfig(String key) {
        configurationAdmin.getConfiguration(key)
    }
}
