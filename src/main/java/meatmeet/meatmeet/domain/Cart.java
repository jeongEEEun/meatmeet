package meatmeet.meatmeet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Cart {
    private String memberId;
    private int itemId;
    private String itemName;
    private int price;
    private int quantity;
}
