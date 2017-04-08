package in.shabhushan.catalog.signin

import com.adobe.cq.sightly.WCMUsePojo
import com.day.cq.personalization.UserPropertiesUtil
import org.apache.felix.scr.annotations.Component

/**
 * Created by Shashi Bhushan
 *       on 9/4/17.
 */

@Component
class SignInUtil extends WCMUsePojo{

    boolean isAnonymous

    @Override
    void activate() throws Exception {
        isAnonymous = UserPropertiesUtil.isAnonymous(request)
    }
}
