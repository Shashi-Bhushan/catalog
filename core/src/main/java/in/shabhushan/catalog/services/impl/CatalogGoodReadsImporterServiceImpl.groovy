package in.shabhushan.catalog.services.impl

import com.adobe.cq.commerce.pim.api.ProductImporter
import com.adobe.cq.commerce.pim.common.AbstractProductImporter
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import in.shabhushan.catalog.constants.CommonConstants
import in.shabhushan.catalog.constants.GoodReadsImporterConstants
import in.shabhushan.catalog.dto.Book
import in.shabhushan.catalog.dto.Books
import in.shabhushan.catalog.dto.GoodreadsResponse
import in.shabhushan.catalog.dto.Response
import in.shabhushan.catalog.services.CatalogGoodReadsImporterService
import in.shabhushan.catalog.services.CreateProductNodesService
import in.shabhushan.catalog.services.HttpService
import in.shabhushan.catalog.services.ResultDeserializationService
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Properties
import org.apache.felix.scr.annotations.Property
import org.apache.felix.scr.annotations.Reference
import org.apache.felix.scr.annotations.Service
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.SlingHttpServletResponse
import org.apache.sling.api.resource.ResourceResolver
import org.json.JSONException
import org.json.JSONObject
import org.json.XML
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.jcr.Node
import javax.jcr.RepositoryException

/**
 * @author Shashi Bhushan
 */
@Component(
    label = "Catalog Importer",
    description = "Catalog's Importer for GoodReads",
    immediate = true,
    metatype = false,
    enabled = true
)
@Service(CatalogGoodReadsImporterService)
@Properties(value = [ @Property(name = "commerceProvider", value = CommonConstants.COMMERCE_PROVIDER, propertyPrivate = true) ])
@CompileStatic
@Slf4j
class CatalogGoodReadsImporterServiceImpl extends AbstractProductImporter implements CatalogGoodReadsImporterService{

    private static final Logger LOG = LoggerFactory.getLogger(CatalogGoodReadsImporterServiceImpl.class);

    @Reference
    private HttpService httpService

    @Reference
    private ResultDeserializationService resultDeserializationService

    @Reference
    private CreateProductNodesService createProductNodesService

    @Override
    void importCatalog(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        String baseApiPath = request.getParameter(GoodReadsImporterConstants.BASE_API_PATH)
        String apiKey = request.getParameter(GoodReadsImporterConstants.API_KEY)
        String authorId = request.getParameter(GoodReadsImporterConstants.AUTHOR_ID)
        String productsPath = request.getParameter(GoodReadsImporterConstants.BASE_PRODUCTS_PATH)

        List<Book> books = getBooks(getUrl(baseApiPath, apiKey, authorId))

        log.info "${books}"

        createProductNodesService.createProducts(response.getWriter(), productsPath, books)
    }

    private static final String getUrl(String baseApiPath, String apiKey, String authorId) {
        return "${baseApiPath}?key=${apiKey}&id=${authorId}"
    }

    private final List<Book> getBooks(String url) {
        // Get Response
        String apiResponse = httpService.get(url);

        log.debug "API Response is ${apiResponse}"
        JSONObject jsonObj = null
        try {
            jsonObj = XML.toJSONObject(apiResponse)
        } catch (JSONException e) {
            log.error("JSON exception", e.getMessage())
            e.printStackTrace()
        }

        log.debug "JsonObj is ${jsonObj?.toString()}"

        Response deserializedResponse = resultDeserializationService.deserializeResult(jsonObj?.toString(), Response.class)

        return deserializedResponse?.getGoodreadsResponse()?.getAuthor()?.getBooks()?.getBook()
    }

    /**
     * Checks for if API Path for product import, base store id etc are set or not
     * @param slingHttpServletRequest
     * @param slingHttpServletResponse
     * @return
     * @throws IOException
     */
    @Override
    protected boolean validateInput(SlingHttpServletRequest slingHttpServletRequest, SlingHttpServletResponse slingHttpServletResponse) throws IOException {
        return false
    }

    /**
     * Starts the import of products
     * @param resourceResolver
     * @param node is the base Node from where products' should start
     * @param b is the import incremental, if it is save version info as well
     * @throws RepositoryException
     * @throws IOException
     */
    @Override
    protected void doImport(ResourceResolver resourceResolver, Node node, boolean b) throws RepositoryException, IOException {
        /**
         * From Product Response feed, get product
         *
         * while importing this product, check for "categories" property
         * if has category and does not have "baseProductCode" && "baseProduct" then it's a base product
         * if does not have category and have "baseProductCode" && "baseProduct" then it's a variant product
         */

    }
}
