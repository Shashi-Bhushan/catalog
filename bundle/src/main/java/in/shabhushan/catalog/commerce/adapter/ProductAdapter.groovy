package in.shabhushan.catalog.commerce.adapter

/**
 * Created by Shashi Bhushan
 *      on 3/31/2017
 *      for catalog
 */
class ProductAdapter {

    String imageSource

    String title
    String description
    String rating
    String starRating
    String summary
    String path
    Date publishedDate
    int noOfPages
    int price


    ProductAdapter setTitle(String title) {
        this.title = title
        this
    }

    ProductAdapter setDescription(String description) {
        this.description = description
        this
    }

    ProductAdapter setRating(String rating) {
        this.rating = rating
        this
    }

    ProductAdapter setStarRating(String starRating) {
        this.starRating = starRating
        this
    }

    ProductAdapter setPrice(String price) {
        this.price = price as Integer
        this
    }

    ProductAdapter setNoOfPages(int noOfPages) {
        this.noOfPages = noOfPages
        this
    }

    ProductAdapter setSummary(String summary) {
        this.summary = summary
        this
    }

    ProductAdapter setPath(String path) {
        this.path = path
        this
    }

    ProductAdapter setPublishedDate(Date date) {
        this.publishedDate = date
        this
    }

    String getPublishedDate() {
        publishedDate.format("MMM d, YYYY")
    }

    ProductAdapter setImageSource(String imageSource) {
        this.imageSource = imageSource
        this
    }
}
