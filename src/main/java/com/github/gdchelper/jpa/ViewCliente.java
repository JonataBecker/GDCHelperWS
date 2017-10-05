/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.gdchelper.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pichau
 */
@Entity
@Table(name = "view_cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ViewCliente.findAll", query = "SELECT v FROM ViewCliente v")
    , @NamedQuery(name = "ViewCliente.findByCodigo", query = "SELECT v FROM ViewCliente v WHERE v.codigo = :codigo")
    , @NamedQuery(name = "ViewCliente.findByBairro", query = "SELECT v FROM ViewCliente v WHERE v.bairro = :bairro")
    , @NamedQuery(name = "ViewCliente.findByCelular", query = "SELECT v FROM ViewCliente v WHERE v.celular = :celular")
    , @NamedQuery(name = "ViewCliente.findByCep", query = "SELECT v FROM ViewCliente v WHERE v.cep = :cep")
    , @NamedQuery(name = "ViewCliente.findByCidade", query = "SELECT v FROM ViewCliente v WHERE v.cidade = :cidade")
    , @NamedQuery(name = "ViewCliente.findByConceito", query = "SELECT v FROM ViewCliente v WHERE v.conceito = :conceito")
    , @NamedQuery(name = "ViewCliente.findByCpfCnpj", query = "SELECT v FROM ViewCliente v WHERE v.cpfCnpj = :cpfCnpj")
    , @NamedQuery(name = "ViewCliente.findByDataAtualizacao", query = "SELECT v FROM ViewCliente v WHERE v.dataAtualizacao = :dataAtualizacao")
    , @NamedQuery(name = "ViewCliente.findByDataCadastro", query = "SELECT v FROM ViewCliente v WHERE v.dataCadastro = :dataCadastro")
    , @NamedQuery(name = "ViewCliente.findByEndereco", query = "SELECT v FROM ViewCliente v WHERE v.endereco = :endereco")
    , @NamedQuery(name = "ViewCliente.findByNome", query = "SELECT v FROM ViewCliente v WHERE v.nome = :nome")
    , @NamedQuery(name = "ViewCliente.findByNomeFantasia", query = "SELECT v FROM ViewCliente v WHERE v.nomeFantasia = :nomeFantasia")
    , @NamedQuery(name = "ViewCliente.findByTelefonePrincipal", query = "SELECT v FROM ViewCliente v WHERE v.telefonePrincipal = :telefonePrincipal")
    , @NamedQuery(name = "ViewCliente.findByTelefoneSecundario", query = "SELECT v FROM ViewCliente v WHERE v.telefoneSecundario = :telefoneSecundario")
    , @NamedQuery(name = "ViewCliente.findByUf", query = "SELECT v FROM ViewCliente v WHERE v.uf = :uf")
    , @NamedQuery(name = "ViewCliente.findByVersaoAtual", query = "SELECT v FROM ViewCliente v WHERE v.versaoAtual = :versaoAtual")
    , @NamedQuery(name = "ViewCliente.findByVersaoLiberada", query = "SELECT v FROM ViewCliente v WHERE v.versaoLiberada = :versaoLiberada")
    , @NamedQuery(name = "ViewCliente.findByGdc", query = "SELECT v FROM ViewCliente v WHERE v.gdc = :gdc")
    , @NamedQuery(name = "ViewCliente.findByScoreMedia", query = "SELECT v FROM ViewCliente v WHERE v.scoreMedia = :scoreMedia")})
public class ViewCliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo")
    @Id
    private int codigo;
    @Size(max = 255)
    @Column(name = "bairro")
    private String bairro;
    @Size(max = 255)
    @Column(name = "celular")
    private String celular;
    @Size(max = 255)
    @Column(name = "cep")
    private String cep;
    @Size(max = 255)
    @Column(name = "cidade")
    private String cidade;
    @Size(max = 255)
    @Column(name = "conceito")
    private String conceito;
    @Size(max = 255)
    @Column(name = "cpfCnpj")
    private String cpfCnpj;
    @Column(name = "dataAtualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;
    @Column(name = "dataCadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @Size(max = 255)
    @Column(name = "endereco")
    private String endereco;
    @Size(max = 255)
    @Column(name = "nome")
    private String nome;
    @Size(max = 255)
    @Column(name = "nomeFantasia")
    private String nomeFantasia;
    @Size(max = 255)
    @Column(name = "telefonePrincipal")
    private String telefonePrincipal;
    @Size(max = 255)
    @Column(name = "telefoneSecundario")
    private String telefoneSecundario;
    @Size(max = 255)
    @Column(name = "uf")
    private String uf;
    @Size(max = 255)
    @Column(name = "versaoAtual")
    private String versaoAtual;
    @Size(max = 255)
    @Column(name = "versaoLiberada")
    private String versaoLiberada;
    @ManyToOne
    @JoinColumn(name = "gdc")
    private Tecnico gdc;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "score_media")
    private Double scoreMedia;

    public ViewCliente() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getConceito() {
        return conceito;
    }

    public void setConceito(String conceito) {
        this.conceito = conceito;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getTelefonePrincipal() {
        return telefonePrincipal;
    }

    public void setTelefonePrincipal(String telefonePrincipal) {
        this.telefonePrincipal = telefonePrincipal;
    }

    public String getTelefoneSecundario() {
        return telefoneSecundario;
    }

    public void setTelefoneSecundario(String telefoneSecundario) {
        this.telefoneSecundario = telefoneSecundario;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getVersaoAtual() {
        return versaoAtual;
    }

    public void setVersaoAtual(String versaoAtual) {
        this.versaoAtual = versaoAtual;
    }

    public String getVersaoLiberada() {
        return versaoLiberada;
    }

    public void setVersaoLiberada(String versaoLiberada) {
        this.versaoLiberada = versaoLiberada;
    }

    public Tecnico getGdc() {
        return gdc;
    }

    public void setGdc(Tecnico gdc) {
        this.gdc = gdc;
    }

    public Double getScoreMedia() {
        return scoreMedia;
    }

    public void setScoreMedia(Double scoreMedia) {
        this.scoreMedia = scoreMedia;
    }
    
}
