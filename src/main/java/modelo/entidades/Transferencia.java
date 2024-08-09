package modelo.entidades;

import java.io.Serializable;
import java.time.LocalDate;

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
public class Transferencia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private double amount;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Cuenta srcAccount;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Cuenta dstAccount;
	@Column
	private LocalDate date;
	@Column
	private String concept;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idCategoriaTransferencia")
	private CategoriaTransferencia categoriaTransferencia;

	public Transferencia() {

	}

	public Transferencia(double amount, Cuenta srcAccount, Cuenta dstAccount, LocalDate date, String concept,
			CategoriaTransferencia categoriaTransferencia) {
		this.amount = amount;
		this.srcAccount = srcAccount;
		this.dstAccount = dstAccount;
		this.date = date;
		this.concept = concept;
		this.categoriaTransferencia = categoriaTransferencia;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public CategoriaTransferencia getCategoriaTransferencia() {
		return categoriaTransferencia;
	}

	public void setCategoriaTransferencia(CategoriaTransferencia categoriaTransferencia) {
		this.categoriaTransferencia = categoriaTransferencia;
	}

	

}
