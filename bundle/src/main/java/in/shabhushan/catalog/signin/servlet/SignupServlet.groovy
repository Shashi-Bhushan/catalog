package in.shabhushan.catalog.signin.servlet

import groovy.util.logging.Slf4j
import in.shabhushan.catalog.signin.constants.SigninConstants
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Properties
import org.apache.felix.scr.annotations.Property
import org.apache.felix.scr.annotations.Service
import org.apache.jackrabbit.api.security.user.UserManager
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.SlingHttpServletResponse
import org.apache.sling.api.resource.ResourceResolver
import org.apache.sling.api.servlets.SlingAllMethodsServlet

import javax.jcr.Session
import javax.servlet.Servlet
import javax.servlet.ServletException

/**
 * The AccountManagementService provides a way for a visitor (a non logged-in user) to request a new account
 * or to request a password reset.
 * For any other account management task (e.g. modifying a user property)
 * use org.apache.jackrabbit.api.security.user.UserManager, based on the logged-in user session.
 *
 * Created by Shashi Bhushan
 *       on 9/4/17.
 */
@Component
@Service(Servlet)
@Properties([
    @Property(name = "sling.servlet.paths", value="/bin/signup")
])
@Slf4j
class SignupServlet extends SlingAllMethodsServlet {

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        log.error("Signup servlet called")

        createUserInternal(request)



//         TODO: Use UserManager API to update user

        response.writer.write("success")
    }

    private void createUserInternal(SlingHttpServletRequest request) {
        String username = request.getParameter(SigninConstants.USERNAME_PLACEHOLDER)
        String password = request.getParameter(SigninConstants.PASSWORD_PLACEHOLDER)

        ResourceResolver resourceResolver = request.resourceResolver

        Session session = resourceResolver.adaptTo(Session)
        UserManager userManager = resourceResolver.adaptTo(UserManager)

        log.debug("user ${username} with password ${password} has been created")

        if(userManager) {
            userManager.createUser(username, password)

            if(!userManager.autoSave) {
                session?.save()
            }
        }
    }
}
