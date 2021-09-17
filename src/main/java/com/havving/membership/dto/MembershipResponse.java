package com.havving.membership.dto;

import com.havving.membership.entity.Membership;
import com.havving.membership.enums.MembershipType;
import com.havving.membership.exception.MembershipErrorResult;
import com.havving.membership.exception.MembershipException;
import com.havving.membership.repository.MembershipRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author HAVVING
 * @since 2021-09-17
 */
@Getter
@Builder
@RequiredArgsConstructor
public class MembershipResponse {

    private final Long id;
    private final MembershipType membershipType;
}

@Service
@RequiredArgsConstructor
class MembershipService {

    private final MembershipRepository membershipRepository;

    public MembershipResponse addMembership(final String userId, final MembershipType membershipType, final Integer point) {
        final Membership result = membershipRepository.findByUserIdAndMembershipType(userId, membershipType);
        if (result != null)
            throw new MembershipException(MembershipErrorResult.DUPLICATED_MEMBERSHIP_REGISTER);

        final Membership membership = Membership.builder()
                .userId(userId)
                .point(point)
                .membershipType(membershipType)
                .build();

        membershipRepository.save(membership);

        return null;
    }
}
