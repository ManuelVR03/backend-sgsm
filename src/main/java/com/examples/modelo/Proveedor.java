package com.examples.modelo;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the proveedor database table.
 * 
 */
@Entity
@Table(name="proveedor")
@NamedQuery(name="Proveedor.findAll", query="SELECT p FROM Proveedor p")
public class Proveedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String cif;

	private String direccion;

	private String email;

	private String nombre;

	private String telefono;

	//bi-directional many-to-one association to Factura
	@OneToMany(mappedBy="proveedor")
	private List<Factura> facturas;

	//bi-directional many-to-one association to Pedido
	@OneToMany(mappedBy="proveedor")
	private List<Pedido> pedidos;

	//bi-directional many-to-one association to Producto
	@OneToMany(mappedBy="proveedor")
	private List<Producto> productos;

	public Proveedor() {
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public List<Factura> getFacturas() {
		return this.facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public Factura addFactura(Factura factura) {
		getFacturas().add(factura);
		factura.setProveedor(this);

		return factura;
	}

	public Factura removeFactura(Factura factura) {
		getFacturas().remove(factura);
		factura.setProveedor(null);

		return factura;
	}

	public List<Pedido> getPedidos() {
		return this.pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Pedido addPedido(Pedido pedido) {
		getPedidos().add(pedido);
		pedido.setProveedor(this);

		return pedido;
	}

	public Pedido removePedido(Pedido pedido) {
		getPedidos().remove(pedido);
		pedido.setProveedor(null);

		return pedido;
	}

	public List<Producto> getProductos() {
		return this.productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public Producto addProducto(Producto producto) {
		getProductos().add(producto);
		producto.setProveedor(this);

		return producto;
	}

	public Producto removeProducto(Producto producto) {
		getProductos().remove(producto);
		producto.setProveedor(null);

		return producto;
	}

}