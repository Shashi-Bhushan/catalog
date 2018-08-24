package in.shabhushan.catalog.services

import in.shabhushan.catalog.dto.Book

/**
 * @author Shashi Bhushan
 */
interface CreateProductNodesService {
    void createProducts(String productPath, List<Book> books)
}
