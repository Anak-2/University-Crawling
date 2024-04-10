package university.crawling.global.error;

import lombok.Getter;

import static university.crawling.global.constant.exception.StatusCodeConstant.NOT_FOUND;

@Getter
public class NotFoundException extends RuntimeException {

    private final String statusCode = NOT_FOUND.statusCode();
    private final String message;

    public NotFoundException(final String message) {
        this.message = message;
    }
}

