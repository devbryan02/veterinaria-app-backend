package com.app.veterinaria.domain.model;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public class Mascota {

    private UUID id;
    private String nombre;
    private String especie;
    private String raza;
    private String edad;
    private char sexo;
    private String temperamento;
    private String condicionReproductiva;
    private String color;
    private Dueno dueno;

    private List<Imagen> imagenes;

    //constructor vacio
    public Mascota() {}

    //constructor lleno
    public Mascota(UUID id, String nombre, String especie, String raza, String edad, char sexo, String temperamento, String condicionReproductiva, String color, Dueno dueno, List<Imagen> imagenes) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.sexo = sexo;
        this.temperamento = temperamento;
        this.condicionReproductiva = condicionReproductiva;
        this.color = color;
        this.dueno = dueno;
        this.imagenes = imagenes;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getTemperamento() {
        return temperamento;
    }

    public void setTemperamento(String temperamento) {
        this.temperamento = temperamento;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCondicionReproductiva() {
        return condicionReproductiva;
    }

    public void setCondicionReproductiva(String condicionReproductiva) {
        this.condicionReproductiva = condicionReproductiva;
    }

    public Dueno getDueno() {
        return dueno;
    }

    public void setDueno(Dueno dueno) {
        this.dueno = dueno;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }
}
