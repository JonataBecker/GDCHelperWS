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

    /**
     * Retorna ID do atendimento
     * 
     * @return ID do atendimento
     */
    public int getId() {
        return id;
    }

    /**
     * Define ID do atendimento
     * 
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna cliente do atendimento
     * 
     * @return Cliente
     */
    public int getCliente() {
        return cliente;
    }

    /**
     * Define o cliente do atendimento
     * 
     * @param cliente 
     */
    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    /**
     * Retorna a data do atendimento
     * 
     * @return Data
     */
    public Date getData() {
        return data;
    }

    /**
     * Define a data do atendimento
     * 
     * @param data 
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * Retorna o código do contato do atendimento
     * 
     * @return Código do contato
     */
    public int getCodigoContato() {
        return codigoContato;
    }

    /**
     * Define o código do contato do atendimento
     * 
     * @param codigoContato 
     */
    public void setCodigoContato(int codigoContato) {
        this.codigoContato = codigoContato;
    }

    /**
     * Retorna o técnico do atendimento
     * 
     * @return Técnico
     */
    public String getTecnico() {
        return tecnico;
    }

    /**
     * Define o técnico do atendimento
     * 
     * @param tecnico 
     */
    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    /**
     * Retorna o sistema do atendimento
     * 
     * @return Sistema
     */
    public String getSistema() {
        return sistema;
    }

    /**
     * Define o sistema do atendimento
     * 
     * @param sistema 
     */
    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    /**
     * Retorna o contato do atendimento
     * 
     * @return Contato
     */
    public String getContato() {
        return contato;
    }

    /**
     * Define o contato do atendimento
     * 
     * @param contato 
     */
    public void setContato(String contato) {
        this.contato = contato;
    }

    /**
     * Retorna a mensagem do atendimento
     * 
     * @return Mensagem
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Define a mensagem do atendimento
     * 
     * @param mensagem 
     */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    /**
     * Retorna o score do atendimento
     * 
     * @return Score
     */
    public double getScore() {
        return score;
    }

    /**
     * Define o score do atendimento
     * 
     * @param score 
     */
    public void setScore(double score) {
        this.score = score;
    }

}
