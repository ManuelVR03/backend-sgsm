package com.examples.modelo;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the pedido database table.
 * 
 */
@Entity
@Table(name="pedido")
@NamedQuery(name="Pedido.findAll", query="SELECT p FROM Pedido p")
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_realizacion")
	private Date fechaRealizacion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_recepcion")
	private Date fechaRecepcion;

	//bi-directional many-to-one association to Albaran
	@OneToMany(mappedBy="pedido")
	private List<Albaran> albarans;

	//bi-directional many-to-one association to DetallePedido
	@OneToMany(mappedBy="pedido")
	private List<DetallePedido> detallePedidos;

	//bi-directional many-to-one association to Bar
	@ManyToOne
	private Bar bar;

	//bi-directional many-to-one association to EstadoPedido
	@ManyToOne
	@JoinColumn(name="estado_id")
	private EstadoPedido estadoPedido;

	//bi-directional many-to-one association to Proveedor
	@ManyToOne
	private Proveedor proveedor;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	private Usuario usuario;

	public Pedido() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaRealizacion() {
		return this.fechaRealizacion;
	}

	public void setFechaRealizacion(Date fechaRealizacion) {
		this.fechaRealizacion = fechaRealizacion;
	}

	public Date getFechaRecepcion() {
		return this.fechaRecepcion;
	}

	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public List<Albaran> getAlbarans() {
		return this.albarans;
	}

	public void setAlbarans(List<Albaran> albarans) {
		this.albarans = albarans;
	}

	public Albaran addAlbaran(Albaran albaran) {
		getAlbarans().add(albaran);
		albaran.setPedido(this);

		return albaran;
	}

	public Albaran removeAlbaran(Albaran albaran) {
		getAlbarans().remove(albaran);
		albaran.setPedido(null);

		return albaran;
	}

	public List<DetallePedido> getDetallePedidos() {
		return this.detallePedidos;
	}

	public void setDetallePedidos(List<DetallePedido> detallePedidos) {
		this.detallePedidos = detallePedidos;
	}

	public DetallePedido addDetallePedido(DetallePedido detallePedido) {
		getDetallePedidos().add(detallePedido);
		detallePedido.setPedido(this);

		return detallePedido;
	}

	public DetallePedido removeDetallePedido(DetallePedido detallePedido) {
		getDetallePedidos().remove(detallePedido);
		detallePedido.setPedido(null);

		return detallePedido;
	}

	public Bar getBar() {
		return this.bar;
	}

	public void setBar(Bar bar) {
		this.bar = bar;
	}

	public EstadoPedido getEstadoPedido() {
		return this.estadoPedido;
	}

	public void setEstadoPedido(EstadoPedido estadoPedido) {
		this.estadoPedido = estadoPedido;
	}

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}