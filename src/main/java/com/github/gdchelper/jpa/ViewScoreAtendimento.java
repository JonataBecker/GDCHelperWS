/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.gdchelper.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pichau
 */
@Entity
@Table(name = "view_score_atendimento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ViewScoreAtendimento.findAll", query = "SELECT v FROM ViewScoreAtendimento v")
    , @NamedQuery(name = "ViewScoreAtendimento.findById", query = "SELECT v FROM ViewScoreAtendimento v WHERE v.id = :id")
    , @NamedQuery(name = "ViewScoreAtendimento.findByCliente", query = "SELECT v FROM ViewScoreAtendimento v WHERE v.cliente = :cliente")
    , @NamedQuery(name = "ViewScoreAtendimento.findByContato", query = "SELECT v FROM ViewScoreAtendimento v WHERE v.contato = :contato")
    , @NamedQuery(name = "ViewScoreAtendimento.findByDataCriacao", query = "SELECT v FROM ViewScoreAtendimento v WHERE v.dataCriacao = :dataCriacao")
    , @NamedQuery(name = "ViewScoreAtendimento.findByDataFim", query = "SELECT v FROM ViewScoreAtendimento v WHERE v.dataFim = :dataFim")
    , @NamedQuery(name = "ViewScoreAtendimento.findByDataInicio", query = "SELECT v FROM ViewScoreAtendimento v WHERE v.dataInicio = :dataInicio")
    , @NamedQuery(name = "ViewScoreAtendimento.findByMensagem", query = "SELECT v FROM ViewScoreAtendimento v WHERE v.mensagem = :mensagem")
    , @NamedQuery(name = "ViewScoreAtendimento.findBySegundos", query = "SELECT v FROM ViewScoreAtendimento v WHERE v.segundos = :segundos")
    , @NamedQuery(name = "ViewScoreAtendimento.findBySistema", query = "SELECT v FROM ViewScoreAtendimento v WHERE v.sistema = :sistema")
    , @NamedQuery(name = "ViewScoreAtendimento.findByTecnico", query = "SELECT v FROM ViewScoreAtendimento v WHERE v.tecnico = :tecnico")
    , @NamedQuery(name = "ViewScoreAtendimento.findByScore", query = "SELECT v FROM ViewScoreAtendimento v WHERE v.score = :score")})
public class ViewScoreAtendimento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @Id
    private int id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cliente")
    private int cliente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contato")
    private int contato;
    @Column(name = "dataCriacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;
    @Column(name = "dataFim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFim;
    @Column(name = "dataInicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicio;
    @Size(max = 20000)
    @Column(name = "mensagem")
    private String mensagem;
    @Basic(optional = false)
    @NotNull
    @Column(name = "segundos")
    private int segundos;
    @Size(max = 255)
    @Column(name = "sistema")
    private String sistema;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tecnico")
    private int tecnico;
    @Basic(optional = false)
    @NotNull
    @Column(name = "score")
    private double score;

    public ViewScoreAtendimento() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getContato() {
        return contato;
    }

    public void setContato(int contato) {
        this.contato = contato;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public int getTecnico() {
        return tecnico;
    }

    public void setTecnico(int tecnico) {
        this.tecnico = tecnico;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
    
}
