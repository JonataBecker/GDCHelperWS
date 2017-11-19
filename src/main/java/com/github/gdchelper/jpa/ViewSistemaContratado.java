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
 * View com informações dos sistemas contratados
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

    /**
     * Retorna o ID do sistema contratado
     * 
     * @return ID do sistema
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do sistema contratado
     * 
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o código do cliente
     * 
     * @return Código do cliente
     */
    public int getCodigoCliente() {
        return codigoCliente;
    }

    /**
     * Define o código do cliente
     * 
     * @param codigoCliente 
     */
    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    /**
     * Retorna o código do sistema
     * 
     * @return Código do sistema
     */
    public int getCodigoSistema() {
        return codigoSistema;
    }

    /**
     * Define o código do sistema
     * 
     * @param codigoSistema 
     */
    public void setCodigoSistema(int codigoSistema) {
        this.codigoSistema = codigoSistema;
    }

    /**
     * Retorna a descrição do sistema contratado
     * 
     * @return Descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do sistema contratado
     * 
     * @param descricao 
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna o score do sistema contratado
     * 
     * @return Score
     */
    public double getScore() {
        return score;
    }

    /**
     * Define o score do sistema contratado
     * 
     * @param score 
     */
    public void setScore(double score) {
        this.score = score;
    }
    
}
