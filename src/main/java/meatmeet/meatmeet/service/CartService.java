package meatmeet.meatmeet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Cart;
import meatmeet.meatmeet.domain.Item;
import meatmeet.meatmeet.repository.CartRepository;

@Service
@Slf4j
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> findCartByMemberId(String memberId) {
    	return cartRepository.findCartByMemberId(memberId);
    }
    
    public int totalPrice(String memberId) {
    	List<Cart> cartItems = findCartByMemberId(memberId);
    	int totalPrice = 0;
    	
    	for(Cart cart: cartItems) {
    		totalPrice += cart.getPrice() * cart.getQuantity();
    	}
    	return totalPrice;
    }
    
    public void updateQuantity(String memberId, int itemId, int quantity) {
    	cartRepository.updateQuantity(memberId, quantity, itemId);
    }
}
