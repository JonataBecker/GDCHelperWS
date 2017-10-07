package com.github.gdchelper.jpa;

import java.io.Serializable;
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
@Table(name = "View_Atendimento_Periodo_Tempo")
public class ViewAtendimentoPeriodoTempo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Periodo", updatable = false, nullable = false)
    private String periodo;
    @Column(name = "Periodo_USA")
    private String periodoUSA;
    @Column(name = "Tempo")
    private String tempo;

    public ViewAtendimentoPeriodoTempo() {
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

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

}
