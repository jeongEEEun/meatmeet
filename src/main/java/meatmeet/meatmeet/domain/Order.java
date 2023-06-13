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
//
//    public Order(String orderName, String payment) {
//        this.orderName = orderName;
//        this.payment = payment;
//    }
//
//    // Getter and setter methods
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getOrderName() {
//        return orderName;
//    }
//
//    public void setOrderName(String orderName) {
//        this.orderName = orderName;
//    }
//
//    public String getOrderPhone() {
//        return orderPhone;
//    }
//
//    public void setOrderPhone(String orderPhone) {
//        this.orderPhone = orderPhone;
//    }
//
//    public String getOrderAddress() {
//        return orderAddress;
//    }
//
//    public void setOrderAddress(String orderAddress) {
//        this.orderAddress = orderAddress;
//    }
//
//    public String getOrderRequest() {
//        return orderRequest;
//    }
//
//    public void setOrderRequest(String orderRequest) {
//        this.orderRequest = orderRequest;
//    }
//
//    public String getPayment() {
//        return payment;
//    }
//
//    public void setPayment(String payment) {
//        this.payment = payment;
//    }
//
//    public boolean isCancelled() {
//        return cancelled;
//    }
//
//    public void setCancelled(boolean cancelled) {
//        this.cancelled = cancelled;
//    }
}
