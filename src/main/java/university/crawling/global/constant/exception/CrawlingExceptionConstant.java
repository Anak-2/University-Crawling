package university.crawling.global.constant.exception;

public enum CrawlingExceptionConstant {

    CRAWLING_PARSE_ERROR("JSON 파싱 도중 에러가 발생했습니다");
    private final String message;

    CrawlingExceptionConstant(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
