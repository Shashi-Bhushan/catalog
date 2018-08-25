package in.shabhushan.catalog.services

import in.shabhushan.catalog.dto.Book

/**
 * @author Shashi Bhushan
 */
interface CreateProductNodesService {
    void createProducts(PrintWriter writer, String productPath, List<Book> books)
}
