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
	@JoinColumn(name = "item_id", nullable = false)
	private Food food;
	
	@Column(nullable = false)
	private int quantity;
	
	private double amount;
	
	private LocalTime deliveryTime;
	
	 @CreationTimestamp
	  private Instant createdAt;
	 
	 @ManyToOne
	 @JoinColumn(name = "customer_id", nullable = false)
	 private User customer;
	 
	 @Enumerated(value = EnumType.STRING)
	 private StatusType orderStatus;
	 
	 @Column(nullable = false)
	 private String ticketID;

	public Food getItem() {
		return food;
	}

	public void setItem(Food food) {
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

	public LocalTime getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(LocalTime deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
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
	 
	 

}
