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
 * View com informações do score de atendimento
 */
@Entity
@Table(name = "View_Score_Atendimento")
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

    /**
     * Retorna o ID do score
     * 
     * @return ID do score
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do score
     * 
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o cliente do atendimento
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
