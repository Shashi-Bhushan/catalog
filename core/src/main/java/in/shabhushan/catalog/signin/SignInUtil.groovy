package in.shabhushan.catalog.signin

import com.adobe.cq.sightly.WCMUsePojo
import com.adobe.granite.security.user.UserProperties
import com.day.cq.personalization.UserPropertiesUtil
import org.apache.felix.scr.annotations.Component

/**
 * Created by Shashi Bhushan
 *       on 9/4/17.
 */

@Component
class SignInUtil extends WCMUsePojo{

    boolean isAnonymous
    UserProperties userProperties

    @Override
    void activate() throws Exception {
        isAnonymous = UserPropertiesUtil.isAnonymous(request)
        userProperties = request.adaptTo(UserProperties)
    }
}
