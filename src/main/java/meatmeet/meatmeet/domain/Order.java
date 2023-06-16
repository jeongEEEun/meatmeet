package meatmeet.meatmeet.domain;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Order {
	private String orderId;
	private String memberId;
	private int itemId;
	private String itemName;
	private int price;
	private int quantity;
	private int totalPrice;
	private String userName;
	private String phone;
	private String address;
	private String request;
	private LocalDate orderDate;
    private String payment;
    
    public Order(Order order) {
    	this.memberId = order.memberId;
    	this.userName = order.userName;
    	this.phone = order.phone;
    	this.address = order.address;
    	this.request = order.request;
    	this.payment = order.payment;
    }
}

