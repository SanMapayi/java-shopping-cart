import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter@Setter
public class InventoryItems<T extends Product> implements Comparable<InventoryItems<T>>{

    private T product;
    private int qtyTotal;
    private int qtyReserved;
    private int qtyReorder;
    private int qtyLow;
    private double salesPrice;
    private List<Product> productsList = new ArrayList<>(capacity);
    private static int capacity = 6;

    public InventoryItems(T product, int qtyTotal, int qtyReserved, int qtyReorder, int qtyLow, double salesPrice) {
        this.product = product;
        this.qtyTotal = Math.min(qtyTotal, capacity);
        this.qtyReserved = qtyReserved;
        this.qtyReorder = qtyReorder;
        this.qtyLow = qtyLow;
        this.salesPrice = salesPrice;
        int a = 0;
        char b = 0;
        var skus = product.sku().substring(0, product.sku().length()-1);
        var sku = product.sku();
        for (int i = 0; i < qtyTotal; i++) {
           int index = sku.length()-1;
           if (Character.isDigit(sku.charAt(index))) {
              a = Integer.parseInt(String.valueOf(sku.charAt(index)));
               System.out.println(a);
               a = a + 1;
               sku = skus.concat(String.valueOf(a));
               if (productsList.size() < capacity) {
                   productsList.add(new Product(sku, product.name(), product.manufacturer(), product.category()));
               }
           }
           else if (Character.isLetter(sku.charAt(index))) {
               b = (char) (sku.charAt(index) +  1);
              sku= skus.concat(String.valueOf(b));
               if (productsList.size() < capacity) {
                   productsList.add(new Product(sku, product.name(), product.manufacturer(), product.category()));
               }
           }
        }

    }

    public InventoryItems(T product, int qtyTotal, int qtyReorder, int qtyLow, double salesPrice) {
        this(product, qtyTotal, 0, qtyReorder, qtyLow, salesPrice);
    }

    public InventoryItems(T product) {
        this.product = product;
    }

    public boolean reserveItem() {
        if (getQtyTotal() == 0) return false;
        else if (getQtyReserved() > getQtyTotal()) return false;
        else {
            int currentReserved = getQtyReserved();
            if (currentReserved + 1 <= getQtyTotal()) {
                qtyReserved = currentReserved + 1;
                return true;
            } else {
                return false;
            }
        }
    }



    public boolean releaseItem() {
        if (getQtyReserved() <= 0) return false;
        else {
            int released = getQtyReserved();
            this.qtyReserved = released - 1;
            return true;
        }
    }

    public void sellItem() {
        if (qtyReserved > 0 && qtyReserved <= qtyTotal) {

            qtyTotal -= 1;
            int b = getQtyReserved();
            qtyReserved = b - 1;
        }
        placeInventoryOrder();
    }

    public void placeInventoryOrder() {

        if (getQtyLow() > getQtyTotal()) {
            int temp = getQtyTotal();
            int temp2 = getQtyReorder();
            qtyTotal += temp2;
//            System.out.printf("The total stock %d of %s < the threshold %d, and restocked with %d" +
//                    "%nThe new Stock level is %d", temp, product.name(), getQtyLow(), temp2 , qtyTotal);
        }
    }

    public InventoryItems<T> addProduct(T t) {
        if (!(this.product.name().equalsIgnoreCase(t.name()))) return this;
        if (productsList.contains(t)) {
            System.out.println("item is already in inventory, cannot add duplicate product");
            return this;
        }
        if (productsList.size() < capacity) {
            productsList.add(t);
            qtyTotal += 1;
            return this;
        }
        else {
            System.out.println("The Inventory capacity is full");
        }
        return this;
    }

    public InventoryItems<T> addProduct(List<T> list) {
        for (var p : list) {
            addProduct(p);
        }
        return this;
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "product=" + product +
                ", qtyTotal=" + qtyTotal +
                ", qtyReserved=" + qtyReserved +
                ", qtyLow=" + qtyLow +
                ", salesPrice=" + salesPrice +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventoryItems<?> that)) return false;

        return getProduct().equals(that.getProduct());
    }

    @Override
    public int hashCode() {
        return getProduct().hashCode();
    }


    @Override
    public int compareTo(InventoryItems<T> o) {
        return this.product.name().compareTo(o.product.name());
    }
}
