package modelo .entidades;

import java.util.*;

import jakarta.persistence.*;

@Entity
public class CategoriaIngreso {

	@Column
    private String name;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    

    public CategoriaIngreso() {
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