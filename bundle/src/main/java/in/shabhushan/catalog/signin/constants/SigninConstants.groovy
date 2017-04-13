package in.shabhushan.catalog.signin.constants

import com.adobe.cq.sightly.WCMUsePojo

/**
 * Created by Shashi Bhushan
 *       on 9/4/17.
 */
class SigninConstants extends WCMUsePojo {

    static final String USERNAME_PLACEHOLDER = "j_username"
    static final String PASSWORD_PLACEHOLDER = "j_password"
    static final String CONFIRM_PASSWORD_PLACEHOLDER = "j_password"
    static final String FULL_NAME= "j_name"
    static final String EMAIL = "j_email"

    static final String PROPERTIES_NODE_PATH = "/content/catalog/en/properties"

    @Override
    void activate() throws Exception {

    }
}
