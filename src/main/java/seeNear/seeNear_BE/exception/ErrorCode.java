package seeNear.seeNear_BE.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //400
    MISMATCH_CODE(BAD_REQUEST,"code is not correct"),
    INVALID_TOKEN(BAD_REQUEST,"token is invalid"),
    MISMATCH_INFO(BAD_REQUEST,"There is no data related to request"),

    //401
    MEMBER_NOT_FOUND(UNAUTHORIZED,"user is not found"),
    INVALID_TOKEN_INFO(UNAUTHORIZED,"token information is invalid"),
    HEADER_TOKEN_NOT_FOUND(UNAUTHORIZED,"token is not found in Bearer authorization header"),

    //403
    INVALID_AUTHORITY(FORBIDDEN,"user is not authorized"),

    //409
    DUPLICATED_MEMBER(CONFLICT,"user is already existed")

    ;


    private final HttpStatus httpStatus;
    private final String detail;
}
