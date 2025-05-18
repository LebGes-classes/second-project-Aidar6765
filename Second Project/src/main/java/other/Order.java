package other;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import human.Buyer;
import places.Product;

import java.util.Objects;

public class Order {

    private int id;
    private int shopId;
    private Buyer buyer;
    private Product product;
    private int quantity;
    private int cost;

    @JsonCreator
    public Order(@JsonProperty("id") int id,
                 @JsonProperty("shop id") int shopId,
                 @JsonProperty("buyer") Buyer buyer,
                 @JsonProperty("product") Product product,
                 @JsonProperty("quantity") int quantity,
                 @JsonProperty("cost") int cost) {
        this.id = id;
        this.shopId = shopId;
        this.buyer = buyer;
        this.product = product;
        this.quantity = quantity;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public int getShopId() {
        return shopId;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCost() {
        return cost;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && shopId == order.shopId && quantity == order.quantity && cost == order.cost && Objects.equals(buyer, order.buyer) && Objects.equals(product, order.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shopId, buyer, product, quantity, cost);
    }
}
