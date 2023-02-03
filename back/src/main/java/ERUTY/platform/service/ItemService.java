package ERUTY.platform.service;

import ERUTY.platform.form.ItemForm;
import ERUTY.platform.domain.Item;
import ERUTY.platform.form.findItemForm;
import ERUTY.platform.repository.ItemRepository;
import ERUTY.platform.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import javax.servlet.http.HttpSession;
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

    public void validateDuplicateMember(ItemForm itemForm) {
        List<Item> items = itemRepository.findItemsByDesignName(itemForm.getDesignName());

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

    public List<Item> findItemsByCreator(String creator){
        return itemRepository.findItemsByCreator(creator);
    }
    public Item iteminfo(String designName){
        return itemRepository.findItemByDesignName(designName);
    }

    public List<Item> getItemList() {
        return null;
    }

    public Item findOne(String itemId) {
        Item item = itemRepository.findItemById(itemId);
//        item.setViews(item.getViews() + 1);
//        itemRepository.save(item);

        return item;
    }
}
