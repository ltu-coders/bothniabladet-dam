package se.ltucoders.bothniabladetdam.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@IdClass(OrderDetailsPK.class)
public class OrderDetails {

    @Id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "orderId")
    private Orders orders;

    @Id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "imageId")
    private Image image;

    @Column(name = "sellPrice")
    private BigDecimal sellPrice;

    public OrderDetails() {
    }

    public OrderDetails(Orders orders, Image image, BigDecimal sellPrice) {
        this.orders = orders;
        this.image = image;
        this.sellPrice = sellPrice;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }
}
