package hello.itemservice.web;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Model 부분
/**
 * 저장
 */
@Repository
public class ItemRepository {
    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    // 상품 저장
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(),item);
        return item;
    }

    // 아이디 찾기
    public Item findById(Long id) {
        return store.get(id);
    }

    // Item 전체 리스트 불러오기
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    // 상품정보 업데이트
    public void update(Long itemId,Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    // 초기화
    public void clearStore() {
        store.clear();
    }

}
