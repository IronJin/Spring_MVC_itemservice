package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;
import org.thymeleaf.processor.element.IElementModelProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository //컴포넌트 스캔의 대상이 됨
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); //동시에 여러 쓰레드가 접근하게 되면 해쉬맵을 쓰지말것
    //추천하는 것은 ConcurrentHashMap<>()을 사용할 것
    private static long sequence = 0L; //long 말고 automiclong 등을 사용해야함

    //아이템을 저장하는 기능
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    //조회기능
    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemid, Item updateParam){
        Item findItem = findById(itemid);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    //해쉬맵에 있는 자료를 다 날리기위해 만들었고 테스트용도임
    public void clearStore(){
        store.clear();
    }

}
