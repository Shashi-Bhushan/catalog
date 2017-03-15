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

package in.shabhushan.catalog.commerce.provider.service;

import com.adobe.cq.commerce.api.CommerceService;
import com.adobe.cq.commerce.api.CommerceServiceFactory;
import com.adobe.cq.commerce.common.AbstractJcrCommerceServiceFactory
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;

/**
 * Created by Shashi Bhushan
 *      on 13/3/17.
 */
@Component(label = "Catalog Service Factory", description = "Factory for reference implementation commerce service", metatype = true)
@Service(CommerceServiceFactory.class)
@Property(name = "commerceProvider", value = "catalog", propertyPrivate = true)
public class CatalogServiceFactory extends AbstractJcrCommerceServiceFactory implements CommerceServiceFactory {
    @Override
    public CommerceService getCommerceService(Resource resource) {
        return new CatalogServiceImpl(getServiceContext(), resource);
    }
}
