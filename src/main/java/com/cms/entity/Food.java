package com.cms.entity;
import jakarta.persistence.*;

@Entity
@Table(name="food_tbl")
public class Food {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique= true , nullable =false )
	private String name;
	
	private double price;
	
	@Column(unique=true)
	private String imageId;

	public int getId() {
		return id;
	}
    
	public void setId(int id)
	{
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	
	
	@Override 
	public String toString()
	{
		return "Name : "+this.name;
	}
	
	
}
