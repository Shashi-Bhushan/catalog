package in.shabhushan.catalog.services

import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.SlingHttpServletResponse

/**
 * @author Shashi Bhushan
 */
interface CatalogGoodReadsImporterService {
    void importCatalog(SlingHttpServletRequest request, SlingHttpServletResponse response)
}
