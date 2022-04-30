package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price; //수량이나 가격이 0일수도 있음
    private Integer quantity;

    //기본생성자임 -- 왜 쓰는지 모르니 사용할때 검색할걸
    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
