package ERUTY.platform.repository;

import ERUTY.platform.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    Page<Item> findItemsByDesignNameContaining(String searchKeyword, Pageable pageable);
    Item findItemById(String id);
    List<Item> findItemsByMemberId(String memberId);
}
