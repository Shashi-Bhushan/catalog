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
package in.shabhushan.catalog.commerce.hybris.api;

import com.adobe.cq.commerce.api.CommerceServiceFactory;
import com.adobe.cq.commerce.api.Product;
import in.shabhushan.catalog.commerce.hybris.HybrisSession;
import in.shabhushan.catalog.commerce.hybris.SessionInfo;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;

/**
 * An extension of the core {@link CommerceServiceFactory} interface to add specific hybris-related functions.
 */
public interface HybrisFactory extends CommerceServiceFactory {
    /**
     * Get the current configuration options.
     */
    public HybrisConfiguration getServiceContext();

    /**
     * Get a {@link HybrisService} implementation for a resource.
     */
    public HybrisService getCommerceService(Resource resource);

    /**
     * Get a {@link Product} implementation from a resource.
     * @param resource the resource containing the product data, or a reference to it.
     * @return a {@link Product} instance, or {@code null} if the resource did not contain the necessary information.
     */
    public Product getProduct(Resource resource);

    /**
     * Get a {@link HybrisSession} for a request. {@link HybrisService#login} implementations should call through this
     * method, but that is not a requirement.
     * @param service
     * @param request
     * @param response
     * @param sessionInfo
     * @return
     */
    public HybrisSession getSession(HybrisService service, SlingHttpServletRequest request, SlingHttpServletResponse response, SessionInfo sessionInfo);

}
