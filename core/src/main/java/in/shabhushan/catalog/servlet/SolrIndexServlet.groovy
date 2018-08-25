package in.shabhushan.catalog.servlet

import groovy.util.logging.Slf4j
import in.shabhushan.catalog.context.SolrContext
import org.apache.solr.common.SolrInputDocument
import in.shabhushan.catalog.services.SolrCrawlerService
import in.shabhushan.catalog.services.SolrIndexService
import in.shabhushan.catalog.services.impl.SolrConfigurationImpl
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Properties
import org.apache.felix.scr.annotations.Property
import org.apache.felix.scr.annotations.Reference
import org.apache.felix.scr.annotations.Service
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.SlingHttpServletResponse
import org.apache.sling.api.servlets.SlingAllMethodsServlet
import org.osgi.service.cm.ConfigurationAdmin

import javax.servlet.Servlet
import javax.servlet.ServletException

/**
 * @author Shashi Bhushan
 */
@Component(
    label = "Solr Index Servlet",
    description = "Servlet index Content to Solr",
    metatype = false,
    enabled = true,
    immediate = true
)
@Service(
    value= Servlet.class
)
@Properties([
    @Property(name = "sling.servlet.paths", value = [
        "/bin/solr/push/pages"
    ], propertyPrivate = true),
    @Property(name = "sling.servlet.methods", value = [
        "POST"
    ], propertyPrivate = true)
])
@Slf4j
class SolrIndexServlet extends SlingAllMethodsServlet {

    @Reference
    private ConfigurationAdmin configurationAdmin

    @Reference
    private SolrCrawlerService solrCrawlerService

    @Reference
    private SolrIndexService solrIndexService

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        log.info "Entered ${this.class.name}"

        SolrContext solrContext = new SolrContext(configurationAdmin.getConfiguration(SolrConfigurationImpl.class.name))

        List<SolrInputDocument> solrIndexRequests = solrCrawlerService.crawlContent(solrContext)
        boolean resultindexingPages = solrIndexService.indexPagesToSolr(solrContext, solrIndexRequests)

        log.info "Exiting ${this.class.name}"
    }
}
