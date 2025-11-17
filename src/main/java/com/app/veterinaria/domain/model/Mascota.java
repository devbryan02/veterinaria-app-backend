package com.app.veterinaria.domain.model;

import com.app.veterinaria.domain.emuns.EstadoMascota;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Mascota {

    private UUID id;
    private String nombre;
    private String especie;
    private String raza;
    private String sexo;
    private String temperamento;
    private String condicionReproductiva;
    private String color;
    private Dueno dueno;
    private LocalDate fechaCreacion;
    private String identificador;
    private Integer anios;
    private Integer meses;
    private EstadoMascota estado;

    private List<Imagen> imagenes;

    //constructor vacio
    public Mascota() {}

    //constructor lleno
    public Mascota(UUID id, String nombre, String especie, String raza, String sexo, String temperamento, String condicionReproductiva, String color, Dueno dueno, List<Imagen> imagenes, LocalDate fechaCreacion, String identificador, Integer anios, Integer meses, EstadoMascota estado) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.sexo = sexo;
        this.temperamento = temperamento;
        this.condicionReproductiva = condicionReproductiva;
        this.color = color;
        this.dueno = dueno;
        this.imagenes = imagenes;
        this.fechaCreacion = fechaCreacion;
        this.identificador = identificador;
        this.anios = anios;
        this.meses = meses;
        this.estado = estado;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
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

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Integer getAnios() {
        return anios;
    }

    public void setAnios(Integer anios){
        this.anios = anios;
    }

    public Integer getMeses() {
        return meses;
    }

    public void setMeses(Integer meses) {
        this.meses = meses;
    }

    public EstadoMascota getEstado(){
        return estado;
    }

    public void setEstado(EstadoMascota estado){
        this.estado = estado;
    }

}
