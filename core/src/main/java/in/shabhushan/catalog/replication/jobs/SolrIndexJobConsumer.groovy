package in.shabhushan.catalog.replication.jobs

import com.day.cq.replication.ReplicationAction
import com.day.cq.replication.ReplicationActionType
import com.day.cq.wcm.api.NameConstants
import com.day.cq.wcm.api.Page
import in.shabhushan.catalog.constants.CommonConstants
import in.shabhushan.catalog.context.SolrContext
import in.shabhushan.catalog.services.ResourceResolverService
import in.shabhushan.catalog.services.impl.SolrConfigurationImpl
import org.apache.commons.lang3.StringUtils
import org.apache.felix.scr.annotations.Activate
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.ConfigurationPolicy
import org.apache.felix.scr.annotations.Properties
import org.apache.felix.scr.annotations.Property
import org.apache.felix.scr.annotations.Reference
import org.apache.felix.scr.annotations.Service
import org.apache.sling.api.resource.LoginException
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.ResourceResolver
import org.apache.sling.api.resource.ValueMap
import org.apache.sling.event.jobs.Job
import org.apache.sling.event.jobs.consumer.JobConsumer
import org.osgi.service.cm.ConfigurationAdmin
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.util.stream.Collectors

/**
 * @author Shashi Bhushan
 */
@Component(
    label = "Amazon Elastic Search Indexing JobConsumer",
    description = "Job consumer to handle page replication jobs and create/update/delete document index on Elastic Search",
    metatype = true,
    immediate = true,
    policy = ConfigurationPolicy.REQUIRE
)
@Service(JobConsumer)
@Properties(value = [
    @Property(
        name = JobConsumer.PROPERTY_TOPICS,
        value = SolrIndexJobConsumer.SOLR_SEARCH_INDEXING_TOPIC
    )
])
class SolrIndexJobConsumer implements JobConsumer {

    public static final String SOLR_SEARCH_INDEXING_TOPIC = "in/shabhushan/solr/jobs/indexing"

    private static final Logger LOG = LoggerFactory.getLogger(this.class)

    @Reference
    private ResourceResolverService resolverService

    @Override
    JobResult process(Job job) {
        LOG.info "${this.class.name} Started"

        ReplicationAction replicationAction = (ReplicationAction) job.getProperty(CommonConstants.REPLICATION_ACTION);
        if (null != replicationAction && null != replicationAction.getType()
            && StringUtils.isNotBlank(replicationAction.getPath())) {

            String replicationActionType = replicationAction.type as String

            String path = replicationAction.path

            LOG.info("Job retry count: $job.retryCount , Replication Action $replicationActionType , Path for action : $path")
            ResourceResolver resourceResolver = resolverService.resourceResolver

            if (isValidIndexingResource(resourceResolver, path)) {

                LOG.info "${this.class.name} Sleeping"
                Thread.sleep(1000*60*3)
                LOG.info "${this.class.name} Waking"

            } else {
                LOG.info("Index request is not for a valid resource completing the job");
            }

            return JobResult.OK
        } else {
            LOG.info("Could not get Replication Action $replicationAction , from job data completing this job")
            return JobResult.OK
        }
    }

    private boolean isValidIndexingResource(ResourceResolver resourceResolver, String location) {
        return true;//isPage(resourceResolver, location) && isSearchable(resourceResolver, location)
    }

    private boolean isSearchable(ResourceResolver resourceResolver, String location) {
        boolean result = false;
        Resource resource = resourceResolver.getResource(location + "/" + NameConstants.NN_CONTENT);
        if (null != resource) {
            ValueMap properties = resource.getValueMap();
            result = properties.get("searchable", Boolean.class) ?: false
        }
        LOG.debug("Indexing - isSearchable : {}", result);

        return result;
    }

    private boolean isPage(ResourceResolver resourceResolver, String location) {

        boolean result = false;

        Resource resource = resourceResolver.getResource(location);
        if (null != resource) {
            Page page = resource.adaptTo(Page.class);
            if (null != page){
                result = true
            }
            else {
                result = false
            }
            LOG.debug("Indexing - isPage : {}", result);
        }

        return result
    }
}
