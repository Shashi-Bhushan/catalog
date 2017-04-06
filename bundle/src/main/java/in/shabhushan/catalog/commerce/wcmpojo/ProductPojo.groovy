package in.shabhushan.catalog.commerce.wcmpojo

import com.adobe.cq.commerce.api.CommerceService
import com.adobe.cq.commerce.api.Product
import com.adobe.cq.sightly.WCMUsePojo
import in.shabhushan.catalog.commerce.adapter.CatalogAdapter
import in.shabhushan.catalog.commerce.helper.ProductHelper

/**
 * Created by Shashi Bhushan
 *       on 14/3/17.
 */
class ProductPojo extends WCMUsePojo{

    String productData

    /**
     * Instance of {@link in.shabhushan.catalog.commerce.framework.provider.product.CatalogProductImpl}
     */
    private CatalogAdapter adapter

    @Override
    void activate() throws Exception {
        productData = resource.valueMap["cq:productMaster"]

        CommerceService commerceService = getResource().adaptTo(CommerceService)
        setAdapterInternal(commerceService)
    }

    /**
     *  This method sets {@code adapter}
     *
     * - Logins to {@link CommerceService}
     * - Get {@link Product} from path specified by {@code productDate}
     * - Adapt {@link Product} to {@link CatalogAdapter}
     */
    private void setAdapterInternal(CommerceService commerceService) {
        if(commerceService){
            commerceService.login(getRequest(), getResponse())

            Product product = commerceService.getProduct(productData)
            adapter = product?.adaptTo(CatalogAdapter)

            ProductHelper.product = product
            ProductHelper.pojo = adapter
        }
    }

    /**
     * Getter for {@link CatalogAdapter} instance
     *
     * @return
     *      {@link Product} adapted to {@link CatalogAdapter}
     */
    CatalogAdapter getPojo() {
        adapter
    }

    /**
     * Getter for Class name of {@link CatalogAdapter}
     *
     * @return
     */
    String getName() {
        adapter.class.name
    }
}
