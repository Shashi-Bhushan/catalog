package in.shabhushan.catalog.commerce.pojo

import com.adobe.cq.commerce.api.CommerceService
import com.adobe.cq.commerce.api.Product
import com.adobe.cq.sightly.WCMUsePojo

/**
 * Created by Shashi Bhushan
 *       on 14/3/17.
 */
class ProductPojo extends WCMUsePojo{

    String productData

    /**
     * Instance of {@link in.shabhushan.catalog.commerce.provider.product.CatalogProductImpl}
     */
    Product product

    @Override
    void activate() throws Exception {
        productData = getProperties()?.get("productData", String)

        CommerceService commerceService = getResource().adaptTo(CommerceService.class)
        commerceService.login(getRequest(), getResponse())

        product = commerceService.getProduct(productData)
    }

    String getTitle() {
        product.getProperty("jcr:title", String)
    }

    String getRating() {
        product.getProperty("catalogRating", String)
    }
}
