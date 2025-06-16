package com.examples.modelo;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the stock database table.
 * 
 */
@Entity
@Table(name="stock")
@NamedQuery(name="Stock.findAll", query="SELECT s FROM Stock s")
public class Stock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private BigDecimal cantidad;

	//bi-directional many-to-one association to Bar
	@ManyToOne
	private Bar bar;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	private Producto producto;

	public Stock() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public Bar getBar() {
		return this.bar;
	}

	public void setBar(Bar bar) {
		this.bar = bar;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}