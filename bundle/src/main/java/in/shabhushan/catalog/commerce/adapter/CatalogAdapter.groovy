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
    String summary
    Date publishedDate
    int noOfPages
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

    CatalogAdapter setPrice(String price) {
        this.price = price as Integer
        this
    }

    CatalogAdapter setNoOfPages(int noOfPages) {
        this.noOfPages = noOfPages
        this
    }

    CatalogAdapter setSummary(String summary) {
        this.summary = summary
        this
    }

    CatalogAdapter setPublishedDate(Date date) {
        this.publishedDate = date
        this
    }

    String getPublishedDate() {
        publishedDate.format("MMM d, YYYY")
    }

    CatalogAdapter setImageSource(String imageSource) {
        this.imageSource = imageSource
        this
    }
}
