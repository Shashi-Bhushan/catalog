package in.shabhushan.catalog.commerce.adapter

/**
 * Created by Shashi Bhushan
 *      on 3/31/2017
 *      for catalog
 */
class CatalogAdapter {
    String title
    String rating

    CatalogAdapter setTitle(String title) {
        this.title = title
        this
    }

    CatalogAdapter setRating(String rating) {
        this.rating = rating
        this
    }
}
