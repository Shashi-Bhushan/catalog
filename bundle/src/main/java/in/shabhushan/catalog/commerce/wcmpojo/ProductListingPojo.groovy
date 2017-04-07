package in.shabhushan.catalog.commerce.wcmpojo

import com.adobe.cq.commerce.api.CommerceService
import com.adobe.cq.commerce.api.Product
import com.adobe.cq.sightly.WCMUsePojo
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
        CommerceService commerceService = resource.adaptTo(CommerceService)

        if(commerceService) {
            commerceService.login(request, response)
        }

        iterateInternal(commerceService)
    }

    private void iterateInternal(CommerceService commerceService) {
        Resource pagesRoot = resourceResolver.resolve(resource.path[0..-12])

        pagesRoot.listChildren().each { Resource page ->
            if(page.resourceType == "cq:Page") {
                String productMaster = page.getChild('jcr:content')?.valueMap['cq:productMaster']

                if(productMaster) {
                    Product product = commerceService.getProduct(productMaster)
                    ProductAdapter adapter = product.adaptTo(ProductAdapter)

                    if(adapter) {
                        pageList << adapter
                        pageHrefList << (page.path + '.html')
                    }
                }
            }
        }
    }
}
