package meatmeet.meatmeet.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class Order {
    private String orderName;
    private String orderPhone;
    private String orderAddress;
    private String orderRequest;
    private String payment;
}
