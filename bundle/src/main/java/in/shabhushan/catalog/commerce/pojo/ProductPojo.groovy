package in.shabhushan.catalog.commerce.pojo

import com.adobe.cq.commerce.api.CommerceService
import com.adobe.cq.commerce.api.CommerceSession
import com.adobe.cq.commerce.api.Product
import com.adobe.cq.sightly.WCMUsePojo
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.SlingHttpServletResponse
import org.apache.sling.api.resource.Resource

/**
 * Created by Shashi Bhushan
 *       on 14/3/17.
 */
class ProductPojo extends WCMUsePojo{

    String productData

    Product product

    @Override
    void activate() throws Exception {
        productData = getProperties().get("productData", String)

        CommerceService commerceService = getResource().adaptTo(CommerceService.class)
        commerceService.login(getRequest(), getResponse())

        product = commerceService.getProduct(productData)
    }

    String getTitle() {
        product.getProperty("jcr:title", String)
    }

    def getRating() {
        product.getProperty("catalogRating", String)
    }
}
