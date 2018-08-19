/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package in.shabhushan.catalog.commerce

import spock.lang.Specification

/**
 * Created by Shashi Bhushan
 *       on 14/3/17.
 */
class CatalogCommerceProductImplSpec extends Specification {

//    @Shared
//    CatalogCommerceProductImpl product;

//    def setupSpec() {
//        Resource resource = Mock(Resource.class)
//
//        product = new CatalogCommerceProductImpl(resource)
//    }

    def "maximum of two numbers"() {
        expect:
        Math.max(a, b) == c

        where:
        a << [3, 5, 9]
        b << [7, 4, 9]
        c << [7, 5, 9]
    }

//    def "Should Return Correct Page Title"() {
//        given:
//        String pagePath
//
//        when:
//        pagePath = product.getPagePath()
//
//        then:
//        pagePath != null
//    }
}
