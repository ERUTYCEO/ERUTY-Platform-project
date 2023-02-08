package ERUTY.platform.repository;

import ERUTY.platform.domain.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends MongoRepository<Member, String> {

     Member findMemberByEmail(String email);
     List<Member> findMembersByEmail(String email);
     List<Member> findAllByOrderByMarketingOkDesc();
     Member findMemberById(String memberId);
}
