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

/**
 * @author Shashi Bhushan
 */
package in.shabhushan.catalog.commerce.hybris.common;

import com.adobe.cq.commerce.api.CommerceService;
import com.adobe.cq.commerce.api.CommerceServiceFactory;
import com.adobe.cq.commerce.api.Product;
import com.adobe.cq.commerce.common.AbstractJcrCommerceServiceFactory;
import in.shabhushan.catalog.commerce.hybris.HybrisSession;
import in.shabhushan.catalog.commerce.hybris.SessionInfo;
import in.shabhushan.catalog.commerce.hybris.api.HybrisFactory;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(metatype = true, label = "Catalog Default Hybris Factory", immediate = true)
@Service(value = { CommerceServiceFactory.class, HybrisFactory.class })
@Properties(value = { @Property(name = "service.description", value = "Hybris-specific factory for commerce service"),
    @Property(name = "commerceProvider", value = "catalog-default") })
public class DefaultHybrisFactory extends AbstractJcrCommerceServiceFactory implements HybrisFactory {

    private static final Logger log = LoggerFactory.getLogger(DefaultHybrisFactory.class);

    @Override
    public HybrisConfiguration getServiceContext() {
        return null;
    }

    @Override
    public Product getProduct(Resource resource) {
        return null;
    }

    @Override
    public HybrisSession getSession(HybrisService service, SlingHttpServletRequest request, SlingHttpServletResponse response, SessionInfo sessionInfo) {
        return null;
    }

    @Override
    public CommerceService getCommerceService(Resource resource) {
        return null;
    }
}
