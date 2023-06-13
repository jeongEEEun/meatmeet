package meatmeet.meatmeet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meatmeet.meatmeet.domain.Cart;
import meatmeet.meatmeet.domain.Item;
import meatmeet.meatmeet.repository.CartRepository;

@Service
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getCartByMemberId(String memberId) {
    	return cartRepository.findByMemberId(memberId);
    }
    /**
     * 아이템 이름 조회
     */
    public List<Item> getItemNameByMemberId(String memberId) {
    	return cartRepository.getItemName(memberId);
    }
    /**
     * 아이템 가격 조회

     */
    public List<Item> getItemPrice(String memberId) {
        return cartRepository.getItemPrice(memberId);
    }

    /**
     * 아이템 수량 1 감소

     */
//    public void decreaseCartItemQuantity(int itemId) {
//        Cart cart = cartRepository.findByItemId(itemId);
//        if (cart != null && cart.getQuantity() > 0) {
//            cart.setQuantity(cart.getQuantity() - 1);
//            cartRepository.update(cart);
//        }
//    }


    /**
     * 아이템 수량 1 증가

     */
//    public void increaseCartItemQuantity(int cartId) {
//        Cart cart = cartRepository.findByItemId(cartId);
//        if (cart != null) {
//            cart.setQuantity(cart.getQuantity() + 1);
//            cartRepository.update(cart);
//        }
//    }
}
