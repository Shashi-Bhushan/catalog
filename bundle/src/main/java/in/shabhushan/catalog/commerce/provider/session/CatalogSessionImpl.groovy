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

package in.shabhushan.catalog.commerce.provider.session;

import com.adobe.cq.commerce.api.CommerceException;
import com.adobe.cq.commerce.common.AbstractJcrCommerceService;
import com.adobe.cq.commerce.common.AbstractJcrCommerceSession;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Shashi Bhushan
 *      on 13/3/17.
 */
public class CatalogSessionImpl extends AbstractJcrCommerceSession {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogSessionImpl.class);

    public CatalogSessionImpl(AbstractJcrCommerceService commerceService, SlingHttpServletRequest request,
                              SlingHttpServletResponse response, Resource resource) throws CommerceException {
        super(commerceService, request, response, resource);
    }
}
