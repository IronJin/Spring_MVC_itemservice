package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.net.PortUnreachableException;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

    /**
     * 상품의 상세 내역을 클릭시 뜨는 폼을 제공해주는 메소드
     */
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    /**
     * 상품을 등록하는 폼을 보여주기
     */
    @GetMapping("/add")
    public String addForm(Model model) {
        return "basic/addForm";
    }

    /**
     * 상품 등록 버튼을 눌렀을때 동작
     */
    //@PostMapping("/add")
    public String save(
            @RequestParam String itemName,
            @RequestParam int price,
            @RequestParam Integer quantity,
            Model model) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute("item",item);
        return "redirect:/basic/items/"+item.getId();
    }

    /**
     * 상품 등록 버튼을 눌렀을때 동작
     * @ModelAttribute 를 사용
     * 리다이렉트를 쓰는 이유 : 새로고침했을때 Post 를 계속해서 요청하게 되므로 같은값이 계속해서 저장하게 되는 문제가 발생
     */
    @PostMapping("/add")
    public String addItem(@ModelAttribute("item") Item item, Model model, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId()); //리다이렉트
        redirectAttributes.addAttribute("status", true); //쿼리 파라미터로 넘어감
        model.addAttribute("item",item); //이 부분은 @ModelAttribute("item") 이 item 이름으로 모델을 전달해줌 따라서 생략이 가능하다.
        return "redirect:/basic/items/{itemId}";
    }


    /**
     * 상품 수정 폼으로 이동해주는 기능
     */
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editform";
    }

    /**
     * 상품 수정 폼에서 수정버튼을 클릭 후 상품 상세목록으로 이동
     */
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

}
