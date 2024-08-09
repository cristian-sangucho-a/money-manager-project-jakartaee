package modelo.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Transferencia extends Movimiento implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Cuenta srcAccount;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Cuenta dstAccount;
	@ManyToOne
	@JoinColumn(name = "Categoria_ID", insertable = false, updatable = false)
	private CategoriaTransferencia categoria;


	public Transferencia() {

	}
	
	public Transferencia(String concept, Date date, double value, Cuenta srcAccount, Cuenta dstAccount, CategoriaTransferencia categoriaTransferencia) {
		super(concept, date, value);
		this.srcAccount = srcAccount;
		this.dstAccount = dstAccount;
		this.categoria = categoriaTransferencia;
	}

	public Cuenta getSrcAccount() {
		return srcAccount;
	}

	public void setSrcAccount(Cuenta srcAccount) {
		this.srcAccount = srcAccount;
	}

	public Cuenta getDstAccount() {
		return dstAccount;
	}

	public void setDstAccount(Cuenta dstAccount) {
		this.dstAccount = dstAccount;
	}

	
	public CategoriaTransferencia getCategoriaTransferencia() {
		return categoria;
	}

	public void setCategoriaTransferencia(CategoriaTransferencia categoriaTransferencia) {
		this.categoria = categoriaTransferencia;
	}

	

}
