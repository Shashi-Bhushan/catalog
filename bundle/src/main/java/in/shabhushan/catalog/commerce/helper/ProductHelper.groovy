package in.shabhushan.catalog.commerce.helper

import com.adobe.cq.commerce.api.Product
import com.adobe.cq.sightly.WCMUsePojo
import in.shabhushan.catalog.commerce.adapter.ProductAdapter

/**
 * Created by Shashi Bhushan
 *       on 2/4/17.
 */
class ProductHelper extends WCMUsePojo{

    static Product product
    static ProductAdapter pojo

    @Override
    void activate() throws Exception {

    }
}
