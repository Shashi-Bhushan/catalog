package in.shabhushan.catalog.services.impl


import groovy.util.logging.Slf4j

import in.shabhushan.catalog.dto.Book
import in.shabhushan.catalog.services.CreateProductNodesService
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Reference
import org.apache.felix.scr.annotations.Service
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.ResourceResolver
import org.apache.sling.api.resource.ResourceResolverFactory
import org.apache.sling.api.resource.ResourceUtil
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.core.type.TypeReference

/**
 * @author Shashi Bhushan
 */
@Component(
    label = "Catalog Create Product Node Service",
    description = "Creates Product Nodes by importing books from Goodreads",
    immediate = true,
    metatype = true,
    enabled = true
)
@Service(CreateProductNodesService)
@Slf4j
class CreateProductNodesServiceImpl implements CreateProductNodesService {

    @Reference
    private ResourceResolverFactory resourceResolverFactory

    private ResourceResolver resourceResolver

    private ObjectMapper mapper = new ObjectMapper()

    @Override
    void createProducts(PrintWriter writer, String productPath, List<Book> books) {
        if(resourceResolver == null) {
            resourceResolver = getResourceResolver()
        }

        books.forEach({ Book book ->
            String title = book.title.toLowerCase().replaceAll(" ", "-")

            String date = "{Date}${book.publication_year}-${book.publication_month}-${book.publication_day}T00:00:00.000+05:30"
            Map<String, Object> map = [
                "jcr:primaryType" : "nt:unstructured",
                "sling:resourceType" : "commerce/components/product",
                "cq:commerceType" : 'product',
                "textIsRich" : "[true,true]",
                "identifier" : title,
                "jcr:title" : book.title,
                "summary" : book.description,
                "noOfPages" : book.num_pages,
                "publishedDate" : date,
                "rating" : book.average_rating
            ]

            try {
                Resource createdResource = ResourceUtil.getOrCreateResource(resourceResolver, "$productPath/$title", map, "nt:unstructured", true)
                writer.println "Node created at $createdResource.path"
            } catch (Exception cause) {
                writer.println "Exception at $productPath/$title. Exception is $cause.message"
            }
        })
    }

    /**
     * Get Service Resource Resolver associated with 'signupService'
     *
     * @return
     *      {@link org.apache.sling.api.resource.ResourceResolver} of user associated with 'signupService'
     */
    private ResourceResolver getResourceResolver() {
        // Get Service Resource Resolver
        Map<String, Object> param = ["sling.service.subservice":"signupService"] as Map<String, Object>

        return resourceResolverFactory.getServiceResourceResolver(param)
    }
}
