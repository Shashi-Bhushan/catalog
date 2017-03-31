package in.shabhushan.catalog.commerce.wcmpojo

import com.adobe.cq.commerce.api.CommerceService
import com.adobe.cq.commerce.api.asset.ProductAssetManager
import com.day.cq.wcm.api.PageManager
import in.shabhushan.catalog.commerce.adapter.CatalogAdapter
import in.shabhushan.catalog.commerce.framework.provider.product.CatalogProductImpl
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.ResourceResolver
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

        productPojo = Spy(ProductPojo) {
            getPojo() >> Spy(CatalogAdapter) {
                getTitle() >> {
                    "myTitle"
                }
            }
        }
    }
/*
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

        WCMUsePojo wcmpojo = Spy(WCMUsePojo) {
            get(String, ValueMap) >> Spy(ValueMap) {
                get("productData", String) >> {
                    "/etc/commerce/product"
                }
            }
        }

        productPojo = Spy(ProductPojo, {
            getResource() >> resource
            getProperties() >> wcmpojo.get("properties", ValueMap)
        })
    }
*/
    def "Method Returns Intended Title"() {
        given:
        String title

        when:
        title = productPojo.pojo.title

        then:
        title == "myTitle"
    }

}
