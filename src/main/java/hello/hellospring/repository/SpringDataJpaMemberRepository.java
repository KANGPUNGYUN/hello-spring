package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface SpringDataJpaMemberRepository extends JpaRepository<Member,
        Long>, MemberRepository {
    // JPQL select m from Member m where m.name = ? 을 조회하는 spring data jpa 기능을 아래와 같이 제공
    @Override
    Optional<Member> findByName(String name);
}