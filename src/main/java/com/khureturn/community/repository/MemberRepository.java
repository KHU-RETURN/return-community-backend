package com.khureturn.community.repository;

import com.khureturn.community.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
<<<<<<< HEAD
=======
import java.util.Optional;
>>>>>>> f3efc04018e9c94e60ee77a0deb5d92d7c626e2e

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByStudentId(String studentId);
<<<<<<< HEAD
=======
    Optional<Member> findByGoogleSub(String googleSub);

    Boolean existsByMemberId(Long memberId);

    Boolean existsByGoogleSub(String googleSub);

    Optional<Member> findByEmail(String email);

    Boolean existsByEmail(String email);







>>>>>>> f3efc04018e9c94e60ee77a0deb5d92d7c626e2e

}