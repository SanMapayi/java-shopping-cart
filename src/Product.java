
enum Category {
    BAKERY_ITEMS, BEVERAGES, CANNED_GOODS, DAIRY_AND_EGGS, FRESH_PRODUCE, GRAINS, MEAT_AND_SEAFOOD;

    public String toString() {
        return name().replaceAll("_", " ");
    }
}
public record Product(String sku, String name, String manufacturer, Category category) {

    @Override
    public String toString() {
        return String.format("Product { SKU: %s, Name: %s, Manufacturer: %s, Category: %s }", sku(), name(), manufacturer(), category());
    }
}

