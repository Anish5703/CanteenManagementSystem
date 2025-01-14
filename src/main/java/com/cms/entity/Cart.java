package com.cms.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_tbl")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cart")
    private List<CartItem> items = new ArrayList<>();

    private double totalAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
        recalculateTotal();
    }

    public void addItem(CartItem item) {
        items.add(item);
        recalculateTotal();
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        recalculateTotal();
    }

    public double getTotalAmount() {
        return recalculateTotal();
    }

    private double recalculateTotal() {
        return items.stream()
                           .mapToDouble(item -> item.getFood().getPrice() * item.getQuantity())
                           .sum();
    }
    

    @Override
    public String toString() {
        return "Cart [id=" + id + ", user=" + user.getUsername() + ", totalAmount=" + totalAmount + "]";
    }
}
