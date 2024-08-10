package modelo.dto;

import java.io.*;
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

    public int getSrc() {
        return src;
    }

    public int getDst() {
        return dst;
    }

    public String getConcept() {
        return concept;
    }

    public Date getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }

    public String getTipoMovimiento() {
        return tipo_movimiento;
    }

    public String getNameSrc(){
        CuentaDAO cuentaDao = new CuentaDAO();
        Cuenta cuentaSrc = cuentaDao.getByID(this.src);
        return cuentaSrc.getName();
    }

    public String getNameDst(){
        CuentaDAO cuentaDao = new CuentaDAO();
        Cuenta cuentaDst = cuentaDao.getByID(this.dst);
        return cuentaDst.getName();
    }

	@Override
	public String toString() {
		return "MovimientoDTO [id=" + id + ", src=" + src + ", dst=" + dst + ", concept=" + concept + ", date=" + date
				+ ", value=" + value + ", tipo_movimiento=" + tipo_movimiento + "]";
	}
    
    
}