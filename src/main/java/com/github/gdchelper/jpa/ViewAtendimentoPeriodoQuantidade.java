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
 *
 * @author JonataBecker
 */
public class ViewAtendimentoPeriodoQuantidade implements Serializable {

    private static final long serialVersionUID = 1L;
    private String periodo;
    private String periodoUSA;
    private long quantidade;

    public ViewAtendimentoPeriodoQuantidade() {
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

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }

}
