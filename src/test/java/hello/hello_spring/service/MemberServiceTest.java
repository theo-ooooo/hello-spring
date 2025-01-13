package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memoryMemberRepository;

    @BeforeEach
    public void beforeEach() {
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }

    @AfterEach
    public void afterEach() {
        memoryMemberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("spring");
        // when

        long saveId = memberService.join(member);
        // then
        Member findMember = memberService.findMember(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
     void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        // when
        memberService.join(member1);


        // then

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

    @Test
    void 전체_유저_리스트() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        Member member2 = new Member();
        member2.setName("spring2");
        // when
        memberService.join(member1);
        memberService.join(member2);

        // then
        List<Member> members = memberService.findMembers();
        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    void 단일_유저_검색() {

        // given
        Member member1 = new Member();
        member1.setName("spring4444");
        // when
        long setId = memberService.join(member1);

        // then
        Optional<Member> member = memberService.findMember(setId);

        member.ifPresentOrElse(m -> assertThat(m.getName()).isEqualTo(member1.getName()), Assertions::fail);

    }
}