package in.shabhushan.catalog.commerce.wcmpojo

import com.adobe.cq.commerce.api.CommerceService
import com.adobe.cq.commerce.api.Product
import com.adobe.cq.sightly.WCMUsePojo
import com.day.cq.wcm.api.Page
import com.sun.org.apache.xml.internal.resolver.Catalog
import in.shabhushan.catalog.commerce.adapter.CatalogAdapter
import in.shabhushan.catalog.commerce.helper.ProductHelper
import org.apache.sling.api.resource.Resource

/**
 * Created by Shashi Bhushan
 *       on 2/4/17.
 */
class ProductListingPojo extends WCMUsePojo {

    String rootPath

    List<CatalogAdapter> pageList = new ArrayList<CatalogAdapter>()
    List<String> pageHrefList = new ArrayList<String>()

    @Override
    void activate() throws Exception {
        CommerceService commerceService = resource.adaptTo(CommerceService)
        if(commerceService) {
            commerceService.login(request, response)
        }

        Resource pagesRoot = resourceResolver.resolve(resource.path.reverse().drop(12).reverse())

        /*
         * TODO: iterate using groovy each
         */
        if(pagesRoot.hasChildren()){
            Iterator<Resource> pages = pagesRoot.listChildren()

            while(pages.hasNext()) {
                Resource page = pages.next()

                if(page.resourceType == "cq:Page") {
                    String productMaster = page.getChild('jcr:content')?.valueMap['cq:productMaster']
                    if(productMaster) {
                        Product product = commerceService.getProduct(productMaster)
                        CatalogAdapter adapter = product.adaptTo(CatalogAdapter)

                        if(adapter) {
                            pageList.add(adapter)
                            pageHrefList.add(page.path + '.html')
                        }
                    }
                }
            }
        }
    }
}
