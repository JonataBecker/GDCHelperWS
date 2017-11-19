package com.github.gdchelper.jpa;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * Entidade de atendimento
 */
@Entity
@Table(name = "Atendimento")
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int cliente;
    private int tecnico;
    private int contato;
    @Column(length = 20000)
    private String mensagem;
    private String sistema;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataCriacao;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataInicio;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataFim;
    private int segundos;

    public Atendimento() {
    }
    
    public Atendimento(String mensagem) {
        this.mensagem = mensagem;
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
     * Retorna o técnico do atendimento
     * 
     * @return Técnico
     */
    public int getTecnico() {
        return tecnico;
    }

    /**
     * Define o técnico do atendimento
     * 
     * @param tecnico 
     */
    public void setTecnico(int tecnico) {
        this.tecnico = tecnico;
    }

    /**
     * Retorna o contato do atendimento
     * 
     * @return Contato
     */
    public int getContato() {
        return contato;
    }

    /**
     * Define o contato do atendimento
     * 
     * @param contato 
     */
    public void setContato(int contato) {
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
     * Retorna a data de criação do atendimento
     * 
     * @return Data de criação
     */
    public Date getDataCriacao() {
        return dataCriacao;
    }

    /**
     * Define a data de criação do atendimento
     * 
     * @param dataCriacao 
     */
    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    /**
     * Retorna a data de início do atendimento
     * 
     * @return Data de início
     */
    public Date getDataInicio() {
        return dataInicio;
    }

    /**
     * Define a data de início do atendimento
     * 
     * @param dataInicio 
     */
    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * Retorna a data de fim do atendimento
     * 
     * @return Data de fim
     */
    public Date getDataFim() {
        return dataFim;
    }

    /**
     * Define a data de fim do atendimento
     * 
     * @param dataFim 
     */
    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    /**
     * Retorna a quantidade de segundos do atendimento
     * 
     * @return Segundos
     */
    public int getSegundos() {
        return segundos;
    }

    /**
     * Define a quantidade de segundos do atendimento
     * 
     * @param segundos 
     */
    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

}
