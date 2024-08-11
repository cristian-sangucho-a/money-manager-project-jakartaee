package modelo .entidades;

import java.io.Serializable;
import java.util.*;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CATINGRESO")
public class CategoriaIngreso extends Categoria implements Serializable{
	private static final long serialVersionUID = 1L;
    public CategoriaIngreso() {
    }
    public CategoriaIngreso(String name) {
		super(name);
		
	}

	
}