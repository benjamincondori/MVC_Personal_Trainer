package com.android.kotlin.personaltrainer.model.PlanEntrenamiento;

public class DetallePlanEntrenamiento {

    private int idPlanEntrenamiento;
    private int idRutina;
    private String dia_semana;

    public DetallePlanEntrenamiento() {
    }

    public DetallePlanEntrenamiento(int idPlanEntrenamiento, int idRutina, String dia_semana) {
        this.idPlanEntrenamiento = idPlanEntrenamiento;
        this.idRutina = idRutina;
        this.dia_semana = dia_semana;
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

    public String getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(String dia_semana) {
        this.dia_semana = dia_semana;
    }

    @Override
    public String toString() {
        return "{" +
                "idPlanEntrenamiento=" + idPlanEntrenamiento +
                ", idRutina=" + idRutina +
                ", dia_semana='" + dia_semana + '\'' +
                '}';
    }
}
