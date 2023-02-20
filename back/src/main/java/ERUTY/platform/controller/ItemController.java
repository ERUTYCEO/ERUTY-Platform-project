package ERUTY.platform.controller;

import ERUTY.platform.common.Messsage;
import ERUTY.platform.domain.Item;
import ERUTY.platform.form.ItemForm;
import ERUTY.platform.form.findItemForm;
import ERUTY.platform.service.ItemService;
import ERUTY.platform.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final MemberService memberService;

    @GetMapping("/items/upload")
    public String createForm(Model model) {
        model.addAttribute("itemForm", new ItemForm());
        return "items/upload";
    }

    @PostMapping("/items/upload")
    public String registration(@Valid ItemForm itemForm, BindingResult bindingResult, HttpSession session) throws ParseException {
        if (bindingResult.hasErrors()) {
            return "items/upload";
        }

        String itemId = itemService.registItem(itemForm, session);

        String memberId = (String) session.getAttribute("loginId");
        memberService.uploadListUpdate(itemId, memberId);

        return "redirect:/";
    }

    @GetMapping("items/all")
    public String allPost(@RequestParam(required = false, defaultValue = "id", value = "orderBy") String orderCriteria,
                          @RequestParam(required = false, defaultValue = "0", value = "page") int page,
                          HttpSession session, Model model, findItemForm finditemForm) {

        String memberId = (String)session.getAttribute("loginId");

        Page<Item> itemList;
        int size = 12;

        itemList = itemService.allItem(page, size, orderCriteria);

        itemList = itemService.findLikedList(memberId, itemList);

        model.addAttribute("itemList", itemList);

        int nowPage = itemList.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, itemList.getTotalPages());

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "gallery";
    }

    @GetMapping("items/search")
    public String searchPost(@RequestParam("keyword") String keyword,
                             @RequestParam(required = false, defaultValue = "id", value = "orderBy") String orderCriteria,
                             @RequestParam(required = false, defaultValue = "0", value = "page") int page,
                             HttpSession session, Model model, findItemForm finditemForm) {

        String memberId = (String)session.getAttribute("loginId");

        Page<Item> itemList;
        int size = 12;

        log.info("keyword : " + keyword);

        if (keyword == "null") {
            return "redirect:/items/all";
        } else {
            itemList = itemService.searchList(page, size, keyword, orderCriteria);
            if (itemList.isEmpty()) {
                model.addAttribute("data", new Messsage("검색결과가 없습니다.", "/items/all"));
                return "message";
            }
        }

        itemList = itemService.findLikedList(memberId, itemList);

        model.addAttribute("itemList", itemList);

        int nowPage = itemList.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, itemList.getTotalPages());

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("keyword", keyword);


        return "searchGallery";
    }

    @GetMapping("items/{itemId}/detail")
    public String itemDetail(@PathVariable("itemId") String itemId, HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) {
        Item item = itemService.updateView(itemId, request, response);

        String memberId = (String) session.getAttribute("loginId");

        boolean liked = itemService.findLiked(memberId, itemId);
        item.setLiked(liked);

        model.addAttribute("item", item);
        model.addAttribute("numImage", item.getImagePathes().length);
        model.addAttribute("newLineChar", '\n');

        return "designpage";
    }

    @GetMapping("getLikeById")
    public Boolean liked(@RequestParam String itemId, HttpSession session) {
        String memberId = (String) session.getAttribute("loginId");

        Item item = itemService.likedListUpdate(itemId, memberId);

        boolean liked = itemService.findLiked(memberId, itemId);
        item.setLiked(liked);

        log.info("liked : " + liked);

        return liked;
    }

    @GetMapping("items/{itemId}/buy")
    public String buy(@PathVariable("itemId") String itemId, Model model) {
        Item item = itemService.updateNumBuy(itemId);

        model.addAttribute("item", item);
        model.addAttribute("newLineChar", '\n');
        return "redirect:/items/{itemId}/detail";
    }

    @GetMapping("item/{itemId}/delete")
    public String deleteItem(@PathVariable("itemId") String itemId, HttpSession session, Model model){
        String loginId = (String) session.getAttribute("loginId");

        Item item = itemService.findItemById(itemId);
        String itemMemberId = item.getMemberId();

        if(loginId.equals(itemMemberId)){
            log.info("동일 인물");
            itemService.deleteItem(loginId, itemId);
            return "redirect:/members/" + loginId + "/mypage";
        }
        log.info("비동일 인물");
        return "redirect:/members/" + itemMemberId + "/mypage";
    }

    
}



