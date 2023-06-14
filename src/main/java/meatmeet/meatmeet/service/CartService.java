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
    	List<Cart> cartItems = cartRepository.findCartByMemberId(memberId);
    	
    	for(Cart cart: cartItems) {
    		Optional<Item> i = cartRepository.findByItemId(cart.getItemId());
    		
    		if(i.isPresent()) {
    			Item item = i.get();
    			
    			cart.setItemName(item.getItemName());
    			cart.setPrice(item.getTodayPrice());
    		}
    	}
    	return cartItems;
    }
    
    public int totalPrice(String memberId) {
    	List<Cart> cartItems = findCartByMemberId(memberId);
    	int totalPrice = 0;
    	
    	for(Cart cart: cartItems) {
    		totalPrice += cart.getPrice();
    	}
    	return totalPrice;
    }
    
    public int totalQuantity(String memberId) {
    	List<Cart> cartItems = findCartByMemberId(memberId);
    	int totalQuantity = 0;
    	
    	for(Cart cart: cartItems) {
    		totalQuantity += cart.getQuantity();
    	}
    	return totalQuantity;
    }
}
