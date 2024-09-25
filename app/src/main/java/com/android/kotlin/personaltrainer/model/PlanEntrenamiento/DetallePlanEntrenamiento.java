package com.android.kotlin.personaltrainer.model.PlanEntrenamiento;

public class DetallePlanEntrenamiento {

    private int idPlanEntrenamiento;
    private int idRutina;
    private String dia;

    public DetallePlanEntrenamiento() {
    }

    public DetallePlanEntrenamiento(int idPlanEntrenamiento, int idRutina, String dia) {
        this.idPlanEntrenamiento = idPlanEntrenamiento;
        this.idRutina = idRutina;
        this.dia = dia;
    }

    public int getIdPlanEntrenamiento() {
        return idPlanEntrenamiento;
    }

    public void setIdPlanEntrenamiento(int idPlanEntrenamiento) {
        this.idPlanEntrenamiento = idPlanEntrenamiento;
    }

    public int getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    @Override
    public String toString() {
        return "{" +
                "idPlanEntrenamiento=" + idPlanEntrenamiento +
                ", idRutina=" + idRutina +
                ", dia_semana='" + dia + '\'' +
                '}';
    }
}
