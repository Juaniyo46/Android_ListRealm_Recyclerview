package com.example.ejerciciolistas;

class Sitio {
    private String name;
    private String descripcion;

    public Sitio(String sitio, String descripcion) {
        this.name = sitio;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }



    public String getName() {
        return name;
    }

    public void setName(String sitio) {
        this.name = sitio;
    }


    @Override
    public String toString() {
        return "Sitio{" +
                "name='" + name + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
