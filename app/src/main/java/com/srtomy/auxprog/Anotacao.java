package com.srtomy.auxprog;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.srtomy.auxprog.converter.LocalDateTimeConverter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Anotacao  implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;
    private String titulo;
    private String categoria;

    @TypeConverters(LocalDateTimeConverter.class)
    private LocalDateTime dtCriacao;
    private String descricao;

    public Anotacao(String titulo) {
        this.titulo = titulo;
    }

    public Anotacao() {
    }

    //get e set
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(LocalDateTime dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
