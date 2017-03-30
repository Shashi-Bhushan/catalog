package in.shabhushan.catalog.commerce.pojo

import com.adobe.cq.commerce.api.CommerceService
import com.adobe.cq.commerce.api.Product
import com.adobe.cq.commerce.api.asset.ProductAssetManager
import com.day.cq.wcm.api.Page
import com.day.cq.wcm.api.PageManager
import in.shabhushan.catalog.commerce.provider.product.CatalogProductImpl
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.ResourceResolver
import org.apache.sling.api.resource.ValueMap
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Shashi Bhushan
 *      on 3/30/2017
 *      for catalog
 */
class ProductPojoSpec extends Specification {

    @Shared
    private ProductPojo productPojo

    def setupSpec() {
        Resource constructorArg = Spy(Resource) {
            getResourceResolver() >> Spy(ResourceResolver) {
                adaptTo(ProductAssetManager) >> Mock(ProductAssetManager)
                adaptTo(PageManager) >> Mock(PageManager)
            }
        }

        Resource resource = Spy(Resource) {
            getResourceResolver() >> Spy(ResourceResolver) {
                adaptTo(ProductAssetManager) >> Mock(ProductAssetManager)
                adaptTo(PageManager) >> Mock(PageManager)
                adaptTo(CommerceService) >> Spy(CommerceService) {
                    getProduct() >> Spy(CatalogProductImpl, constructorArgs : [constructorArg]) {
                        getProperty("jcr:title") >> {
                            "myTitle"
                        }
                        getProperty("catalogRating") >> {
                            "6"
                        }
                    }
                }
            }
        }

        productPojo = Spy(ProductPojo, {
            getResource() >> resource
            getProperties() >> Mock(ValueMap) {
                get(String, String) >> {
                    "/etc/commerce/products"
                }
                get("resourcePage", Page.class) >> Mock(Page)
            }
        }).activate()
    }

    def "Method Returns Intended Title"() {
        given:
        String title

        when:
        title = productPojo.getTitle()

        then:
        title == "myTitle"
    }
}
