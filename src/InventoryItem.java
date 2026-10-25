import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class InventoryItem implements Comparable<InventoryItem>{

    private Product product;
    private int qtyTotal;
    private int qtyReserved;
    private int qtyReorder;
    private int qtyLow;
    private double salesPrice;

    public InventoryItem(Product product, int qtyTotal, int qtyReserved, int qtyReorder, int qtyLow, double salesPrice) {
        this.product = product;
        this.qtyTotal = qtyTotal;
        this.qtyReserved = qtyReserved;
        this.qtyReorder = qtyReorder;
        this.qtyLow = qtyLow;
        this.salesPrice = salesPrice;
    }

    public InventoryItem(Product product, int qtyTotal, int qtyReorder, int qtyLow, double salesPrice) {
        this(product, qtyTotal, 0, qtyReorder, qtyLow, salesPrice);
    }

    public InventoryItem(Product product) {
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
        if (!(o instanceof InventoryItem that)) return false;

        return getProduct().equals(that.getProduct());
    }

    @Override
    public int hashCode() {
        return getProduct().hashCode();
    }


    @Override
    public int compareTo(InventoryItem o) {
        return this.product.name().compareTo(o.product.name());
    }
}
