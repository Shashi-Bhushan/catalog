package in.shabhushan.catalog.commerce.pojo

import com.adobe.cq.commerce.api.CommerceService
import com.adobe.cq.commerce.api.Product
import com.adobe.cq.sightly.WCMUsePojo
import in.shabhushan.catalog.commerce.adapter.CatalogAdapter

/**
 * Created by Shashi Bhushan
 *       on 14/3/17.
 */
class ProductPojo extends WCMUsePojo{

    String productData

    /**
     * Instance of {@link in.shabhushan.catalog.commerce.provider.product.CatalogProductImpl}
     */
    CatalogAdapter adapter

    @Override
    void activate() throws Exception {
        productData = getProperties()?.get("productData", String)

        CommerceService commerceService = getResource().adaptTo(CommerceService.class)
        commerceService.login(getRequest(), getResponse())

        Product product = commerceService.getProduct(productData)
        adapter = product.adaptTo(CatalogAdapter)
    }

    String getTitle() {
        adapter.getTitle()
    }

    String getRating() {
        adapter.getRating()
    }

    String getName() {
        adapter.getClass().getName()
    }
}
