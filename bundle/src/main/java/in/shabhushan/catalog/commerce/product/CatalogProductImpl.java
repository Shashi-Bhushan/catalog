package in.shabhushan.catalog.commerce.product;

import com.adobe.cq.commerce.common.AbstractJcrProduct;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * Created by Shashi Bhushan
 *      on 13/3/17.
 */
public class CatalogProductImpl extends AbstractJcrProduct {

    public static final String PN_IDENTIFIER = "identifier";

    public static final String PN_PRICE = "price";

    protected final ResourceResolver resourceResolver;
    protected final PageManager pageManager;
    protected final Page productPage;
    protected String brand = null;

    public CatalogProductImpl(Resource resource) {
        super(resource);

        resourceResolver = resource.getResourceResolver();
        pageManager = resourceResolver.adaptTo(PageManager.class);
        productPage = pageManager.getContainingPage(resource);
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
