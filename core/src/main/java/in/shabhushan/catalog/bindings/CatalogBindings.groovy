package in.shabhushan.catalog.bindings

import in.shabhushan.catalog.constants.ToolTipPlacement
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Service
import org.apache.sling.scripting.api.BindingsValuesProvider

import javax.script.Bindings

/**
 * @author Shashi Bhushan
 */
@Component
@Service
class CatalogBindings implements BindingsValuesProvider{

    @Override
    void addBindings(Bindings bindings) {
        bindings.put("tooltipPlacement", ToolTipPlacement.BOTTOM.toolTipPlacement)
        bindings.put("uuid", UUID.randomUUID().toString().replace("-",""))
    }
}
