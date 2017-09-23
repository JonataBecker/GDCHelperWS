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
@Entity
@Table(name = "View_Atendimento_Periodo_Quantidade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ViewAtendimentoPeriodoQuantidade.findAll", query = "SELECT v FROM ViewAtendimentoPeriodoQuantidade v")
    , @NamedQuery(name = "ViewAtendimentoPeriodoQuantidade.findByPeriodo", query = "SELECT v FROM ViewAtendimentoPeriodoQuantidade v WHERE v.periodo = :periodo")
    , @NamedQuery(name = "ViewAtendimentoPeriodoQuantidade.findByPeriodoUSA", query = "SELECT v FROM ViewAtendimentoPeriodoQuantidade v WHERE v.periodoUSA = :periodoUSA")
    , @NamedQuery(name = "ViewAtendimentoPeriodoQuantidade.findByQuantidade", query = "SELECT v FROM ViewAtendimentoPeriodoQuantidade v WHERE v.quantidade = :quantidade")})
public class ViewAtendimentoPeriodoQuantidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 7)
    @Column(name = "Periodo")
    @Id
    private String periodo;
    @Size(max = 7)
    @Column(name = "Periodo_USA")
    private String periodoUSA;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Quantidade")
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
