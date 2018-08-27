package in.shabhushan.catalog.replication.listener;

import com.day.cq.replication.ReplicationAction;
import in.shabhushan.catalog.constants.CommonConstants;
import in.shabhushan.catalog.replication.jobs.SolrIndexJobConsumer;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

/**
 * @author Shashi Bhushan
 */
@Component(
    metatype = true,
    immediate = true,
    label = "Catalog Page Replication Event Listener",
    description = "Event handler for page replication events",
    policy = ConfigurationPolicy.REQUIRE
)
@Service
@Properties({
    @Property(
        name = EventConstants.EVENT_TOPIC,
        value = ReplicationAction.EVENT_TOPIC),
    @Property(
        label = "Event Filters",
        value = "(paths=/content/catalog*)",
        description = "[Optional] Event Filters used to further restrict this event handler.",
        name = EventConstants.EVENT_FILTER)
})
public class CatalogPageReplicationEventHandler implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogPageReplicationEventHandler.class);

    @Reference
    private JobManager jobManager;

    @Override
    public void handleEvent(Event event) {
        LOG.debug("CatalogPageReplicationEventHandler#handleEvent Started");

        try {
            // Get the Replication Action
            ReplicationAction action = ReplicationAction.fromEvent(event);

            if (null != action) {
                // Add to Sling Job Queue
                this.jobManager.addJob(SolrIndexJobConsumer.SOLR_SEARCH_INDEXING_TOPIC,
                    Collections.singletonMap(CommonConstants.REPLICATION_ACTION, action));
            }
        } catch (Exception cause) {
            LOG.error("Exception occurred in handling replication event for indexing : {} ", cause);
        }
        LOG.debug("CatalogPageReplicationEventHandler#handleEvent Finished");
    }
}
