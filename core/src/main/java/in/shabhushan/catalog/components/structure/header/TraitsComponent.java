package in.shabhushan.catalog.components.structure.header;

import com.citytechinc.cq.component.annotations.Component;
import com.citytechinc.cq.component.annotations.DialogField;
import com.citytechinc.cq.component.annotations.widgets.MultiField;
import com.citytechinc.cq.component.annotations.widgets.PathField;
import com.citytechinc.cq.component.annotations.widgets.RichTextEditor;
import com.citytechinc.cq.component.annotations.widgets.TextField;
import com.citytechinc.cq.component.annotations.widgets.rte.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Shashi Bhushan
 *      on 3/10/2017.
 */
@Component(value="Traits Component")
public class TraitsComponent {

    private static final String DEFAULT_TITLE = "Default Article Title";

    @DialogField(fieldLabel="Article Title", defaultValue=DEFAULT_TITLE)
    private final String title;

    @DialogField(fieldLabel="Article Content", fieldDescription="Full text of the article.")
    @RichTextEditor(
        edit=@Edit(),
        image=@Image(),
        spellcheck=@SpellCheck(),
        subsuperscript=@SubSuperscript(),
        links=@Links(),
        lists=@Lists(),
        justify=@Justify()
    )
    private final String content;

    @DialogField(fieldLabel="Related Items", fieldDescription="A list of links to internal pages related to the Article.")
    @MultiField
    @PathField
    private final List<String> relatableItems;

    public TraitsComponent(SlingHttpServletRequest request) {
        ValueMap properties = request.getResource().adaptTo(ValueMap.class);

        this.title = properties.get("title", DEFAULT_TITLE);
        this.content = properties.get("content", "");

        this.relatableItems = Arrays.asList(properties.get("relatableItems", new String[0]));
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getRelatableItems() {
        return relatableItems;
    }

}
