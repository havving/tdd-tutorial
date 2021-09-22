package com.havving.membership.service;

import com.havving.membership.dto.MembershipAddResponse;
import com.havving.membership.dto.MembershipDetailResponse;
import com.havving.membership.entity.Membership;
import com.havving.membership.enums.MembershipType;
import com.havving.membership.exception.MembershipErrorResult;
import com.havving.membership.exception.MembershipException;
import com.havving.membership.repository.MembershipRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author HAVVING
 * @since 2021-09-17
 */
@ExtendWith(MockitoExtension.class)
public class MembershipServiceTest {

    @Mock
    private MembershipRepository membershipRepository;
    @Mock
    private RatePointService ratePointService;
    @InjectMocks
    private MembershipService target;

    private final String userId = "userId";
    private final MembershipType membershipType = MembershipType.NAVER;
    private final Integer point = 10000;
    private final Long membershipId = -1L;

    // 멤버십 등록
    @Test
    public void failedMembership_isExist() {
        // given
        doReturn(Membership.builder().build())
                .when(membershipRepository).findByUserIdAndMembershipType(userId, membershipType);

        // when
        final MembershipException result = assertThrows(MembershipException.class,
                () -> target.addMembership(userId, membershipType, point));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MembershipErrorResult.DUPLICATED_MEMBERSHIP_REGISTER);
    }

    @Test
    public void successMembership() {
        // given
        doReturn(null).when(membershipRepository).findByUserIdAndMembershipType(userId, membershipType);
        doReturn(membership()).when(membershipRepository).save(any(Membership.class));

        // when
//        final Membership result = target.addMembership(userId, membershipType, point);
        final MembershipAddResponse result = target.addMembership(userId, membershipType, point);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getMembershipType()).isEqualTo(MembershipType.NAVER);

        // verify
        verify(membershipRepository, times(1)).findByUserIdAndMembershipType(userId, membershipType);
        verify(membershipRepository, times(1)).save(any(Membership.class));
    }

    // 멤버십 전체 조회
    @Test
    public void getMembership() {
        // given
        doReturn(Arrays.asList(
                Membership.builder().build(),
                Membership.builder().build(),
                Membership.builder().build()
        )).when(membershipRepository).findAllByUserId(userId);

        // when
        final List<MembershipDetailResponse> result = target.getMembershipList(userId);

        // then
        assertThat(result.size()).isEqualTo(3);
    }

    // 멤버십 상세 조회
    @Test
    public void failedMembershipDetail_isNotExist() {
        // given
        doReturn(null).when(membershipRepository).findByUserIdAndMembershipType(userId, membershipType);

        // when
        final MembershipException result = assertThrows(MembershipException.class, () -> target.getMembership(userId, membershipType));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MembershipErrorResult.MEMBERSHIP_NOT_FOUND);
    }

    @Test
    public void getMembershipDetail() {
        // given
        doReturn(Membership.builder()
                .id(-1L)
                .membershipType(MembershipType.NAVER)
                .point(point)
                .createdAt(LocalDateTime.now())
                .build()
        ).when(membershipRepository).findByUserIdAndMembershipType(userId, membershipType);

        // when
        final MembershipDetailResponse result = target.getMembership(userId, membershipType);

        // then
        assertThat(result.getMembershipType()).isEqualTo(MembershipType.NAVER);
        assertThat(result.getPoint()).isEqualTo(point);
    }

    // 멤버십 삭제
    @Test
    public void membershipDeleteFailed_isNotExist() {
        // given
        doReturn(Optional.empty()).when(membershipRepository).findById(membershipId);

        // when
        final MembershipException result = assertThrows(MembershipException.class, () ->
                target.removeMembership(membershipId, userId));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MembershipErrorResult.MEMBERSHIP_NOT_FOUND);
    }

    @Test
    public void membershipDeleteFailed_isNotOwner() {
        // given
        final Membership membership = membership();
        doReturn(Optional.of(membership)).when(membershipRepository).findById(membershipId);

        // when
        final MembershipException result = assertThrows(MembershipException.class, () ->
                target.removeMembership(membershipId, "notOwner"));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MembershipErrorResult.NOT_MEMBERSHIP_OWNER);
    }

    @Test
    public void membershipDelete() {
        // given
        final Membership membership = membership();
        doReturn(Optional.of(membership)).when(membershipRepository).findById(membershipId);

        // when
        target.removeMembership(membershipId, userId);
    }

    // 멤버식 포인트 적립
    @Test
    public void membershipPointFailed_isNotExist() {
        // given
        doReturn(Optional.empty()).when(membershipRepository).findById(membershipId);

        // when
        final MembershipException result = assertThrows(MembershipException.class, () ->
                target.accumulateMembershipPoint(membershipId, userId, 10000));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MembershipErrorResult.MEMBERSHIP_NOT_FOUND);
    }

    @Test
    public void membershipPointFailed_isNotOwner() {
        // given
        final Membership membership = membership();
        doReturn(Optional.of(membership)).when(membershipRepository).findById(membershipId);

        // when
        final MembershipException result = assertThrows(MembershipException.class, () ->
                target.accumulateMembershipPoint(membershipId, "notowner", 10000));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MembershipErrorResult.NOT_MEMBERSHIP_OWNER);
    }

    @Test
    public void membershipPoint() {
        // given
        final Membership membership = membership();
        doReturn(Optional.of(membership)).when(membershipRepository).findById(membershipId);

        // when
        target.accumulateMembershipPoint(membershipId, userId, 10000);
    }


   private Membership membership() {
        return Membership.builder()
                .id(-1L)
                .userId(userId)
                .point(point)
                .membershipType(MembershipType.NAVER)
                .build();
    }

}
