package in.shabhushan.catalog.servlet

import in.shabhushan.catalog.services.TestService
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Properties
import org.apache.felix.scr.annotations.Property
import org.apache.felix.scr.annotations.Reference
import org.apache.felix.scr.annotations.Service
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.SlingHttpServletResponse
import org.apache.sling.api.servlets.SlingSafeMethodsServlet

import javax.servlet.Servlet
import javax.servlet.ServletException

/**
 * @author Shashi Bhushan
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
        "/bin/test"
    ], propertyPrivate = true)
])
class TestServlet extends SlingSafeMethodsServlet {

    @Reference
    private TestService testService

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json")
        response.setCharacterEncoding("UTF-8")

        response.getWriter().println("Implementation Class for Test Service is $testService.name")
    }
}
