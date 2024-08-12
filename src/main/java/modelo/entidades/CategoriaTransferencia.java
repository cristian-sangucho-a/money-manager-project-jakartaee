package modelo.entidades;

import java.io.Serializable;
import java.util.*;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
@DiscriminatorValue("CATTRANSFERENCIA")
public class CategoriaTransferencia extends Categoria implements Serializable {

	private static final long serialVersionUID = 1L;

	public CategoriaTransferencia() {
	}

	public CategoriaTransferencia(String name) {
		super(name);
	}
	
}