package com.havving.membership.controller;

import com.havving.membership.common.DefaultRestController;
import com.havving.membership.dto.MembershipAddResponse;
import com.havving.membership.dto.MembershipDetailResponse;
import com.havving.membership.dto.MembershipRequest;
import com.havving.membership.enums.MembershipType;
import com.havving.membership.service.MembershipService;
import com.havving.membership.validation.ValidationGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.havving.membership.constants.MembershipConstants.USER_ID_HEADER;

/**
 * @author HAVVING
 * @since 2021-09-18
 */
@RestController
@RequiredArgsConstructor
public class MembershipController extends DefaultRestController {

    private final MembershipService membershipService;

    @PostMapping("/api/v1/membership")
    public ResponseEntity<MembershipAddResponse> addMembership(
            @RequestHeader(USER_ID_HEADER) final String userId,
            @RequestBody @Validated(ValidationGroups.MembershipAddMarker.class) final MembershipRequest membershipRequest) {

        final MembershipAddResponse membershipResponse =
                membershipService.addMembership(userId, membershipRequest.getMembershipType(), membershipRequest.getPoint());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(membershipResponse);
    }

    @GetMapping("/api/v1/membership/list")
    public ResponseEntity<List<MembershipDetailResponse>> getMembershipList(
            @RequestHeader(USER_ID_HEADER) final String userId) {

        return ResponseEntity.ok(membershipService.getMembershipList(userId));
    }

    @GetMapping("/api/v1/membership")
    public ResponseEntity<MembershipDetailResponse> getMembership(
            @RequestHeader(USER_ID_HEADER) final String userId,
            @RequestParam final MembershipType membershipType) {

        return ResponseEntity.ok(membershipService.getMembership(userId, membershipType));
    }

    @DeleteMapping("/api/v1/membership/{id}")
    public ResponseEntity<Void> getMembership(
            @RequestHeader(USER_ID_HEADER) final String userId,
            @PathVariable final Long id) {

        membershipService.removeMembership(id, userId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/v1/membership/{id}/accumulate")
    public ResponseEntity<Void> accumulateMembershipPoint(
            @RequestHeader(USER_ID_HEADER) final String userId,
            @PathVariable final Long id,
            @RequestBody @Validated(ValidationGroups.MembershipAccumulateMarker.class) final MembershipRequest membershipRequest) {

        membershipService.accumulateMembershipPoint(id, userId, membershipRequest.getPoint());

        return ResponseEntity.noContent().build();
    }

}
