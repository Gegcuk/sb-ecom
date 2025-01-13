    package uk.gegc.ecommerce.sbecom.exception;

    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.validation.FieldError;
    import org.springframework.web.bind.MethodArgumentNotValidException;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.RestControllerAdvice;
    import uk.gegc.ecommerce.sbecom.dto.response.APIResponse;

    import java.util.HashMap;
    import java.util.Map;

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException exception){
            Map<String, String> response = new HashMap<>();
            exception.getBindingResult().getAllErrors().forEach(err -> {
                String fieldName = ((FieldError) err).getField();
                String message = err.getDefaultMessage();
                response.put(fieldName, message);
            });
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException exception){
            APIResponse apiResponse = new APIResponse(exception.getMessage(), false);
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(APIException.class)
        public ResponseEntity<APIResponse> myAPIexception(APIException exception){
            APIResponse apiResponse = new APIResponse(exception.getMessage(), false);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }



    }
