package in.shabhushan.catalog.commerce.pojo

import com.adobe.cq.sightly.WCMUsePojo

/**
 * Created by Shashi Bhushan
 *       on 14/3/17.
 */
class ProductPojo extends WCMUsePojo{

    private String productData;

    @Override
    void activate() throws Exception {
        productData = getProperties().get("productData", "")
    }
}
