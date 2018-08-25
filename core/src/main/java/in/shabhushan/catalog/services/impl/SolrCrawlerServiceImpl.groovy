package in.shabhushan.catalog.services.impl

import com.day.cq.search.PredicateGroup
import com.day.cq.search.Query
import com.day.cq.search.QueryBuilder
import com.day.cq.search.result.SearchResult
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import in.shabhushan.catalog.context.SolrContext
import org.apache.solr.common.SolrInputDocument
import in.shabhushan.catalog.services.ResourceResolverService
import in.shabhushan.catalog.services.SolrCrawlerService
import in.shabhushan.catalog.util.SolrUtil
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Reference
import org.apache.felix.scr.annotations.Service
import org.apache.sling.api.resource.ResourceResolver

import javax.jcr.Session

/**
 * @author Shashi Bhushan
 */
@Component(
    label = "Solr Crawler Service",
    immediate = true,
    metatype = false,
    enabled = true
)
@Service(SolrCrawlerService)
@CompileStatic
@Slf4j
class SolrCrawlerServiceImpl implements SolrCrawlerService {

    @Reference
    private QueryBuilder queryBuilder

    @Reference
    private ResourceResolverService resourceResolverService

    @Override
    List<SolrInputDocument> crawlContent(SolrContext solrContext) {
        ResourceResolver resourceResolver = resourceResolverService.getResourceResolver()

        Map<String, String> searchMap = SolrUtil.getSearchMap(solrContext)

        // TODO: what is params :
        Query query = queryBuilder.createQuery(PredicateGroup.create(params: searchMap), resourceResolver.adaptTo(Session))

        SearchResult searchResult = query.getResult()

        return SolrUtil.createPageMetaData(solrContext, searchResult)
    }
}
