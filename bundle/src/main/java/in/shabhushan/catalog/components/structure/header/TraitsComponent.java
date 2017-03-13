package in.shabhushan.catalog.components.structure.header;

import com.citytechinc.cq.component.annotations.Component;
import com.citytechinc.cq.component.annotations.DialogField;
import com.citytechinc.cq.component.annotations.widgets.TextField;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;

/**
 * Created by Shashi Bhushan
 *      on 3/10/2017.
 */
@Component(value = "TraitsComponent")
public class TraitsComponent {

    @DialogField(fieldLabel = "TraitsComponent", fieldDescription = "Our title")
    @TextField
    private final String title;

    public TraitsComponent(SlingHttpServletRequest request) {
        ValueMap properties = request.getResource().adaptTo(ValueMap.class);
        this.title = properties.get("title", "DEFAULT TITLE");
    }

    public String getTitle() {
        return title;
    }
}
