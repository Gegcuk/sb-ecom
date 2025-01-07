package uk.gegc.ecommerce.sbecom.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class APIException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public APIException(String message) {
        super(message);
    }
}
