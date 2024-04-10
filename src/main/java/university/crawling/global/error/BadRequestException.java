package university.crawling.global.error;

import lombok.Getter;

import static university.crawling.global.constant.exception.StatusCodeConstant.BAD_REQUEST;

@Getter
public class BadRequestException extends RuntimeException {

    private final String statusCode = BAD_REQUEST.statusCode();
    private final String message;

    public BadRequestException(final String message) {
        this.message = message;
    }
}