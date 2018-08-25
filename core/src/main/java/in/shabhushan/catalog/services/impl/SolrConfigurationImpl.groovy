package in.shabhushan.catalog.services.impl

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import in.shabhushan.catalog.services.SolrConfiguration
import org.apache.felix.scr.annotations.Activate
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Modified
import org.apache.felix.scr.annotations.Properties
import org.apache.felix.scr.annotations.Property
import org.apache.sling.commons.osgi.PropertiesUtil
import org.osgi.service.component.ComponentContext

/**
 * @author Shashi Bhushan
 */
@Component(
    label = "Solr Configuration",
    immediate = true,
    metatype = true,
    enabled = true,
    description = "Configurations for Solr Server"
)
@Properties([
    @Property(
        name = SOLR_PROTOCOL,
        label = "Solr Server Protocol"
    ),
    @Property(
        name = SOLR_SERVER_NAME,
        label = "Solr Server Name"
    ),
    @Property(
        name = SOLR_SERVER_PORT,
        label = "Solr Server Port"
    ),
    @Property(
        name = SOLR_SERVER_COLLECTION_NAME,
        label = "Solr Server Collection Name"
    ),
    @Property(
        name = CONTENT_PAGE_PATH,
        label = "Content Page Path"
    ),
    @Property(
        name = RESOURCE_TYPE,
        label = "Resource Type"
    ),
    @Property(
        name = ALLOWED_TEMPLATES,
        label = "Allowed Templates",
        cardinality = Integer.MAX_VALUE
    )
])
@Slf4j
@CompileStatic
class SolrConfigurationImpl implements SolrConfiguration {

    public static final String SOLR_PROTOCOL = "solr.protocol"
    public static final String SOLR_SERVER_NAME = "solr.server.name"
    public static final String SOLR_SERVER_PORT = "solr.server.port"
    public static final String SOLR_SERVER_COLLECTION_NAME = "solr.server.collection.name"
    public static final String CONTENT_PAGE_PATH = "content.path"
    public static final String RESOURCE_TYPE = "resource.type"
    public static final String ALLOWED_TEMPLATES = "allowed.templates"

    @Activate
    protected void activate(final ComponentContext context) {
        log.info("${this.class.name} : Activate Method.")
        this.modified(context)
        log.info("${this.class.name} : Activate Method End.")
    }

    @Modified
    protected void modified(final ComponentContext context) {
        log.info("${this.class.name} : Modified Method.")
        final Dictionary<?, ?> config = context.properties

        // Synthetic Accessors of Instance Variables
        setSolrProtocol(PropertiesUtil.toString(config.get(SOLR_PROTOCOL), ""))
        setSolrServerName(PropertiesUtil.toString(config.get(SOLR_SERVER_NAME), ""))
        setSolrServerPort(PropertiesUtil.toString(config.get(SOLR_SERVER_PORT), ""))
        setSolrCollectionName(PropertiesUtil.toString(config.get(SOLR_SERVER_COLLECTION_NAME), ""))
        setContentPagePath(PropertiesUtil.toString(config.get(CONTENT_PAGE_PATH), ""))
        setContentPagePath(PropertiesUtil.toString(config.get(CONTENT_PAGE_PATH), ""))
        setResourceType(PropertiesUtil.toString(config.get(RESOURCE_TYPE), ""))
        setAllowedTemplates(PropertiesUtil.toStringArray(config.get(ALLOWED_TEMPLATES), null))

        log.info("${this.class.name} : Modified Method End.")
    }

    String solrProtocol
    String solrServerName
    String solrServerPort
    String solrCollectionName
    String contentPagePath
    String resourceType
    String[] allowedTemplates
}
