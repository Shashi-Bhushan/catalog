package in.shabhushan.catalog.dto;

/**
 * @author Shashi Bhushan
 * <p>
 * Change History:
 * Revision         Date            Dev             Comment
 * ----------------------------------------------------------------------------------------
 */
public class Book {
    private String small_image_url;
    private String num_pages;
    private String description;
    private String publication_day;
    private String publication_month;
    private String publication_year;
    private String average_rating;
    private String title;

    public String getSmall_image_url() {
        return small_image_url;
    }

    public void setSmall_image_url(String small_image_url) {
        this.small_image_url = small_image_url;
    }

    public String getNum_pages() {
        return num_pages;
    }

    public void setNum_pages(String num_pages) {
        this.num_pages = num_pages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublication_day() {
        return publication_day;
    }

    public void setPublication_day(String publication_day) {
        this.publication_day = publication_day;
    }

    public String getPublication_month() {
        return publication_month;
    }

    public void setPublication_month(String publication_month) {
        this.publication_month = publication_month;
    }

    public String getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(String publication_year) {
        this.publication_year = publication_year;
    }

    public String getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(String average_rating) {
        this.average_rating = average_rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
