package meatmeet.meatmeet.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class Order {
<<<<<<< HEAD
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
=======
    private String orderName;
    private String orderPhone;
    private String orderAddress;
    private String orderRequest;
    private String payment;
>>>>>>> 7df70b3d936f78d073ce542632fd06502a1d96a6
}
