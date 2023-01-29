package ERUTY.platform.controller;

import ERUTY.platform.domain.Item;
import ERUTY.platform.form.ItemForm;
import ERUTY.platform.form.findItemForm;
import ERUTY.platform.service.ItemService;
import ERUTY.platform.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final MemberService memberService;

    @GetMapping("/items/upload1")
    public String upload1() {
        log.info("upload1 controller");

        return "items/upload1";
    }
    
    @GetMapping("/items/upload2")
    public String createForm(Model model) {
        log.info("upload2 controller");

        model.addAttribute("itemForm", new ItemForm());
        return "items/upload2";
    }

    @PostMapping("/items/upload2")
    public String registration(@Valid ItemForm itemForm, BindingResult result, HttpSession session) {
        log.info(itemForm.getCreator(), " + ", itemForm.getDesignName());
        if(result.hasErrors()) {
            return "items/upload2";
        }

        java.sql.Date sqlDate = java.sql.Date.valueOf(itemForm.getCreatedDate());

        Item item = new Item(
                itemForm.getDesignName(), itemForm.getCreator(), sqlDate,
                itemForm.getDescription(), itemForm.getPrice(), itemForm.isOrigin(),
                itemForm.isCanModification(), itemForm.isCanCommercialUse());

        session.getAttribute("loginId");

        log.info("session : " + session);

        String memberId = String.valueOf(session);

        itemService.saveItem(item);
        memberService.uploadListUpdate(item.getId(), memberId);

        return "redirect:/";
    }
    @GetMapping("/items/search")
    public String DesignList(Model model, @PageableDefault(page=0, size=10, direction = Sort.Direction.DESC)Pageable pageable, findItemForm finditemForm){
        Page<Item> list = null;
        String searchKeyword = finditemForm.getSearchKeyword();
        if (searchKeyword == null){
            list = itemService.TotalItem(pageable); // 검색 X -> 아이템 전체 리스트들 띄우기
        }
        else{
            list = itemService.searchItemList(finditemForm, pageable); //검색결과에 해당하는 아이템만
        }
        int nowPage = list.getPageable().getPageNumber()+1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());
        model.addAttribute("list",list);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        return "items/itemSearch";
    }

    @GetMapping("/items/view")
    public String itemview(Model model, String designName){
        model.addAttribute("info",itemService.iteminfo(designName));
        String name = itemService.iteminfo(designName).getDesignName();
        String description = itemService.iteminfo(designName).getDescription();
        String creator = itemService.iteminfo(designName).getCreator();
        long price = itemService.iteminfo(designName).getPrice();
        model.addAttribute("name", name);
        model.addAttribute("description",description);
        model.addAttribute("creator", creator);
        model.addAttribute("price",price);
        return "items/itemInfo";
    }

}
