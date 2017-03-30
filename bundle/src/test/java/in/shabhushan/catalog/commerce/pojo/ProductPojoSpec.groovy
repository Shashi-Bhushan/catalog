package in.shabhushan.catalog.commerce.pojo

import com.adobe.cq.commerce.api.CommerceService
import com.adobe.cq.commerce.api.Product
import in.shabhushan.catalog.commerce.provider.product.CatalogProductImpl
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Shashi Bhushan
 *      on 3/30/2017
 *      for catalog
 */
class ProductPojoSpec extends Specification {

    @Shared
    private ProductPojo productPojo;

    def setupSpec() {
        productPojo = Spy(ProductPojo, {
            getTitle() >> {
                "myTitle"
            }
        })

        /*
         * TODO: Make This interaction based test
         */
//        Product product = Spy(CatalogProductImpl, {
//            getProperty("jcr:title") >> {
//                "myTitle"
//            }
//        })

//        Spy(CommerceService, {
//            getProduct(String) >> {
//                product
//            }
//        })
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
