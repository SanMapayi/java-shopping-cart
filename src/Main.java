import java.time.LocalDate;
import java.util.Arrays;
import java.util.NavigableSet;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {


        Product rice = new Product("ABC1234D", "Rice", "The Sun", Category.GRAINS);
        Product tomatoes = new Product("ABC12344", "Tomatoes", "Lakers", Category.CANNED_GOODS);

        InventoryItems<Product> riceInventory= new InventoryItems<>(rice,3, 5, 1, 3.35 );
        InventoryItems<Product> tomatoesInventory = new InventoryItems<>(tomatoes, 10, 0, 2, 2.13);
        NavigableSet<InventoryItems<Product>> inventoryItems = new TreeSet<>();
        inventoryItems.add(riceInventory);
        inventoryItems.add(tomatoesInventory);


        riceInventory.addProduct(new Product("ADVC1234H", "Rice", "The Sun", Category.GRAINS))
                .addProduct(new Product("ADVC1234I", "Rice", "The Sun", Category.GRAINS))
                .addProduct(new Product("ADVC1234J", "Rice", "The Sun", Category.GRAINS))
                .addProduct(new Product("ADVC1234K", "Rice", "The Sun", Category.GRAINS));
        riceInventory.getProductsList().forEach(System.out::println);
    System.out.println(riceInventory.getQtyTotal());
    System.out.println(riceInventory.getQtyReserved());



        Cart cart = new Cart(1, LocalDate.now(), Type.PHYSICAL);
        System.out.println("_".repeat(100));
//
        Store store = new Store(inventoryItems, Arrays.asList(cart));
        store.listProductsByCategory();




    }
}
