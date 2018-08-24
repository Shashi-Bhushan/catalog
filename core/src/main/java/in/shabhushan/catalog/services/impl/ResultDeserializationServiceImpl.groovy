package in.shabhushan.catalog.services.impl

import aQute.bnd.annotation.component.Deactivate
import com.google.gson.Gson
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import in.shabhushan.catalog.services.ResultDeserializationService
import org.apache.felix.scr.annotations.Activate
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Service

/**
 * @author Shashi Bhushan
 *
 * Change History:
 * Revision         Date            Dev             Comment
 * ----------------------------------------------------------------------------------------
 *
 */
@Component(
    label = "Http Service",
    immediate = true,
    metatype = true,
    enabled = true
)
@Service(ResultDeserializationService)
@Slf4j
@CompileStatic
class ResultDeserializationServiceImpl implements ResultDeserializationService {
    private Gson gson

    @Override
    public <T extends Object> T deserializeResult(String responseBody, Class<T> declaredType) {
        return gson.fromJson(responseBody, declaredType)
    }

    /**
     * Activate method.
     */
    @Activate
    public void activate()
    {
        this.gson = new Gson();
    }

    @Deactivate
    public void deactivate()
    {
        this.gson = null;
    }
}
