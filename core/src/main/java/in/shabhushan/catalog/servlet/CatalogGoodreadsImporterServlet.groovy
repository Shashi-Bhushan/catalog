package in.shabhushan.catalog.servlet

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import in.shabhushan.catalog.services.CatalogGoodReadsImporterService
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Properties
import org.apache.felix.scr.annotations.Property
import org.apache.felix.scr.annotations.Reference
import org.apache.felix.scr.annotations.Service
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.SlingHttpServletResponse
import org.apache.sling.api.servlets.SlingAllMethodsServlet

import javax.servlet.Servlet
import javax.servlet.ServletException

/**
 * @author Shashi Bhushan
 */
@Component(
    label = "Goodreads Importer Servlet",
    description = "Imports books' information from Goodreads Author API in AEM",
    metatype = false,
    enabled = true,
    immediate = true
)
@Service(
    value= Servlet.class
)
@Properties([
    @Property(name = "sling.servlet.paths", value = [
        "/bin/goodreads/catalogImport"
    ], propertyPrivate = true),
    @Property(name = "sling.servlet.methods", value = [
        "POST"
    ], propertyPrivate = true)
])
@Slf4j
@CompileStatic
class CatalogGoodreadsImporterServlet extends SlingAllMethodsServlet {

    @Reference
    private CatalogGoodReadsImporterService catalogGoodReadsImporterService

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        log.info "Entered ${this.class.name}"

        catalogGoodReadsImporterService.importCatalog(request, response)

        log.info "Exiting ${this.class.name}"
    }
}
