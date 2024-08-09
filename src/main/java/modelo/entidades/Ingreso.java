package modelo.entidades;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("INGRESO")
public class Ingreso extends Movimiento implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Cuenta dstAccount;
	@JoinColumn(name = "Categoria_ID", insertable = false, updatable = false)
	private CategoriaIngreso categoria;
	
	public Ingreso() {
		
	}
	public Ingreso(String concept, Date date, double value, Cuenta dstAccount, CategoriaIngreso categoria) {
		super(concept, date, value);
		this.dstAccount = dstAccount;
		this.categoria = categoria;
	}
	public Cuenta getDstAccount() {
		return dstAccount;
	}
	public void setDstAccount(Cuenta dstAccount) {
		this.dstAccount = dstAccount;
	}
	public CategoriaIngreso getCategoria() {
		return categoria;
	}
	public void setCategoria(CategoriaIngreso categoria) {
		this.categoria = categoria;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
