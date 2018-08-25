package in.shabhushan.catalog.services

import in.shabhushan.catalog.context.SolrContext
import org.apache.solr.common.SolrInputDocument

/**
 * @author Shashi Bhushan
 */
interface SolrCrawlerService {
    List<SolrInputDocument> crawlContent(SolrContext solrContext)
}
