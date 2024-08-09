package modelo.entidades;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
@DiscriminatorValue("EGRESO")
public class Egreso extends Movimiento implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Cuenta srcAccount;
	@ManyToOne
	@JoinColumn(name = "Categoria_ID", insertable = false, updatable = false)
	private CategoriaEgreso categoria;

	public Egreso() {

	}

	public Egreso(String concept, Date date, double value, Cuenta srcAccount, CategoriaEgreso categoria) {
		super(concept, date, value);
		this.srcAccount = srcAccount;
		this.categoria = categoria;
	}

	public Cuenta getSrcAccount() {
		return srcAccount;
	}

	public void setSrcAccount(Cuenta srcAccount) {
		this.srcAccount = srcAccount;
	}

	public CategoriaEgreso getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaEgreso categoria) {
		this.categoria = categoria;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
