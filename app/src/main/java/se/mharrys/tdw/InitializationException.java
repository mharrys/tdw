package se.mharrys.tdw;

/**
 * Describes exception occurring during creation of an object.
 */
public class InitializationException extends Exception {

    /**
     * Construct exception.
     *
     * @param message reason for exception
     */
    public InitializationException(String message) {
        super(message);
    }

    /**
     * Construct exception.
     *
     * @param message reason for exception
     * @param throwable chained exception
     */
    public InitializationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}