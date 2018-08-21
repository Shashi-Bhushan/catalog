package in.shabhushan.catalog.services

import org.apache.sling.api.resource.ResourceResolver

/**
 * @author Shashi Bhushan
 *
 * Change History:
 * Revision         Date            Dev             Comment
 * ----------------------------------------------------------------------------------------
 *
 */
interface UserManagerService {
    String getLoggedInUserID(ResourceResolver resourceResolver)
}

