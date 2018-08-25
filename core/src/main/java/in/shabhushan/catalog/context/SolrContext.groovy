package in.shabhushan.catalog.context

import in.shabhushan.catalog.services.impl.SolrConfigurationImpl
import org.osgi.service.cm.Configuration

/**
 * @author Shashi Bhushan
 */
class SolrContext {

    String solrServerProtocol
    String solrServerName
    String solrServerPort
    String solrCollectionName
    String contentPagePath
    String solrUrl
    String resourceType
    List<String> allowedTemplates

    SolrContext(Configuration solrConfiguration) {
        Dictionary properties = solrConfiguration.properties

        // Synthetic Accessor of properties
        this.solrServerProtocol = properties.get(SolrConfigurationImpl.SOLR_PROTOCOL) as String
        this.solrServerName = properties.get(SolrConfigurationImpl.SOLR_SERVER_NAME) as String
        this.solrServerPort = properties.get(SolrConfigurationImpl.SOLR_SERVER_PORT) as String
        this.solrCollectionName = properties.get(SolrConfigurationImpl.SOLR_SERVER_COLLECTION_NAME) as String
        this.contentPagePath = properties.get(SolrConfigurationImpl.CONTENT_PAGE_PATH) as String
        this.resourceType = properties.get(SolrConfigurationImpl.RESOURCE_TYPE) as String
        this.allowedTemplates = properties.get(SolrConfigurationImpl.ALLOWED_TEMPLATES) as List<String>

        this.solrUrl = "$solrServerProtocol://$solrServerName:$solrServerPort/solr/$solrCollectionName"
    }
}
