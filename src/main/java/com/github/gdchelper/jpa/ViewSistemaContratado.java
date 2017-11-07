/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.gdchelper.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@Entity
@Table(name = "view_sistema_contratado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ViewSistemaContratado.findAll", query = "SELECT v FROM ViewSistemaContratado v")
    , @NamedQuery(name = "ViewSistemaContratado.findById", query = "SELECT v FROM ViewSistemaContratado v WHERE v.id = :id")
    , @NamedQuery(name = "ViewSistemaContratado.findByCodigoCliente", query = "SELECT v FROM ViewSistemaContratado v WHERE v.codigoCliente = :codigoCliente")
    , @NamedQuery(name = "ViewSistemaContratado.findByCodigoSistema", query = "SELECT v FROM ViewSistemaContratado v WHERE v.codigoSistema = :codigoSistema")
    , @NamedQuery(name = "ViewSistemaContratado.findByDescricao", query = "SELECT v FROM ViewSistemaContratado v WHERE v.descricao = :descricao")
    , @NamedQuery(name = "ViewSistemaContratado.findByScore", query = "SELECT v FROM ViewSistemaContratado v WHERE v.score = :score")})
public class ViewSistemaContratado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @Id
    private int id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigoCliente")
    private int codigoCliente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigoSistema")
    private int codigoSistema;
    @Size(max = 255)
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "score")
    private double score;

    public ViewSistemaContratado() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public int getCodigoSistema() {
        return codigoSistema;
    }

    public void setCodigoSistema(int codigoSistema) {
        this.codigoSistema = codigoSistema;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
    
}
