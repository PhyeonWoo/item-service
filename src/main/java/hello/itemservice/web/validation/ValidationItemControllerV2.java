package hello.itemservice.web.validation;


import hello.itemservice.web.Item;
import hello.itemservice.web.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId,Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "validation/v2/item";
    }

//    @GetMapping("/add")
//    public String addForm(Model model) {
//        model.addAttribute("item",new Item());
//        return "validation/v1/addForm";
//    }
//
//    @PostMapping("/add")
//    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId",savedItem.getId());
//        redirectAttributes.addAttribute("status",true);
//        return "redirect:/validation/v1/items/{itemId}";
//    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    // 입력해서 보내는 부분
    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        /**
         * bindingResult가 error를 대체해준다
         */
        //검증 로직 (필드 에러)
        if(!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item","itemName","물건 이름은 필수입니다"));
            // itemName이 없을경우 errors에 내용을 저장
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 100000) {
            bindingResult.addError(new FieldError("item","price","1,000원 이상 100,000이하 여야 합니다"));

            // price가 1000원 이하, 10000원 이상일 경우 errors에 내용을 저장
        }
        if(item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item","quantity","9,999개 이하로 주문가능합니다"));
            // quantity가 9999이상일 경우 errors에 내용을 저장
        }

        // 특정 필드가 아닌 복합 룰 검증 (객체 에러)
        if(item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice <= 10000) {
                bindingResult.addError(new ObjectError("item","가격 * 수량의 합이 10,000 원 이상이어야 합니다 = "+resultPrice));
            }
        }

        // 검증 실패 시 입력폼으로 돌아오기
        if(bindingResult.hasErrors()) {
            log.info("errors = {}",bindingResult);
            // model.addAttribute("bindingResult",bindingResult);
            // bindingResult는 모델에 안담아도 스스로 넘어간다
            return "validation/v2/addForm";
        }

        // 검증 성공 시 리다이렉트
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId,Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,@ModelAttribute Item item) {
        itemRepository.update(itemId,item);
        return "redirect:/validation/v2/items/{itemId}";
    }
}

