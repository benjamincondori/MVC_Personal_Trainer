package com.android.kotlin.personaltrainer.model.EstadoFisico;

public class EstadoFisico {

    private int id;
    private float estatura;
    private float peso;
    private String fecha;
    private String enfermedades;
    private int idCliente;

    public EstadoFisico() {
    }

    public EstadoFisico(float estatura, float peso, String fecha, String enfermedades, int idCliente) {
        this.estatura = estatura;
        this.peso = peso;
        this.fecha = fecha;
        this.enfermedades = enfermedades;
        this.idCliente = idCliente;
    }

    public EstadoFisico(int id, float estatura, float peso, String fecha, String enfermedades, int idCliente) {
        this.id = id;
        this.estatura = estatura;
        this.peso = peso;
        this.fecha = fecha;
        this.enfermedades = enfermedades;
        this.idCliente = idCliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getEstatura() {
        return estatura;
    }

    public void setEstatura(float estatura) {
        this.estatura = estatura;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(String enfermedades) {
        this.enfermedades = enfermedades;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", estatura=" + estatura +
                ", peso=" + peso +
                ", fecha='" + fecha + '\'' +
                ", enfermedades='" + enfermedades + '\'' +
                ", idCliente=" + idCliente +
                '}';
    }
}
