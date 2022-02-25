package com.example.registrocovid;

public class Modelo {

    private String nombre;
    private String barrio;
    private String resultado;
    private String doc;
    private String direccion;
    private String fecha;
    private String lugar;

    public Modelo() {
    }

    public Modelo(String nombre, String barrio, String resultado, String doc, String direccion, String fecha, String lugar) {
        this.nombre = nombre;
        this.barrio = barrio;
        this.resultado = resultado;
        this.doc = doc;
        this.direccion = direccion;
        this.fecha = fecha;
        this.lugar = lugar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
}
