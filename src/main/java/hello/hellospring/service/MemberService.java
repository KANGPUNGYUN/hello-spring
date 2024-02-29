package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    // 기존코드
    // private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 변경코드
    private final MemberRepository memberRepository;

    // 의존성 주입
    // memberRepository를 new를 통해 인스턴스를 직접 생성하는 것이 아니라 외부에서 넣어 줄수 있도록 함
    // 그 이유는 memberRepository를 new를 통해 생성하면 기존의 객체가 아닌 새로운 인스턴스가 생성되기 때문에 다른 곳에서 new로 생성한 인스턴스와 다른 인스턴스가 된다.
    // MemberService의 입장에서 직접 new를 사용하지 않고 MemberRepository를 외부에서 주입한다.

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member){
//        AOP를 적용하여 아래의 주석을 사용하지 않아도 됨

//        long start = System.currentTimeMillis();
//        try {
//            validateExistMemberName(member); //중복 회원 검증 memberRepository.save(member);
//            return member.getId();
//        } finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish - start;
//            System.out.println("join " + timeMs + "ms");
//        }

        validateExistMemberName(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 이름 검증
    private void validateExistMemberName(Member member){
        memberRepository.findByName(member.getName())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원 이름입니다.");
            });
    }

    /**
     *  전체회원 조회
     */
    public List<Member> findMembers(){
//        AOP를 적용하여 아래의 주석을 사용하지 않아도 됨

//        long start = System.currentTimeMillis();
//        try {
//            return memberRepository.findAll();
//        } finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish - start;
//            System.out.println("findMembers " + timeMs + "ms");
//        }
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
