package com.havving.membership.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author HAVVING
 * @since 2021-09-17
 */
@Getter
@RequiredArgsConstructor
public class MembershipException extends RuntimeException {

    private final MembershipErrorResult errorResult;
}
