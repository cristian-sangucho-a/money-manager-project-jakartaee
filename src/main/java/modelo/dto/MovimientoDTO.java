package modelo.dto;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import modelo.dao.CuentaDAO;
import modelo.entidades.Cuenta;

/**
 * 
 */
public class MovimientoDTO {

    private int id;
    private int src;
    private int dst;
    private String concept;
    private Date date;
    private double value;
    private String tipo_movimiento;

    public MovimientoDTO() {
    }

    public MovimientoDTO(int id, int src, int dst, String concept, Date date, double value, String tipo_movimiento) {
        this.id = id;
        this.src = src;
        this.dst = dst;
        this.concept = concept;
        this.date = date;
        this.value = value;
        this.tipo_movimiento = tipo_movimiento;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSrcAccount() {
		return src;
	}

	public void setSrcAccount(int src) {
		this.src = src;
	}

	public int getDstAccount() {
		return dst;
	}

	public void setDstAccount(int dst) {
		this.dst = dst;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getTipo_movimiento() {
		return tipo_movimiento;
	}

	public void setTipo_movimiento(String tipo_movimiento) {
		this.tipo_movimiento = tipo_movimiento;
	}

	public String getNameSrcAccount(){
        CuentaDAO cuentaDao = new CuentaDAO();
        if(src!=0) {
        	Cuenta cuentaSrc = cuentaDao.getByID(this.src);
            return cuentaSrc.getName();
        }
        return null;
    }

    public String getNameDstAccount(){
        CuentaDAO cuentaDao = new CuentaDAO();
        if(dst!=0) {
        	Cuenta cuentaDst = cuentaDao.getByID(this.dst);
        	return cuentaDst.getName();
        }
        return null; 
        
    }
    
    public String getStringDate(){
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

	@Override
	public String toString() {
		return "MovimientoDTO [id=" + id + ", src=" + src + ", dst=" + dst + ", concept=" + concept + ", date=" + date
				+ ", value=" + value + ", tipo_movimiento=" + tipo_movimiento + "]";
	}
    
    
}