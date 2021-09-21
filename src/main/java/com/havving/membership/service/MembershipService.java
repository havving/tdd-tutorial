package com.havving.membership.service;

import com.havving.membership.dto.MembershipAddResponse;
import com.havving.membership.dto.MembershipDetailResponse;
import com.havving.membership.entity.Membership;
import com.havving.membership.enums.MembershipType;
import com.havving.membership.exception.MembershipErrorResult;
import com.havving.membership.exception.MembershipException;
import com.havving.membership.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author HAVVING
 * @since 2021-09-17
 */
@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepository;

    public MembershipAddResponse addMembership(final String userId, final MembershipType membershipType, final Integer point) {
        final Membership result = membershipRepository.findByUserIdAndMembershipType(userId, membershipType);
        if (result != null)
            throw new MembershipException(MembershipErrorResult.DUPLICATED_MEMBERSHIP_REGISTER);

        final Membership membership = Membership.builder()
                .userId(userId)
                .membershipType(membershipType)
                .point(point)
                .build();

        final Membership savedMembership = membershipRepository.save(membership);

        return MembershipAddResponse.builder()
                .id(savedMembership.getId())
                .membershipType(savedMembership.getMembershipType())
                .build();
    }

    public List<MembershipDetailResponse> getMembershipList(final String userId) {
        final List<Membership> membershipList = membershipRepository.findAllByUserId(userId);

        return membershipList.stream()
                .map(v -> MembershipDetailResponse.builder()
                        .id(v.getId())
                        .membershipType(v.getMembershipType())
                        .point(v.getPoint())
                        .createdAt(v.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public MembershipDetailResponse getMembership(final String userId, final MembershipType membershipType) {
        final Membership findResult = membershipRepository.findByUserIdAndMembershipType(userId, membershipType);
        if (findResult == null)
            throw new MembershipException(MembershipErrorResult.MEMBERSHIP_NOT_FOUND);

        return MembershipDetailResponse.builder()
                .id(findResult.getId())
                .membershipType(findResult.getMembershipType())
                .point(findResult.getPoint())
                .createdAt(findResult.getCreatedAt())
                .build();
    }

    public void removeMembership(final Long membershipId, final String userId) {
        final Optional<Membership> optionalMembership = membershipRepository.findById(membershipId);
        final Membership membership = optionalMembership.orElseThrow(() -> new
                MembershipException(MembershipErrorResult.MEMBERSHIP_NOT_FOUND));
        if (!membership.getUserId().equals(userId))
            throw new MembershipException(MembershipErrorResult.NOT_MEMBERSHIP_OWNER);

        membershipRepository.deleteById(membershipId);
    }
}
