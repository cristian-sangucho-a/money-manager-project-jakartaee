package modelo .entidades;

import java.io.Serializable;
import java.util.*;

import jakarta.persistence.*;

@Entity
@Table
public class Cuenta implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private double balance;
    
    public Cuenta() {
    }

	public Cuenta(int id, String name, double balance) {
		super();
		this.id = id;
		this.name = name;
		this.balance = balance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
    
}