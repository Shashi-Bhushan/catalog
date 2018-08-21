package in.shabhushan.catalog.services.impl

import in.shabhushan.catalog.services.UserManagerService
import org.apache.sling.api.resource.ResourceResolver

import javax.jcr.Session

/**
 * @author Shashi Bhushan
 *
 * Change History:
 * Revision         Date            Dev             Comment
 * ----------------------------------------------------------------------------------------
 *
 */
class UserManagerServiceImpl implements UserManagerService {

    @Override
    String getLoggedInUserID(ResourceResolver resourceResolver) {
        Session session = resourceResolver.adaptTo(Session)

        return session.getUserID()
    }
}
