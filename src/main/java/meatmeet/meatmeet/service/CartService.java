//package meatmeet.meatmeet.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import meatmeet.meatmeet.domain.Cart;
//import meatmeet.meatmeet.repository.CartRepository;
//
//@Service
//public class CartService {
//    private final CartRepository cartRepository;
//
//    @Autowired
//    public CartService(CartRepository cartRepository) {
//        this.cartRepository = cartRepository;
//    }
//
//    /**
//     * 장바구니 아이템의 아이템 이름을 조회합니다.
//     *
//     * @param itemId 아이템 ID
//     * @return 아이템 이름
//     */
//    public String getItemName(int itemId) {
//        return cartRepository.getItemName(itemId);
//    }
//
//    /**
//     * 장바구니 아이템의 가격을 조회합니다.
//     *
//     * @param itemId 아이템 ID
//     * @return 가격
//     */
//    public int getItemPrice(int itemId) {
//        return cartRepository.getItemPrice(itemId);
//    }
//
//    /**
//     * 장바구니에서 아이템을 삭제합니다.
//     *
//     * @param memberId 회원 ID
//     * @param itemId   아이템 ID
//     */
//    public void removeFromCart(String memberId, int itemId) {
//        cartRepository.removeFromCart(memberId, itemId);
//    }
//
//    /**
//     * 장바구니에 담긴 아이템의 총 수량을 조회합니다.
//     *
//     * @param memberId 회원 ID
//     * @return 총 수량
//     */
//    public int getTotalQuantityInCart(String memberId) {
//        return cartRepository.getTotalQuantityInCart(memberId);
//    }
//
//    /**
//     * 장바구니 아이템의 수량을 1 감소시킵니다.
//     *
//     * @param cartId 장바구니 아이템 ID
//     */
//    public void decreaseCartItemQuantity(int cartId) {
//        Cart cart = cartRepository.findById(cartId);
//        if (cart != null && cart.getQuantity() > 0) {
//            cart.setQuantity(cart.getQuantity() - 1);
//            cartRepository.update(cart);
//        }
//    }
//
//    /**
//     * 장바구니 아이템의 수량을 1 증가시킵니다.
//     *
//     * @param cartId 장바구니 아이템 ID
//     */
//    public void increaseCartItemQuantity(int cartId) {
//        Cart cart = cartRepository.findById(cartId);
//        if (cart != null) {
//            cart.setQuantity(cart.getQuantity() + 1);
//            cartRepository.update(cart);
//        }
//    }
//
