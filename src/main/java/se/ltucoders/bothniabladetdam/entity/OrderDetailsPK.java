package se.ltucoders.bothniabladetdam.entity;

import java.io.Serializable;
import java.util.Objects;

public class OrderDetailsPK implements Serializable {
    private Integer orders;
    private Integer image;

    public OrderDetailsPK() {
    }

    public OrderDetailsPK(Integer orders, Integer image) {
        this.orders = orders;
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailsPK that = (OrderDetailsPK) o;
        return orders.equals(that.orders) &&
                image.equals(that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orders, image);
    }
}
