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
    private int quantity;
    private int todayPrice;
    private int yesterdayPrice;
}
