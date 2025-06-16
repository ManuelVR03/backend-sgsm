package com.examples.modelo;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the bar database table.
 * 
 */
@Entity
@Table(name="bar")
@NamedQuery(name="Bar.findAll", query="SELECT b FROM Bar b")
public class Bar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String cif;

	private String direccion;

	private String nombre;

	private String telefono;

	//bi-directional many-to-one association to Pedido
	@OneToMany(mappedBy="bar")
	private List<Pedido> pedidos;

	//bi-directional many-to-one association to Stock
	@OneToMany(mappedBy="bar")
	private List<Stock> stocks;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="bar")
	private List<Usuario> usuarios;

	public Bar() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCif() {
		return this.cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<Pedido> getPedidos() {
		return this.pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Pedido addPedido(Pedido pedido) {
		getPedidos().add(pedido);
		pedido.setBar(this);

		return pedido;
	}

	public Pedido removePedido(Pedido pedido) {
		getPedidos().remove(pedido);
		pedido.setBar(null);

		return pedido;
	}

	public List<Stock> getStocks() {
		return this.stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public Stock addStock(Stock stock) {
		getStocks().add(stock);
		stock.setBar(this);

		return stock;
	}

	public Stock removeStock(Stock stock) {
		getStocks().remove(stock);
		stock.setBar(null);

		return stock;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setBar(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setBar(null);

		return usuario;
	}

}