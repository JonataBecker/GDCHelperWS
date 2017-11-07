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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@Entity
@Table(name = "view_contato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ViewContato.findAll", query = "SELECT v FROM ViewContato v")
    , @NamedQuery(name = "ViewContato.findById", query = "SELECT v FROM ViewContato v WHERE v.id = :id")
    , @NamedQuery(name = "ViewContato.findByCargo", query = "SELECT v FROM ViewContato v WHERE v.cargo = :cargo")
    , @NamedQuery(name = "ViewContato.findByCodigo", query = "SELECT v FROM ViewContato v WHERE v.codigo = :codigo")
    , @NamedQuery(name = "ViewContato.findByCodigoCliente", query = "SELECT v FROM ViewContato v WHERE v.codigoCliente = :codigoCliente")
    , @NamedQuery(name = "ViewContato.findByEmail", query = "SELECT v FROM ViewContato v WHERE v.email = :email")
    , @NamedQuery(name = "ViewContato.findByNome", query = "SELECT v FROM ViewContato v WHERE v.nome = :nome")
    , @NamedQuery(name = "ViewContato.findByTelefone", query = "SELECT v FROM ViewContato v WHERE v.telefone = :telefone")
    , @NamedQuery(name = "ViewContato.findByScore", query = "SELECT v FROM ViewContato v WHERE v.score = :score")})
public class ViewContato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @Id
    private int id;
    @Size(max = 255)
    @Column(name = "cargo")
    private String cargo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo")
    private int codigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigoCliente")
    private int codigoCliente;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email")
    private String email;
    @Size(max = 255)
    @Column(name = "nome")
    private String nome;
    @Size(max = 255)
    @Column(name = "telefone")
    private String telefone;
    @Basic(optional = false)
    @NotNull
    @Column(name = "score")
    private double score;
    @Column(name = "DataUltimoAtendimento")
    private Date dataUltimoAtendimento;

    public ViewContato() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Date getDataUltimaAtualizacao() {
        return dataUltimoAtendimento;
    }

    public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
        this.dataUltimoAtendimento = dataUltimaAtualizacao;
    }
    
}
