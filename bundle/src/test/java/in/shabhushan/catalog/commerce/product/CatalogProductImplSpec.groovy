package in.shabhushan.catalog.commerce.product

import spock.lang.Specification

/**
 * Created by Shashi Bhushan
 *       on 14/3/17.
 */
class CatalogProductImplSpec extends Specification {
    def "maximum of two numbers"() {
        expect:
        Math.max(a, b) == c

        where:
        a << [3, 5, 9]
        b << [7, 4, 9]
        c << [7, 5, 9]
    }
}
