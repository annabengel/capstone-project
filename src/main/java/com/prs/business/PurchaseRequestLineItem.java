package com.prs.business;

import javax.persistence.*;

@Entity
public class PurchaseRequestLineItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn (name = "purchaseRequestId")
	private PurchaseRequest purchaseRequest;
	@ManyToOne
	@JoinColumn (name = "productId")
	private Product product;
	private int quantity;
	
	public PurchaseRequestLineItem() {
	}

	public PurchaseRequestLineItem(int id, PurchaseRequest purchaseRequest, Product product, int quantity) {
		super();
		this.id = id;
		this.purchaseRequest = purchaseRequest;
		this.product = product;
		this.quantity = quantity;
	}


	public PurchaseRequestLineItem(PurchaseRequest purchaseRequest, Product product, int quantity) {
		super();
		this.purchaseRequest = purchaseRequest;
		this.product = product;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PurchaseRequest getPurchaseRequest() {
		return purchaseRequest;
	}

	public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
		this.purchaseRequest = purchaseRequest;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "PurchaseRequestLine [id=" + id + ", purchaseRequestId=" + purchaseRequest + ", productId=" + product
				+ ", quantity=" + quantity + "]";
	}

}
