package com.examples.modelo;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the albaran_factura database table.
 * 
 */
@Entity
@Table(name="albaran_factura")
@NamedQuery(name="AlbaranFactura.findAll", query="SELECT a FROM AlbaranFactura a")
public class AlbaranFactura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to Albaran
	@ManyToOne
	private Albaran albaran;

	//bi-directional many-to-one association to Factura
	@ManyToOne
	private Factura factura;

	public AlbaranFactura() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Albaran getAlbaran() {
		return this.albaran;
	}

	public void setAlbaran(Albaran albaran) {
		this.albaran = albaran;
	}

	public Factura getFactura() {
		return this.factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

}