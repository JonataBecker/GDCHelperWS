package com.github.gdchelper.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * View com informações de quantidade/Tempo total de atendimentos por período
 */
@Entity
@Immutable
@Table(name = "View_Atendimento_Periodo_Tecnico")
public class ViewAtendimentoPeriodoTecnico implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Periodo", updatable = false, nullable = false)
    private String periodo;
    @Column(name = "Periodo_USA")
    private String periodoUSA;
    @Column(name = "Quantidade")
    private BigDecimal quantidade;
    @Column(name = "Tempo")
    private String tempo;
    @Column(name = "Tecnico")
    private String tecnico;

    public ViewAtendimentoPeriodoTecnico() {
    }

    /**
     * Retorna o período do atendimento
     * 
     * @return Período
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     * Define o período do atendimento
     * 
     * @param periodo 
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    /**
     * Retorna o período do atendimento
     * 
     * @return Período
     */
    public String getPeriodoUSA() {
        return periodoUSA;
    }

    /**
     * Define o período do atendimento
     * 
     * @param periodoUSA 
     */
    public void setPeriodoUSA(String periodoUSA) {
        this.periodoUSA = periodoUSA;
    }

    /**
     * Retorna a quantidade de atendimentos
     * 
     * @return Quantidade de atendimentos
     */
    public BigDecimal getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade de atendimentos
     * 
     * @param quantidade 
     */
    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Retorna o tempo de atendimento
     * 
     * @return Tempo de atendimento
     */
    public String getTempo() {
        return tempo;
    }

    /**
     * Define o tempo de atendimento
     * 
     * @param tempo 
     */
    public void setTempo(String tempo) {
        this.tempo = tempo;
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
    
}
