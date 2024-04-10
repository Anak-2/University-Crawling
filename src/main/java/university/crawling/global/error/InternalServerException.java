package university.crawling.global.error;

import lombok.Getter;

import static university.crawling.global.constant.exception.StatusCodeConstant.INTERNAL_SERVER;

@Getter
public class InternalServerException extends RuntimeException {

    private final String statusCode = INTERNAL_SERVER.statusCode();
    private final String message;

    public InternalServerException(final String message) {
        this.message = message;
    }
}
