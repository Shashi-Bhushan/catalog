package in.shabhushan.catalog.services.impl;

import groovy.transform.CompileStatic;
import groovy.util.logging.Slf4j;
import in.shabhushan.catalog.services.HttpService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Shashi Bhushan
 * <p>
 * Change History:
 * Revision         Date            Dev             Comment
 * ----------------------------------------------------------------------------------------
 */
@Component(
    label = "Http Service",
    immediate = true,
    metatype = false,
    enabled = true
)
@Service(HttpService.class)
public class HttpServiceImpl implements HttpService{

    private static final Logger LOG = LoggerFactory.getLogger(HttpServiceImpl.class);

    @Override
    public String get(String url) {
        String httpResponse = StringUtils.EMPTY;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet orderPlaced = new HttpGet(url);
            HttpResponse response = httpClient.execute(orderPlaced);

            InputStream stream = response.getEntity().getContent();

            httpResponse = IOUtils.toString(stream, "UTF-8");
        } catch (IOException e) {
            LOG.error("Not able to fetch URL");
        }

        return httpResponse;
    }
}
