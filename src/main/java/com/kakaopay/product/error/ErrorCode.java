package com.kakaopay.product.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    REQUEST_SUCCESS("SUCCESS", "000", "SUCCESS REQUEST"),
    TEMPORARY_SERVER_ERROR("FAIL", "400", "BAD REQUEST"),
    ALREADY_REGISTER_ERROR("FAIL", "998", "This product has already been registered"),
    SOLD_OUT_ERROR("FAIL", "999", "sold-out");

    private String result;
    private String errCode;
    private String errMsg;
}