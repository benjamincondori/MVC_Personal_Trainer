package com.android.kotlin.personaltrainer.model.Ejercicio;

public class Ejercicio {

    private int id;
    private String nombre;
    private String descripcion;
    private String imagen;
    private String urlVideo;
    private int idCategoria;

    public Ejercicio() {
    }

    public Ejercicio(String nombre, String descripcion, String imagen, String urlVideo, int idCategoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.urlVideo = urlVideo;
        this.idCategoria = idCategoria;
    }

    public Ejercicio(int id, String nombre, String descripcion, String imagen, String urlVideo, int idCategoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.urlVideo = urlVideo;
        this.idCategoria = idCategoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return this.getNombre();
    }
}
