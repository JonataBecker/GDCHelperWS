package com.github.gdchelper.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * View com informações de atendimentos de clientes
 */
@Entity
@Immutable
@Table(name = "View_Cliente_Atendimento")
public class ViewClienteAtendimento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;
    @Column(name = "cliente")
    private int cliente;
    @Column(name = "Data")
    private Date data;
    @Column(name = "codigoContato")
    private int codigoContato;
    @Column(name = "Tecnico")
    private String tecnico;
    @Column(name = "Sistema")
    private String sistema;
    @Column(name = "Contato")
    private String contato;
    @Column(name = "Mensagem")
    private String mensagem;
    @Column(name = "score")
    private double score;

    public ViewClienteAtendimento() {
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getCodigoContato() {
        return codigoContato;
    }

    public void setCodigoContato(int codigoContato) {
        this.codigoContato = codigoContato;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
