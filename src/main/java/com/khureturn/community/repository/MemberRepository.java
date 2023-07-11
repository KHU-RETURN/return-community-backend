package com.khureturn.community.repository;

import com.khureturn.community.domain.Member;
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

<<<<<<< HEAD

=======
    Optional<Member> findByPhoneNumber(String phoneNumber);
>>>>>>> f4de3852aba08c6b1fc00f0251e4e0564eec9985

}
