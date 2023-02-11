package ERUTY.platform.controller;

import ERUTY.platform.domain.Item;
import ERUTY.platform.form.ItemForm;
import ERUTY.platform.form.findItemForm;
import ERUTY.platform.service.ItemService;
import ERUTY.platform.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.linkbuilder.ILinkBuilder;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    @GetMapping("/items/search")
    public String DesignList(Model model, @PageableDefault(page = 0, size = 9, direction = Sort.Direction.DESC) Pageable pageable, HttpSession session, findItemForm finditemForm) {
        Page<Item> itemList = null;
        String searchKeyword = finditemForm.getSearchKeyword();

        String memberId = (String) session.getAttribute("loginId");

        if (searchKeyword == null) {
            itemList = itemService.TotalItem(pageable, memberId); // 검색 X -> 아이템 전체 리스트들 띄우기
        } else {
            itemList = itemService.searchItemList(finditemForm, pageable, memberId); //검색결과에 해당하는 아이템만
        }
        int nowPage = itemList.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, itemList.getTotalPages());
        model.addAttribute("itemList", itemList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "gallery";
    }

    @GetMapping("/items/view")
    public String itemview(Model model, String designName) {
        model.addAttribute("info", itemService.iteminfo(designName));

        return "items/itemInfo";
    }


    @GetMapping("/items/test")
    public String test1(@ModelAttribute("creator") String creator, Model model) {
        List<Item> items = itemService.findItemsByCreator("test0");
        for (Item item : items) {
            log.info(item.getDesignName());
            log.info(String.valueOf(item.getCreatedDate()));
            log.info(String.valueOf(item.isOrigin()));
        }
        return "home";
    }

    @GetMapping("items/{itemId}/detail")
    public String itemDetail(@PathVariable("itemId") String itemId, HttpSession session, Model model) {
        Item item = itemService.updateView(itemId);

        String memberId = (String) session.getAttribute("loginId");

        boolean liked = itemService.findLiked(memberId, itemId);
        item.setLiked(liked);

        model.addAttribute("item", item);
        model.addAttribute("newLineChar", '\n');

        return "designpage";
    }

    @GetMapping("items/{itemId}/like")
    public String liked(@PathVariable("itemId") String itemId, HttpSession session, Model model) {
        String memberId = (String) session.getAttribute("loginId");

        Item item = itemService.likedListUpdate(itemId, memberId);

        boolean liked = itemService.findLiked(memberId, itemId);
        item.setLiked(liked);

        model.addAttribute("item", item);
        model.addAttribute("newLineChar", '\n');

        return "designpage";
    }

    @GetMapping("items/{itemId}/buy")
    public String buy(@PathVariable("itemId") String itemId, HttpSession session, Model model) {
        String memberId = (String) session.getAttribute("loginId");
        Item item = itemService.updateNumBuy(itemId);

        model.addAttribute("item", item);
        model.addAttribute("newLineChar", '\n');
        return "designpage";
    }
}