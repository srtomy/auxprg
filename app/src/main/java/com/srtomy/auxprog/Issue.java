package com.srtomy.auxprog;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Issue {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;
    private String titulo;
    private String categorias;
    private String dtCriacao;
    private String dtSolucao;
    private String descricao;
    private String solucao;

    //get e set
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }

    public String getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(String dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    public String getDtSolucao() {
        return dtSolucao;
    }

    public void setDtSolucao(String dtSolucao) {
        this.dtSolucao = dtSolucao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }
}
