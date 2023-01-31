package ERUTY.platform.Exception;

import org.springframework.http.HttpStatus;

public enum MemberExceptionType implements BaseExceptionType {

    ALREADY_EXIST_EMAIL(600, HttpStatus.OK, "이미 존재하는 이메일입니다."),
    WRONG_PASSWORD(601, HttpStatus.OK, "비밀번호를 다시 확인해주십시오."),
    NOT_FOUND_MEMBER(602, HttpStatus.OK, "해당하는 회원 정보가 없습니다.");

    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    MemberExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
