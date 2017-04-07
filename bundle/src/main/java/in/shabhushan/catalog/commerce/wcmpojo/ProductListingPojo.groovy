package in.shabhushan.catalog.commerce.wcmpojo

import com.adobe.cq.commerce.api.CommerceService
import com.adobe.cq.commerce.api.Product
import com.adobe.cq.commerce.common.CommerceHelper
import com.adobe.cq.sightly.WCMUsePojo
import com.day.cq.wcm.api.Page
import in.shabhushan.catalog.commerce.adapter.ProductAdapter
import org.apache.sling.api.resource.Resource

/**
 * Created by Shashi Bhushan
 *       on 2/4/17.
 */
class ProductListingPojo extends WCMUsePojo {

    List<ProductAdapter> pageList = []
    List<String> pageHrefList = []

    @Override
    void activate() throws Exception {
        currentPage.listChildren().each { Page page ->
            Product product = CommerceHelper.findCurrentProduct(page)

            if (product) {
                ProductAdapter adapter = product.adaptTo(ProductAdapter)

                if (adapter) {
                    pageList << adapter
                    pageHrefList << (page.path + '.html')
                }
            }
        }
    }
}
