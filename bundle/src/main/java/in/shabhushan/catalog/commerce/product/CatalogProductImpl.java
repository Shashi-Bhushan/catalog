package in.shabhushan.catalog.commerce.product;

import com.adobe.cq.commerce.common.AbstractJcrProduct;
import org.apache.sling.api.resource.Resource;

/**
 * Created by Shashi Bhushan
 *      on 13/3/17.
 */
public class CatalogProductImpl extends AbstractJcrProduct {

    public static final String PN_IDENTIFIER = "identifier";

    public CatalogProductImpl(Resource resource) {
        super(resource);
    }

    @Override
    public String getSKU() {
        String sku = getProperty(PN_IDENTIFIER, String.class);

        // Geometrixx products don't have unique ids for size, so append the size to the sku:
        String size = getProperty("size", String.class);

        if (size != null && size.length() > 0) {
            sku += "-" + size;
        }
        return sku;
    }
}
