package com.khureturn.community.repository;

import com.khureturn.community.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByStudentId(String studentId);
    Optional<Member> findByGoogleSub(String googleSub);

    Boolean existsByMemberId(Long memberId);

    Boolean existsByGoogleSub(String googleSub);

    Optional<Member> findByEmail(String email);

    Boolean existsByEmail(String email);








}
