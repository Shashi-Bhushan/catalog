package in.shabhushan.catalog.servlet

import in.shabhushan.catalog.services.PersonConfiguration
import in.shabhushan.catalog.services.impl.PersonConfigurationImpl
import org.apache.felix.scr.annotations.*
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.SlingHttpServletResponse
import org.apache.sling.api.servlets.SlingSafeMethodsServlet
import org.apache.sling.commons.json.JSONObject

import javax.servlet.Servlet
import javax.servlet.ServletException

/**
 * @author Shashi Bhushan
 *
 * Change History:
 * Revision         Date            Dev             Comment
 * ----------------------------------------------------------------------------------------
 * 1.0              Nov 11, 2017    Shashi          Created OSGi Service Servlet
 */
@Component(
    label = "OSGi Service Servlet",
    description = "Fetched Configuration using OSGi Service",
    metatype = false,
    enabled = true,
    immediate = true
)
@Service(
    value= Servlet.class
)
@Properties([
    @Property(name = "sling.servlet.paths", value = [
            "/bin/servlet/osgiservice"
    ], propertyPrivate = true)
])
class OsgiServiceServlet extends SlingSafeMethodsServlet {

    @Reference
    private PersonConfiguration personConfig

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
        throws ServletException, IOException {

        // String.valueOf will also work
        String name             = personConfig.stringConfig
        String gender           = personConfig.dropDownConfig
        String[] phoneNumbers   = personConfig.multifieldConfig
        int noOfChildren        = personConfig.numericConfig
        boolean isMarried       = personConfig.booleanConfig

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
}
