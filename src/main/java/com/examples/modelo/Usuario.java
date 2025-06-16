package com.examples.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String apellido1;

	private String apellido2;

	private String dni;

	private String nombre;

	private String pass;

	//bi-directional many-to-one association to Pedido
	@OneToMany(mappedBy="usuario")
	private List<Pedido> pedidos;

	//bi-directional many-to-one association to Bar
	@ManyToOne
	private Bar bar;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	private Rol rol;

	public Pedido addPedido(Pedido pedido) {
		getPedidos().add(pedido);
		pedido.setUsuario(this);

		return pedido;
	}

	public Pedido removePedido(Pedido pedido) {
		getPedidos().remove(pedido);
		pedido.setUsuario(null);

		return pedido;
	}
}