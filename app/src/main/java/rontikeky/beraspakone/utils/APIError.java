package rontikeky.beraspakone.utils;

/**
 * Created by Acer on 2/19/2018.
 */

public class APIError {
    private int statusCode;
    private String endpoint;
    private String message;

    public APIError() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getMessage() {
        return message;
    }
}
