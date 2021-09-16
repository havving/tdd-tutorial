package com.havving.membership.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author HAVVING
 * @since 2021-09-16
 */
@Getter
@RequiredArgsConstructor
public enum MembershipType {

    NAVER("NAVER"), LINE("LINE"), KAKAO("KAKAO");

    private final String companyName;
}
