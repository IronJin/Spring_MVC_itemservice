package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA",10000, 10);

        //when
        Item saveItem = itemRepository.save(item);


        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    void findAll() {

        Item item1 = new Item("userA",10000,20);
        Item item2 = new Item("userB",20000,30);

        itemRepository.save(item1);
        itemRepository.save(item2);

        List<Item> result = itemRepository.findAll();
        assertThat(result).contains(item1, item2);
        assertThat(result.size()).isEqualTo(2);

    }

    @Test
    void update() {

        Item item = new Item("userA",10000,10);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        Item updateItem = new Item("item2", 20000, 10);
        itemRepository.update(itemId, updateItem);

        Item findItem = itemRepository.findById(itemId);
        assertThat(findItem.getItemName()).isEqualTo(updateItem.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateItem.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateItem.getQuantity());
    }

}