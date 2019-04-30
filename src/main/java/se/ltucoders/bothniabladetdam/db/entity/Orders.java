package se.ltucoders.bothniabladetdam.db.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column(name = "orderDate")
    private LocalDate orderDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "userId")
    private Users user;

    @Column(name = "status")
    private String status;

    @Column(name = "discount")
    private int discount;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "orders",
            cascade =   {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<OrderDetails> orderDetails;

    public Orders() {
    }

    public Orders(LocalDate orderDate, Users user, String status, int discount) {
        this.orderDate = orderDate;
        this.user = user;
        this.status = status;
        this.discount = discount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
