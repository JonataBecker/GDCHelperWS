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
 *
 * @author JonataBecker
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

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodoUSA() {
        return periodoUSA;
    }

    public void setPeriodoUSA(String periodoUSA) {
        this.periodoUSA = periodoUSA;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }
    
}
