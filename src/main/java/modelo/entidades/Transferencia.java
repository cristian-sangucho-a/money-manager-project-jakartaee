package modelo.entidades;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
@DiscriminatorValue("TRANSFERENCIA")
public class Transferencia extends Movimiento implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name = "SRCACCOUNT_ID")
	private Cuenta srcAccount;
	@ManyToOne
	@JoinColumn(name = "DSTACCOUNT_ID")
	private Cuenta dstAccount;
	@ManyToOne
	@JoinColumn(name = "Categoria_ID")
	private CategoriaTransferencia categoria;



	public Transferencia() {

	}
	

	public Transferencia(String concept, Date date, double amount, Cuenta srcAccountID, Cuenta dstAccountID, CategoriaTransferencia transferCategory) {
		super(concept, date, amount);
		this.srcAccount = srcAccountID;
		this.dstAccount = dstAccountID;
		this.categoria = transferCategory;
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

	public void setCategoria(CategoriaTransferencia categoriaTransferencia) {
		this.categoria = categoriaTransferencia;
	}

	

}
