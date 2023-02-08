package ERUTY.platform.service;

import ERUTY.platform.domain.Item;
import ERUTY.platform.domain.Member;
import ERUTY.platform.form.findItemForm;
import ERUTY.platform.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(Item item) {
        validateDuplicateItem(item);
        itemRepository.save(item);
    }

    private void validateDuplicateItem(Item item) {
        List<Item> items = itemRepository.findItemsByDesignName(item.getDesignName());

        if(!items.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 이름의 디자인입니다.");
        }
    }

    public Page<Item> TotalItem(Pageable pageable){
        Page<Item> TotalItemList = itemRepository.findAll(pageable);
        return TotalItemList;
    }

    public Page<Item> searchItemList(findItemForm finditemForm, Pageable pageable){
        String searchKeyword = finditemForm.getSearchKeyword();
        Page<Item> searchitems = itemRepository.findItemsByDesignNameContaining(searchKeyword, pageable);
        if (searchitems.isEmpty()){
            throw new IllegalStateException("검색결과가 없습니다.");
        }
        return searchitems;
    }

    public List<Item> findItemsByCreator(String creator) {
        return itemRepository.findItemsByCreator(creator);
    }

    public Item iteminfo(String designName){
        return itemRepository.findItemByDesignName(designName);
    }

    public Item updateView(String itemId) {
        Item item = findItemById(itemId);
        item.viewPlusOne();
        itemRepository.save(item);
        return item;
    }

    public Item findItemById(String itemId) {
        Item item = itemRepository.findItemById(itemId);

        return item;
    }

    public List<Item> findUploadList(Member member) {

        List<String> uploadlist = member.getUploadList();
        List<Item> itemList = new ArrayList<>();

        for(int i = 0; i < uploadlist.size(); i++) {
            Item item = itemRepository.findItemById(uploadlist.get(i));

            itemList.add(item);
        }

        return itemList;
    }

    public int findLiked(Member member, String itemId) {
        List<String> likedList = member.getLikedList();

        if (likedList.contains(itemId)) {
            return 1;
        } else {
            return 0;
        }
    }
}
