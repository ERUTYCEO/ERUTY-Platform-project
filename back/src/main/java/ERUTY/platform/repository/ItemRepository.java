package ERUTY.platform.repository;

import ERUTY.platform.domain.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    List<Item> findItemsByDesignName(String email);
    Page<Item> findItemsByDesignNameContaining(String searchKeyword, Pageable pageable);
    Item findItemByDesignName(String designName);
}
