package modelo .dto;

import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class CategoriaResumenDTO implements Serializable {
	
    private String categoryName;
    private double sumarized;
    
    
    
    public CategoriaResumenDTO(String categoryName, double sumarized) {
		super();
		this.categoryName = categoryName;
		this.sumarized = sumarized;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public double getSumarized() {
		return sumarized;
	}

	public void setSumarized(double sumarized) {
		this.sumarized = sumarized;
	}
     
}