package com.havving.membership.repository;

import com.havving.membership.entity.Membership;
import com.havving.membership.enums.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author HAVVING
 * @since 2021-09-16
 */
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    Membership findByUserIdAndMembershipType(final String userId, final MembershipType membershipType);
}
