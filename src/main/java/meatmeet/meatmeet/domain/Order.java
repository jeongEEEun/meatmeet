package meatmeet.meatmeet.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class Order {
	private int orderId;
	private String memberId;
	private int itemId;
	private String userName;
	private String phone;
	private String address;
	private String request;
	private int price;
	private String orderDate;
	private String quantity;
    private String orderName;
    private String orderPhone;
    private String orderAddress;
    private String orderRequest;
    private String payment;
}
