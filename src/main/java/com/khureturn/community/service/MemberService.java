package com.khureturn.community.service;

import com.khureturn.community.domain.Member;
import com.khureturn.community.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {this.memberRepository = memberRepository;}
    public Long join(Member member){
        validateDuplilcateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public void validateDuplilcateMember(Member member){
        List<Member> findMembers= memberRepository.findByStudentId(member.getStudentId());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {return memberRepository.findAll();}

    public Optional<Member> findOne(Long id) {return memberRepository.findById(id);}
}
