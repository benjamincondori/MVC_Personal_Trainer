package com.android.kotlin.personaltrainer.model.Rutina;

public class DetalleRutinaEjercicio {

    private int idRutina;
    private int idEjercicio;
    private int series;
    private int repeticiones;
    private int descanso;

    public DetalleRutinaEjercicio() {
    }

    public DetalleRutinaEjercicio(int idRutina, int idEjercicio, int series, int repeticiones, int descanso) {
        this.idRutina = idRutina;
        this.idEjercicio = idEjercicio;
        this.series = series;
        this.repeticiones = repeticiones;
        this.descanso = descanso;
    }

    public int getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
    }

    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public int getDescanso() {
        return descanso;
    }

    public void setDescanso(int descanso) {
        this.descanso = descanso;
    }

    @Override
    public String toString() {
        return "{" +
                "idRutina=" + idRutina +
                ", idEjercicio=" + idEjercicio +
                ", series=" + series +
                ", repeticiones=" + repeticiones +
                ", descanso=" + descanso +
                '}';
    }
}
