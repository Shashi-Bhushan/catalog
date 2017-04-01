package in.shabhushan.catalog.commerce.adapter

/**
 * Created by Shashi Bhushan
 *      on 3/31/2017
 *      for catalog
 */
class CatalogAdapter {

    String imageSource

    String title
    String description
    String rating
    String starRating
    String color
    int price


    CatalogAdapter setTitle(String title) {
        this.title = title
        this
    }

    CatalogAdapter setDescription(String description) {
        this.description = description
        this
    }

    CatalogAdapter setRating(String rating) {
        this.rating = rating
        this
    }

    CatalogAdapter setStarRating(String starRating) {
        this.starRating = starRating
        this
    }

    CatalogAdapter setColor(String color) {
        this.color = color
        this
    }

    CatalogAdapter setPrice(String price) {
        this.price = price
        this
    }

    CatalogAdapter setImageSource(String imageSource) {
        this.imageSource = imageSource
        this
    }
}
