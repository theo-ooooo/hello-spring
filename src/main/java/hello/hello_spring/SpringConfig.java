package hello.hello_spring;
//import hello.hello_spring.repository.JdbcMemberRepository;
//import hello.hello_spring.repository.JdbcTemplateMemberRepository;
//import hello.hello_spring.repository.JpaMemberRepository;
import hello.hello_spring.aop.TimeTraceAop;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import hello.hello_spring.service.MemberService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final MemberRepository  memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

}