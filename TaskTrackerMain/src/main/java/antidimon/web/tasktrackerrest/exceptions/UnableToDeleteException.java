package antidimon.web.tasktrackerrest.exceptions;

import java.io.Serial;


public class UnableToDeleteException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public UnableToDeleteException(String message) {
        super(message);
    }

}
