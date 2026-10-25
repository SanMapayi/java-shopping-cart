import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;
@Getter@Setter
public class Store {
    NavigableSet<InventoryItems<Product>> itemNavigableSet;
    List<Cart> cartList;
    Map<Category, InventoryItems<Product>> categoryInventoryItemMap = new HashMap<>();

    public Store(NavigableSet<InventoryItems<Product>> itemNavigableSet, List<Cart> cartList) {
        this.itemNavigableSet = itemNavigableSet;
        this.cartList = cartList;
        this.itemNavigableSet.forEach(
                i -> {
                    this.categoryInventoryItemMap.put(i.getProduct().category(), i);
                }
        );
    }

    public void listProductsByCategory() {
        NavigableSet<Product> productSet = new TreeSet<>(Comparator.comparing(Product::category));
        itemNavigableSet.forEach(i -> {
            productSet.add(i.getProduct());
        });

        productSet.forEach(System.out::println);
    }

    public Cart checkOutCart(int cartId) {
        Cart temp = null;
        for (Cart cart : cartList) {
            if (cart.getId()== cartId) {
                temp = cart;
                cart.printSalesSlipAndBuy(getItemNavigableSet());
            }
        }
        return temp;
    }

    public Cart checkOutCart(Cart cart) {
        if ((findCart(cart)) == null) return null;
        cart.printSalesSlipAndBuy(getItemNavigableSet());
        getCartList().remove(cart);
        return cart;
    }

    public Cart findCart(Cart cart) {
        if (!cartList.contains(cart)){
            System.out.println("Cart not in the store cart");
            return null;
        }
        return cart;
    }

    public List<Cart> abandonCarts() {
        cartList.forEach(c ->
        {
            var productList = c.getProductsInCart();
            for (Product product : productList) {
                var inventoryToRelease = new InventoryItems<>(product);
                var getInventoryToRelease = getItemNavigableSet().ceiling(inventoryToRelease);
                if(getInventoryToRelease != null && getInventoryToRelease.getProduct().name().equalsIgnoreCase(product.name())) {
                    if (!Objects.equals(c.getDate(), LocalDate.now())) {
                        getInventoryToRelease.releaseItem();
                        cartList.remove(c);
                    }

                }
            }
        });
        return getCartList();
    }
}
