package modelo .entidades;

import java.io.Serializable;
import java.util.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CategoriaEgreso implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
    private String name;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    public CategoriaEgreso() {
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
    

}