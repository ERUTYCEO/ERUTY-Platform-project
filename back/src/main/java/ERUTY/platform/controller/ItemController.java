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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("itemForm", new ItemForm());
        return "items/regist";
    }

    @PostMapping("/items/new")
    public String registration(@Valid ItemForm itemForm, BindingResult result) throws ParseException {
        log.info(itemForm.getCreator(), " + ", itemForm.getDesignName());
        if(result.hasErrors()) {
            return "items/regist";
        }

        String s = String.valueOf(itemForm.getCreatedDate());
        java.sql.Date sqlDate = java.sql.Date.valueOf(s);
        Item item = new Item(itemForm.getDesignName(), itemForm.getCreator(), sqlDate,
                itemForm.getDescription(), itemForm.getTool(), itemForm.getPrice());

        itemService.saveItem(item);



        return "redirect:/";
    }
}