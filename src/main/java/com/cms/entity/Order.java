package com.cms.entity;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name="order_tbl")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "food_id", nullable = false)
	private Food food;
	
	@Column(nullable = false)
	private int quantity;
	
	private double amount;
		
	 @CreationTimestamp
	  private Instant createdAt;
	 
	 @ManyToOne
	 @JoinColumn(name = "user_id", nullable = false)
	 private User user;
	 
	 @Enumerated(value = EnumType.STRING)
	 private StatusType orderStatus;
	 
	 @Column(nullable = false)
	 private String ticketID;

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User customer) {
		this.user = customer;
	}

	public StatusType getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(StatusType orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getTicketID() {
		return ticketID;
	}

	public void setTicketID(String ticketID) {
		this.ticketID = ticketID;
	}

	public int getId() {
		return id;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}
	 
	@Override
	public String toString() {
	    return "Order{" +
	            "id=" + id +
	            ", food=" + (food != null ? food.getId() : "null") + // Assuming Food has an id field
	            ", quantity=" + quantity +
	            ", amount=" + amount +
	            ", createdAt=" + createdAt +
	            ", user=" + (user != null ? user.getId() : "null") + // Assuming User has an id field
	            ", orderStatus=" + orderStatus +
	            ", ticketID='" + ticketID + '\'' +
	            '}';
	}


}
