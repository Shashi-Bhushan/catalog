package in.shabhushan.catalog.commerce.wcmpojo

import com.adobe.cq.commerce.api.CommerceService
import com.adobe.cq.commerce.api.Product
import com.adobe.cq.sightly.WCMUsePojo
import in.shabhushan.catalog.commerce.adapter.ProductAdapter
import in.shabhushan.catalog.commerce.helper.ProductHelper

/**
 * Created by Shashi Bhushan
 *       on 14/3/17.
 */
class ProductPojo extends WCMUsePojo{

    String productData

    /**
     * Instance of {@link in.shabhushan.catalog.commerce.framework.provider.product.CatalogCommerceProductImpl}
     */
    private ProductAdapter adapter

    @Override
    void activate() throws Exception {
        productData = pageProperties["cq:productMaster"]

        CommerceService commerceService = resource.adaptTo(CommerceService)
        setAdapterInternal(commerceService)
    }

    /**
     *  This method sets {@code adapter}
     *
     * - Logins to {@link CommerceService}
     * - Get {@link Product} from path specified by {@code productDate}
     * - Adapt {@link Product} to {@link ProductAdapter}
     */
    private void setAdapterInternal(CommerceService commerceService) {
        if(commerceService){
            commerceService.login(request, response)

            Product product = commerceService.getProduct(productData)
            adapter = product?.adaptTo(ProductAdapter)

            ProductHelper.product = product
            ProductHelper.pojo = adapter
        }
    }

    /**
     * Getter for {@link ProductAdapter} instance
     *
     * @return
     *      {@link Product} adapted to {@link ProductAdapter}
     */
    ProductAdapter getPojo() {
        adapter
    }

    /**
     * Getter for Class name of {@link ProductAdapter}
     *
     * @return
     */
    String getName() {
        adapter.class.name
    }
}
