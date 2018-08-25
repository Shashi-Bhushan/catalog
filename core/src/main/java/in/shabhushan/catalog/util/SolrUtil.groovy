package in.shabhushan.catalog.util

import com.day.cq.search.result.Hit
import com.day.cq.search.result.SearchResult
import in.shabhushan.catalog.context.SolrContext
import org.apache.solr.common.SolrInputDocument
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.ValueMap

/**
 * @author Shashi Bhushan
 */
class SolrUtil {
    static Map<String, String> getSearchMap(final SolrContext solrContext) {
        Map<String, String> params = new HashMap<String, String>()
        params.put("path", solrContext.getContentPagePath())
        params.put("type", solrContext.getResourceType())
        params.put("p.offset", "0")
        params.put("p.limit", "-1")

        return params
    }

    static List<SolrInputDocument> createPageMetaData(SolrContext solrContext, SearchResult searchResult) {

        List<SolrInputDocument> solrInputDocuments = new ArrayList<>();

        for (Hit hit : searchResult.getHits()) {

            Resource page = hit.getResource();
            Resource pageContent = page.getChild("jcr:content");

            if(null != pageContent) {

                ValueMap properties = pageContent.adaptTo(ValueMap);
                String template = properties.get("cq:template",String);
                if(solrContext.getAllowedTemplates().contains(template)) {
                    // TODO: implement pageIndexable
                    String isPageIndexable = properties.get("notsolrindexable",String);

                    SolrInputDocument solrInputDocument = new SolrInputDocument()

                    solrInputDocuments.addField("id", pageContent.getParent().getPath())
                    solrInputDocuments.addField("url", pageContent.getParent().getPath() + ".html")

                    ValueMap pageContentProperties = pageContent.getValueMap()

                    solrInputDocuments.addField("title", pageContentProperties.get("jcr:title", String) ?: pageContent.getParent().getName())

                    solrInputDocuments.addField("description", pageContentProperties.get("jcr:description", String))
                    solrInputDocuments.addField("summary", pageContentProperties.get("summary", String))
                    solrInputDocuments.addField("publishedDate", pageContentProperties.get("publishdate", String))
                    solrInputDocuments.addField("lastModifiedDate", pageContentProperties.get("cq:lastModified", Calendar))
                    solrInputDocuments.addField("resourceType", pageContentProperties.get("sling:resourceType", String))
                    solrInputDocuments.addField("tags","")

                    // TODO: what is <<
                    solrInputDocuments << solrInputDocument
                }
            }
        }

        return solrInputDocuments
    }
}
