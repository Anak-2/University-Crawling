package university.crawling.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import university.crawling.global.response.ErrorResponse;

import static university.crawling.global.constant.exception.SpringExceptionConstant.METHOD_ARGUMENT_TYPE_MISMATCH;

@Slf4j
@RestControllerAdvice
public class ApiResponseControllerAdvice {

    // 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorResponse handleException(final BadRequestException e) {
        log.info("BadRequestException={}", e.getMessage());
        return ErrorResponse.of(e.getStatusCode(), e.getMessage());
    }

    // 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleException(final NotFoundException e) {
        log.info("NotFoundException Exception={}", e.getMessage());
        return ErrorResponse.of(e.getStatusCode(), e.getMessage());
    }

    // 500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerException.class)
    public ErrorResponse handleInternalServerError(final InternalServerException e) {
        log.error("InternalServerException={}", e.getMessage());
        return ErrorResponse.of(e.getStatusCode(), e.getMessage());
    }
}
