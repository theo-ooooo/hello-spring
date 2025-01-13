package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        // given
        Member member = getMember("spring");
        // when

        long saveId = memberService.join(member);
        // then
        Member findMember = memberService.findMember(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
     void 중복_회원_예외() {
        // given
        Member member1 = getMember("spring");
        Member member2 = getMember("spring");

        // when
        memberService.join(member1);


        // then

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

    @Test
    void 전체_유저_리스트() {
        // given
        Member member1 = getMember("spring");
        Member member2 = getMember("spring2");
        // when
        memberService.join(member1);
        memberService.join(member2);

        // then
        List<Member> members = memberService.findMembers();
        assertThat(members.size()).isEqualTo(2);
    }

    private static Member getMember(String name) {
        Member member1 = new Member();
        member1.setName(name);
        return member1;
    }

    @Test
    void 단일_유저_검색() {

        // given
        Member member1 = getMember("spring2");
        // when
        long setId = memberService.join(member1);

        // then
        Optional<Member> member = memberService.findMember(setId);

        member.ifPresentOrElse(m -> assertThat(m.getName()).isEqualTo(member1.getName()), Assertions::fail);

    }
}