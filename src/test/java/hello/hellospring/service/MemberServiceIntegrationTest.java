package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    // @Autowired MemoryMemberRepository memberRepository;  => Error: expected single matching bean but found 2(MemoryMemberRepository, SpringDataJpaMemberRepository) 해결
    @Autowired MemberRepository memberRepository;
//    테스트는 가장 끝단에 있어 다른 곳에서는 쓰이지 않기 때문에, 테스트 케이스를 할 경우 아래처럼 @BeforeEach를 통해 의존성 주입을 받지 않고, @Autowired로 받는 것이 더 편하다.

//    MemberService memberService;
//    MemoryMemberRepository memberRepository;
//    @BeforeEach
//    public void beforeEach() {
//        memberRepository = new MemoryMemberRepository();
//        memberService = new MemberService(memberRepository);
//    }

//    @Transactional를 사용하면 @AfterEach를 통한 테스트별 인스턴스 지우는 행위를 하지 않아도 된다.
//    @Transactional를 사용하지 않은 경우에는 실제 DB에 테스트 값이 올라가고 삭제되지 않지만, @Transactional를 이용하면 테스트를 돌린 뒤, Rollback해주게 된다.
//    @AfterEach
//    public void afterEach(){
//        memberRepository.clearStore();
//    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원이름_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원 이름입니다.");

        /*
        memberService.join(member1);
        try{
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원 이름입니다.");
        }
        */

        //then
    }

    @Test
    void findOne() {
    }
}