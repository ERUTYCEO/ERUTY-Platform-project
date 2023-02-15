package ERUTY.platform.service;

import ERUTY.platform.domain.Item;
import ERUTY.platform.domain.Member;
import ERUTY.platform.form.ItemForm;
import ERUTY.platform.form.findItemForm;
import ERUTY.platform.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public String[] createImagePathes(String imagePath){
        String[] parentPathAndNum = imagePath.split("\\*");
        String parentPath = parentPathAndNum[0];
        int numImage = Integer.parseInt(parentPathAndNum[1]);

        String[] imagePathes = new String[numImage];
        parentPath = parentPath.concat("/file");
        for(int i=0; i<numImage; i++){
            imagePathes[i] = parentPath.concat(String.valueOf(i));
        }
        return imagePathes;
    }

    public String registItem(ItemForm itemForm, HttpSession session) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(itemForm.getCreatedDate());

        String[] imagePathes = createImagePathes(itemForm.getImagePath());

        Item newItem = Item.builder()
                .designName(itemForm.getDesignName())
                .creator(itemForm.getCreator())
                .createdDate(date)
                .description(itemForm.getDescription())
                .price(itemForm.getPrice())
                .isOrigin(itemForm.isOrigin())
                .canModification(itemForm.isCanModification())
                .canCommercialUse(itemForm.isCanCommercialUse())
                .imagePathes(imagePathes)
                .modelPath(itemForm.getModelPath())
                .build();

        String memberId = (String)session.getAttribute("loginId");
        newItem.setMemberId(memberId);
        saveItem(newItem);

        return newItem.getId();
    }
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public Page<Item> TotalItem(Pageable pageable, String memberId) {
        Page<Item> TotalItemList = itemRepository.findAll(pageable);
        Iterator<Item> itemIterator = TotalItemList.iterator();

        return getItems(memberId, TotalItemList, itemIterator);
    }

    public Page<Item> searchItemList(findItemForm finditemForm, Pageable pageable, String memberId){
        String searchKeyword = finditemForm.getSearchKeyword();
        Page<Item> searchItems = itemRepository.findItemsByDesignNameContaining(searchKeyword, pageable);

        /*if (searchItems.isEmpty()){
            throw new IllegalStateException("검색결과가 없습니다.");
        }*/

        Iterator<Item> searchItemIterator = searchItems.iterator();

        return getItems(memberId, searchItems, searchItemIterator);
    }

    private Page<Item> getItems(String memberId, Page<Item> items, Iterator<Item> itemIterator) {
        while(itemIterator.hasNext()) {
            Item item = itemIterator.next();
            if(item.getLikedList().contains(memberId)) {
                item.setLiked(true);
            } else {
                item.setLiked(false);
            }

            log.info("현재 아이템 : " + item.getDesignName() + " 좋아요 표시 : " + item.isLiked());
        }

        return items;
    }

    public Item updateView(String itemId, HttpServletRequest request, HttpServletResponse response) {
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();

        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }

        if(oldCookie != null) {
            if(!oldCookie.getValue().contains(itemId)) {
                viewCountUp(itemId);

                oldCookie.setValue(oldCookie.getValue() + itemId);
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);

                response.addCookie(oldCookie);
            }
        } else {
            viewCountUp(itemId);

            Cookie newCookie = new Cookie("postView", itemId);
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);

            response.addCookie(newCookie);
        }

        Item item = findItemById(itemId);
        return item;
    }

    private void viewCountUp(String itemId) {
        Item item = findItemById(itemId);
        item.viewPlusOne();
        itemRepository.save(item);
    }

    public Item updateNumBuy(String itemId){
        Item item = findItemById(itemId);
        long numBuy = item.getNumBuy();
        item.setNumBuy(numBuy +1);
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

    public Item likedListUpdate(String itemId, String memberId) {
        Item item = itemRepository.findItemById(itemId);

        long likeCount = item.getLikes();

        if(item.getLikedList().contains(memberId)) {
            item.getLikedList().remove(memberId);
            item.setLikes(likeCount - 1);
        } else {
            item.getLikedList().add(memberId);
            item.setLikes(likeCount + 1);
        }

        log.info("좋아요한 Member : " + item.getLikedList());

        itemRepository.save(item);

        return item;
    }

    public boolean findLiked(String memberId, String itemId) {
        Item item = itemRepository.findItemById(itemId);
        List<String> likedList = item.getLikedList();

        if (likedList.contains(memberId)) {
            return true;
        } else {
            return false;
        }
    }

    public List<Item> allItem(String orderCriteria) {
        List<Item> itemList = itemRepository.findAll(Sort.by(Sort.Direction.DESC, orderCriteria));

        return itemList;
    }

    public List<Item> searchList(String keyword, String orderCriteria) {
        List<Item> itemList = new ArrayList<>();

        if(orderCriteria == "views") {
            itemList = itemRepository.findItemsByDesignNameOrderByViewsDesc(keyword);
        } else if(orderCriteria == "likes") {
            itemList = itemRepository.findItemsByDesignNameOrderByLikesDesc(keyword);
        } else {
            itemList = itemRepository.findItemsByDesignNameOrderByIdDesc(keyword);
        }

        return itemList;
    }

    public List<Item> findLikedList(String memberId, List<Item> itemList) {
        Iterator<Item> itemIterator = itemList.iterator();

        while(itemIterator.hasNext()) {
            Item item = itemIterator.next();
            if(item.getLikedList().contains(memberId)) {
                item.setLiked(true);
            } else {
                item.setLiked(false);
            }

            log.info("현재 아이템 : " + item.getDesignName() + " 좋아요 표시 : " + item.isLiked());
        }

        return itemList;
    }
}
