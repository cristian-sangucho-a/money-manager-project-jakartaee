package modelo.dto;

import java.io.*;
import java.util.*;

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
        CuentaDao cuentaDao = new CuentaDao();
        Cuenta cuentaSrc = cuentaDao.getById(this.src);
        return cuentaSrc.getName();
    }

    public String getNameDst(){
        CuentaDao cuentaDao = new CuentaDao();
        Cuenta cuentaDst = cuentaDao.getById(this.dst);
        return cuentaDst.getName();
    }
}