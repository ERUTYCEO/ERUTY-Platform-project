package ERUTY.platform.repository;

import ERUTY.platform.domain.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface MemberRepository extends MongoRepository<Member, String> {

     Member findMemberByEmail(String email);
     List<Member> findMembersByEmail(String email);
     List<Member> findAllByOrderByMarketingOkDesc();
     Member findMemberById(String memberId);
}
