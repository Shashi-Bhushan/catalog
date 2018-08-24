package in.shabhushan.catalog.dto;

import java.util.List;

/**
 * @author Shashi Bhushan
 * <p>
 * Change History:
 * Revision         Date            Dev             Comment
 * ----------------------------------------------------------------------------------------
 */
public class Books {
    List<Book> book;

    public List<Book> getBook() {
        return book;
    }

    public void setBook(List<Book> book) {
        this.book = book;
    }
}
