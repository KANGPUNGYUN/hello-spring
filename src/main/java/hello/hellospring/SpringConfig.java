package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

// 자바 코드로 직접 스프링 빈 등록하는 방식
@Configuration
public class SpringConfig {

//    1. dataSource를 의존성 주입하여 사용한다.(JdbcMemberRepository, JdbcTemplateMemberRepository)

//    private DataSource dataSource;
//
//    @Autowired
//    public SpringConfig(DataSource dataSource){
//        this.dataSource = dataSource;
//    }

//    2. jpa와 같은 경우 dataSource가 아닌 EntityManager를 받아서 사용한다.(JpaMemberRepository)
//    *jpa는 sql 중심의 데이터 지향 프로그래밍에서 객체 지향 프로그래밍이 가능하다. jpa가 자바 ORM(객체-관계 매핑 기술) 표준 인터페이스이기 때문이다.

//    private EntityManager em;
//
//    @Autowired
//    public SpringConfig(EntityManager em){
//        this.em = em;
//    }

//    3. spring data jpa는 SpringDataJpaMemberRepository의 JpaRepository가 직접 인터페이스(MemberRepository)에 대한 구현체를 만들어내고 스프링 빈으로 등록을 할 수 있게 한다.

    private final MemberRepository memberRepository;


    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

//    @Bean
//    public TimeTraceAop timeTraceAop() {
//        return new TimeTraceAop();
//    }

/*    spring data jpa를 사용하기 전의 코드

    @Bean
   public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        // return new MemoryMemberRepository();
        // return new JdbcMemberRepository(dataSource);
        // return new JdbcTemplateMemberRepository(dataSource);
        // return new JpaMemberRepository(em);
    }
*/
}
