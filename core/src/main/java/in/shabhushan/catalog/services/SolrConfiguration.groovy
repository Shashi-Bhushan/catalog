package in.shabhushan.catalog.services

/**
 * @author Shashi Bhushan
 */
interface SolrConfiguration {
    String getSolrProtocol()

    String getSolrServerName()

    String getSolrServerPort()

    String getSolrCollectionName()

    String getContentPagePath()

    String getResourceType()

    String[] getAllowedTemplates()
}
