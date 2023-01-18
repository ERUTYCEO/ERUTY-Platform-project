package ERUTY.platform.controller;

import ERUTY.platform.domain.Item;
import ERUTY.platform.form.ItemForm;
import ERUTY.platform.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("itemForm", new ItemForm());
        return "items/itemRegist";
    }

    @PostMapping("/items/new")
    public String registration(@Valid ItemForm itemForm, BindingResult result) {
        log.info(itemForm.getCreator(), " + ", itemForm.getDesignName());
        if(result.hasErrors()) {
            return "items/itemRegist";
        }
        String s = String.valueOf(itemForm.getCreatedDate());
        java.sql.Date sqlDate = java.sql.Date.valueOf(s);

        Item item = new Item(
                itemForm.getDesignName(), itemForm.getCreator(), sqlDate,
                itemForm.getDescription(), itemForm.getPrice(), itemForm.isOrigin(),
                itemForm.isCanModification(), itemForm.isCanCommercialUse());

        itemService.saveItem(item);
        return "redirect:/";
    }
}
