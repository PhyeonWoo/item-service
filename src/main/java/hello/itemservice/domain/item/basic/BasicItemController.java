//package hello.itemservice.domain.item.basic;
//
//
//import hello.itemservice.web.Item;
//import hello.itemservice.web.ItemRepository;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//
//import java.util.List;
//
//// Controller 부분
//@Controller
//@RequestMapping("/basic/items")
//@RequiredArgsConstructor
//public class BasicItemController {
//    private final ItemRepository itemRepository;
//
//    @GetMapping
//    public String items(Model model) {
//        List<Item> items = itemRepository.findAll();
//        model.addAttribute("items",items);
//        return "basic/items";
//    }
//
//    // 상품 상세정보
//    @GetMapping("/{itemId}")
//    public String item(@PathVariable Long itemId,Model model) {
//        Item item = itemRepository.findById(itemId);
//        model.addAttribute("item",item); // Model에 넣는 부분
//        return "basic/item";
//    }
//
//    //상품 등록폼
//    @GetMapping("/add")
//    public String addForm() {
//        return "basic/addForm";
//    }
//
//    // 상품 추가
//
//    /**
//     * 상품 등록 폼
//     * @ModelAttribute 생략가능
//     * model.addAttribute(item) 자동생성
//     */
//    // 새로고침 할 경우 계속해서 ItemId가 올라가기 때문에
//    // redirect를 사용해야 한다
//
//    @PostMapping("/add")
//    public String add(Item item) {
//        itemRepository.save(item);
//        return "redirect:/basic/items/" +item.getId();
//    }
//
//    // 상품 수정 폼
//    @GetMapping("/{itemId}/edit")
//    public String editForm(@PathVariable Long itemId,Model model) {
//        Item item = itemRepository.findById(itemId);
//        model.addAttribute("item",item);
//        return "basic/editForm";
//    }
//
//    // 상품 수정
//    @PostMapping("/{itemId}/edit")
//    public String edit(@PathVariable Long itemId,@ModelAttribute Item item) {
//        itemRepository.update(itemId, item);
//        return "redirect:/basic/items/{itemId}";
//    }
//
//
//    @Data
//    static class User {
//        private String username;
//        private int age;
//
//        public User(String username, int age) {
//            this.username = username;
//            this.age = age;
//        }
//    }
//}
