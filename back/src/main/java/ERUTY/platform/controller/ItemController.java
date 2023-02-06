package ERUTY.platform.controller;

import ERUTY.platform.domain.Item;
import ERUTY.platform.domain.Member;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    private String modelPath;


    @GetMapping("/items/upload")
    public String createForm(Model model) {
        log.info("item controller getmapping");
        model.addAttribute("itemForm", new ItemForm());
        return "items/upload";
    }

    @PostMapping("/items/upload")
    public String registration(@Valid ItemForm itemForm, BindingResult result, HttpSession session) throws ParseException {
        log.info(itemForm.getCreator(), " + ", itemForm.getDesignName());

        if(result.hasErrors()) {
            return "items/upload";
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(itemForm.getCreatedDate());

        log.info(itemForm.getCreatedDate());
        log.info("형변환 : "+ String.valueOf(date));
        log.info("isOrigin : " + String.valueOf(itemForm.isOrigin()));
        log.info("canModification : " + String.valueOf(itemForm.isCanModification()));
        log.info("canCommercialUse : " + String.valueOf(itemForm.isCanCommercialUse()));

        Item item = Item.builder()
                .designName(itemForm.getDesignName())
                .creator(itemForm.getCreator())
                .createdDate(date)
                .description(itemForm.getDescription())
                .price(itemForm.getPrice())
                .isOrigin(itemForm.isOrigin())
                .canModification(itemForm.isCanModification())
                .canCommercialUse(itemForm.isCanCommercialUse())
                .imagePath(itemForm.getImagePath())
                .modelPath(itemForm.getModelPath())
                .build();

        String memberId = (String)session.getAttribute("loginId");

        itemService.saveItem(item);
        //memberService.uploadListUpdate(item.getId(), memberId);

        return "redirect:/";
    }

    @GetMapping("/items/search")
    public String DesignList(Model model, @PageableDefault(page=0, size=10, direction = Sort.Direction.DESC)Pageable pageable, findItemForm finditemForm){
        Page<Item> itemList = null;
        String searchKeyword = finditemForm.getSearchKeyword();
        if (searchKeyword == null){
            itemList = itemService.TotalItem(pageable); // 검색 X -> 아이템 전체 리스트들 띄우기
        }
        else{
            itemList = itemService.searchItemList(finditemForm, pageable); //검색결과에 해당하는 아이템만
        }
        int nowPage = itemList.getPageable().getPageNumber()+1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, itemList.getTotalPages());
        model.addAttribute("itemList",itemList);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        return "gallery";
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

    
    @GetMapping("/items/test")
    public String test1(@ModelAttribute("creator") String creator, Model model){
        List<Item> items = itemService.findItemsByCreator("test4");
        for(Item item : items){
            log.info(item.getDesignName());
            log.info(String.valueOf(item.getCreatedDate()));
            log.info(String.valueOf(item.isOrigin()));
        }
        return "/home";
    }
    @PostMapping("/items/test")
    public String test(@ModelAttribute("creator") String creator, Model model){
        List<Item> items = itemService.findItemsByCreator("test");
        for(Item item : items){
            log.info(item.getDesignName());
        }
        return "/home";
    }

    @GetMapping("items/{itemId}/detail")
    public String itemDetail(@PathVariable("itemId") String itemId, Model model) {
        Item item = itemService.updateView(itemId);

        model.addAttribute("item", item);

        return "/designpage";
    }
}