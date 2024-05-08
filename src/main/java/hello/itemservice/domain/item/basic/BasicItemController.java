package hello.itemservice.domain.item.basic;


import hello.itemservice.domain.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller 부분
@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    // 상품 상세정보
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId,Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item); // Model에 넣는 부분
        return "basic/item";
    }

    //상품 등록폼
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    // 상품 추가
//    @PostMapping("/add")ㅇㅇ
//    public String addItemV1(@RequestParam String itemName,
//                            @RequestParam int price,
//                            @RequestParam Integer quantity,
//                            Model model) {
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setQuantity(quantity);
//        item.setPrice(price);
//
//        itemRepository.save(item);
//        model.addAttribute("item",item);
//        return "basic/item";
//    }

    // ModelAttribute가 객체를 생성해준다
//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute Item item) {
//        itemRepository.save(item);
//        return "basic/item";
//    }
//

    /**
     * 상품 등록 폼
     * @ModelAttribute 생략가능
     * model.addAttribute(item) 자동생성
     */
    @PostMapping("/add")
    public String addItemV3(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    // 상품 수정 폼
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId,Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }

    // 상품 수정
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,@ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }



    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA",10000,15));
        itemRepository.save(new Item("testB",15000,10));
    }
}
