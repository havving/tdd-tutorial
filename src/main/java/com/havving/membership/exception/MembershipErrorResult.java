package com.havving.membership.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author HAVVING
 * @since 2021-09-17
 */
@Getter
@RequiredArgsConstructor
public enum MembershipErrorResult {

    DUPLICATED_MEMBERSHIP_REGISTER(HttpStatus.BAD_REQUEST,
                                   "Duplicated Membership Register Request");

    private final HttpStatus httpStatus;
    private final String message;
}
