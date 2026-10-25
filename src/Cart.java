import lombok.Getter;

import java.time.LocalDate;
import java.util.*;

enum Type {PHYSICAL, VIRTUAL}

@Getter
public class Cart {

    private final int id;
    private final List<Product> productsInCart = new ArrayList<>();
    private final LocalDate date;
    private final Type type;

    public Cart(int id, LocalDate date, Type type) {
        this.id = id;
        this.date = date;
        this.type = type;
    }

    public void addItem(Product product, NavigableSet<InventoryItems<Product>> inventoryItemSet, int quantity) {
        var inventoryItem = inventoryItemSet.floor(new InventoryItems<>(product));
        if (inventoryItem != null && inventoryItem.getProduct().name().equalsIgnoreCase(product.name())) {
            for (int i = 1; i <= quantity; i++) {
                if(inventoryItem.reserveItem()) {
                    productsInCart.add(product);
                    if (quantity==1) {System.out.println("Reserved");}
                    else System.out.printf("Product %s has been reserved%n", product.name());
                }
                else {
                    i = i-1;
                    System.out.printf("Only %s number of items %s of %s is added as opposed to intended %s.%n",i, i == 1 ? "is" : "were" , product.name(), quantity);
                    break;
                }
            }
        }
        else {
            System.out.printf("The product %s you have selected in not in stock%n", product.name());
        }
        System.out.println();

    }

    public void removeItem(Product product, NavigableSet<InventoryItems<Product>> inventoryItemSet,  int quantity) {
        var inventoryItem = inventoryItemSet.floor(new InventoryItems<>(product));
        if (inventoryItem != null && inventoryItem.getProduct().name().equalsIgnoreCase(product.name())) {
            for (int i = 1; i <= quantity; i++) {
                if(inventoryItem.releaseItem() && productsInCart.contains(product)) {
                    productsInCart.remove(product);
                    System.out.printf("The %s has been removed%n", product.name());
                }
                else {
                    i = i -1;
                    System.out.printf("Only %s number of items %s removed for this %s as product in the cart is not up to %d%n",i, i == 1 ? "is" : "were" , product.name(), quantity);
                    break;
                }
            }
        }

    }

    public void printCartOut() {
        productsInCart.forEach(System.out::println);
    }
    public void showCart(NavigableSet<InventoryItems<Product>> inventoryItemList) {
        double total = 0.0;
        if (getProductsInCart().isEmpty()) {
            System.out.println("Cart is empty; there is no item to show");
            return;
        }
        Set<Product> productsInCartSet = new LinkedHashSet<>(getProductsInCart());
        for (Product product : productsInCartSet) {
            InventoryItems<Product> temp = inventoryItemList.floor(new InventoryItems<>(product));
            if (temp!= null && temp.getProduct().equals(product)) {
                total += showInventoryPrice(temp);
            }
        }
        System.out.printf("The total price of items : %.2f%n", total);

    }

    private double showInventoryPrice (InventoryItems<Product> inventoryItem) {
        int reserved = inventoryItem.getQtyReserved();
        if (reserved == 0) {
            return 0;
        }
        System.out.printf("%-10s : %s %c %.2f%n", inventoryItem.getProduct().name(), reserved, (char) 215,  inventoryItem.getSalesPrice());
        return reserved * inventoryItem.getSalesPrice();
    }



    public void printSalesSlipAndBuy(NavigableSet<InventoryItems<Product>> inventoryItemList) {
        showCart(inventoryItemList);
        Iterator<Product> iterator = productsInCart.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            InventoryItems<Product> temp = inventoryItemList.floor(new InventoryItems<>(product));
            if (temp != null && temp.getProduct().name().equalsIgnoreCase(product.name())) {
                for (int i = 0; i < temp.getQtyReserved(); i++) {
                    temp.sellItem();
                }
                iterator.remove();
            }
        }
        System.out.printf("item sold : %-15s%n", getDate());

    }

    @Override
    public String toString() {
        if (productsInCart.isEmpty()) {
            System.out.println("Cart is empty");
            return null;
        }
        else productsInCart.forEach(System.out::println);
        return String.format("%-3d %-12s : %-10s ", id, date, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart cart)) return false;

        if (id != cart.id) return false;
        if (!date.equals(cart.date)) return false;
        return type == cart.type;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + date.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
