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
     * Instance of {@link in.shabhushan.catalog.commerce.framework.provider.product.CatalogProductImpl}
     */
    private CatalogAdapter adapter

    @Override
    void activate() throws Exception {
        productData = getProperties()?.get("productData", String)

        setAdapterInternal()
    }

    /**
     *  This method sets {@code adapter}
     *
     * - Logins to {@link CommerceService}
     * - Get {@link Product} from path specified by {@code productDate}
     * - Adapt {@link Product} to {@link CatalogAdapter}
     */
    private void setAdapterInternal() {
        CommerceService commerceService = getResource().adaptTo(CommerceService)

        if(commerceService){
            commerceService.login(getRequest(), getResponse())

            Product product = commerceService.getProduct(productData)
            adapter = product?.adaptTo(CatalogAdapter)
        }
    }

    /**
     * Delegate Getter for {@link CatalogAdapter#getTitle()}
     *
     * @return
     *      title returned from {@link CatalogAdapter#getTitle()}
     */
    String getTitle() {
        adapter.title
    }

    /**
     * Delegate Getter for {@link CatalogAdapter#getRating()}
     *
     * @return
     *      title returned from {@link CatalogAdapter#getRating()}
     */
    String getRating() {
        adapter.rating
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
