package com.examples.modelo;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the estado_pedido database table.
 * 
 */
@Entity
@Table(name="estado_pedido")
@NamedQuery(name="EstadoPedido.findAll", query="SELECT e FROM EstadoPedido e")
public class EstadoPedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String nombre;

	//bi-directional many-to-one association to Pedido
	@OneToMany(mappedBy="estadoPedido")
	private List<Pedido> pedidos;

	public EstadoPedido() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Pedido> getPedidos() {
		return this.pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Pedido addPedido(Pedido pedido) {
		getPedidos().add(pedido);
		pedido.setEstadoPedido(this);

		return pedido;
	}

	public Pedido removePedido(Pedido pedido) {
		getPedidos().remove(pedido);
		pedido.setEstadoPedido(null);

		return pedido;
	}

}