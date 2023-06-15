package meatmeet.meatmeet.domain;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Order {
	private String orderId;
	private String memberId;
	private int itemId;
	private String itemName;
	private int price;
	private int quantity;
	private String userName;
	private String phone;
	private String address;
	private String request;
	private LocalDate orderDate;
    private String payment;
}
