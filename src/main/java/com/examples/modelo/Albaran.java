package com.examples.modelo;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the albaran database table.
 * 
 */
@Entity
@Table(name="albaran")
@NamedQuery(name="Albaran.findAll", query="SELECT a FROM Albaran a")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Albaran implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_generacion")
	private Date fechaGeneracion;

	@Column(name="importe_total")
	private BigDecimal importeTotal;

	private boolean validado;

	//bi-directional many-to-one association to Pedido
	@ManyToOne
	private Pedido pedido;

	//bi-directional many-to-one association to AlbaranFactura
	@OneToMany(mappedBy="albaran")
	private List<AlbaranFactura> albaranFacturas;

	public AlbaranFactura addAlbaranFactura(AlbaranFactura albaranFactura) {
		getAlbaranFacturas().add(albaranFactura);
		albaranFactura.setAlbaran(this);

		return albaranFactura;
	}

	public AlbaranFactura removeAlbaranFactura(AlbaranFactura albaranFactura) {
		getAlbaranFacturas().remove(albaranFactura);
		albaranFactura.setAlbaran(null);

		return albaranFactura;
	}

}