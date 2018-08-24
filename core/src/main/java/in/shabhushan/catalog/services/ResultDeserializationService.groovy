package in.shabhushan.catalog.services

/**
 * @author Shashi Bhushan
 */
interface ResultDeserializationService {
    public <T extends Object> T deserializeResult(final String responseBody, final Class<T> declaredType)
}
