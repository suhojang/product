package com.kakaopay.product.error;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private String result;
    private String errCode;
    private String errMsg;

    public ErrorResponse(ErrorCode code) {
        this.result = code.getResult();
        this.errCode = code.getErrCode();
        this.errMsg = code.getErrMsg();
    }

    public static ErrorResponse of(ErrorCode code) {
        return new ErrorResponse(code);
    }
}
