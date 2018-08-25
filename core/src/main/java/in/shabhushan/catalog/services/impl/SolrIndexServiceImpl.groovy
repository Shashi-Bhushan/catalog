package in.shabhushan.catalog.services.impl

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import in.shabhushan.catalog.context.SolrContext
import org.apache.solr.client.solrj.impl.HttpSolrClient
import org.apache.solr.common.SolrInputDocument
import in.shabhushan.catalog.services.SolrIndexService
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Service

/**
 * @author Shashi Bhushan
 */
@Component(
    label = "Solr Index Service",
    immediate = true,
    metatype = false,
    enabled = true
)
@Service(SolrIndexService)
@CompileStatic
@Slf4j
class SolrIndexServiceImpl implements SolrIndexService {

    @Override
    boolean indexPagesToSolr(SolrContext solrContext, List<SolrInputDocument> solrInputDocuments) {

        if(null == solrInputDocuments || solrInputDocuments.isEmpty()) {
            return false
        }

        HttpSolrClient server = new HttpSolrClient(solrContext.solrUrl)

        solrInputDocuments.forEach({SolrInputDocument solrInputDocument ->
            server.add(solrInputDocument)
            server.commit()
        })
        return true
    }
}
