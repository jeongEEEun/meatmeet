package meatmeet.meatmeet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private Long id;
    private String orderName;
    private String orderPhone;
    private String orderAddress;
    private String orderRequest;
    private String payment;
    private boolean cancelled;
}