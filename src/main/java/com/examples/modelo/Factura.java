package com.examples.modelo;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the factura database table.
 * 
 */
@Entity
@Table(name="factura")
@NamedQuery(name="Factura.findAll", query="SELECT f FROM Factura f")
public class Factura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_emision")
	private Date fechaEmision;

	@Column(name="total_factura")
	private BigDecimal totalFactura;

	//bi-directional many-to-one association to AlbaranFactura
	@OneToMany(mappedBy="factura")
	private List<AlbaranFactura> albaranFacturas;

	//bi-directional many-to-one association to Proveedor
	@ManyToOne
	private Proveedor proveedor;

	public Factura() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaEmision() {
		return this.fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public BigDecimal getTotalFactura() {
		return this.totalFactura;
	}

	public void setTotalFactura(BigDecimal totalFactura) {
		this.totalFactura = totalFactura;
	}

	public List<AlbaranFactura> getAlbaranFacturas() {
		return this.albaranFacturas;
	}

	public void setAlbaranFacturas(List<AlbaranFactura> albaranFacturas) {
		this.albaranFacturas = albaranFacturas;
	}

	public AlbaranFactura addAlbaranFactura(AlbaranFactura albaranFactura) {
		getAlbaranFacturas().add(albaranFactura);
		albaranFactura.setFactura(this);

		return albaranFactura;
	}

	public AlbaranFactura removeAlbaranFactura(AlbaranFactura albaranFactura) {
		getAlbaranFacturas().remove(albaranFactura);
		albaranFactura.setFactura(null);

		return albaranFactura;
	}

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

}