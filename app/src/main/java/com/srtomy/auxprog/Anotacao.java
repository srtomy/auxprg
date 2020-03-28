package com.srtomy.auxprog;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.srtomy.auxprog.converter.LocalDateTimeConverter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Anotacao {
    @PrimaryKey
    @NonNull
    private long id;
    private String titulo;
    private String categoria;

    @TypeConverters(LocalDateTimeConverter.class)
    private LocalDateTime dtCriacao;
    private String descricao;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Anotacao anotacao = (Anotacao) o;
        return id == anotacao.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
