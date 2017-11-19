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
import jdk.nashorn.internal.ir.annotations.Immutable;

/**
 * View com informações do cliente
 */
@Entity
@Immutable
@Table(name = "View_Cliente")
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
    @Size(max = 20000)
    @Column(name = "sistemas", columnDefinition="text")
    private String sistemas;

    public ViewCliente() {
    }

    /**
     * Retorna o código do cliente
     * 
     * @return Código do cliente
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Define o código do cliente
     * 
     * @param codigo 
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Retorna o bairro do cliente
     * 
     * @return Bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * Define o bairro do cliente
     * 
     * @param bairro 
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * Retorna o celular do cliente
     * 
     * @return Celular
     */
    public String getCelular() {
        return celular;
    }

    /**
     * Define o celular do cliente
     * 
     * @param celular 
     */
    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * Retorna o CEP do cliente
     * 
     * @return CEP
     */
    public String getCep() {
        return cep;
    }

    /**
     * Define o CEP do cliente
     * 
     * @param cep 
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * Retorna a cidade do cliente
     * 
     * @return Cidade
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * Define a cidade do cliente
     * 
     * @param cidade 
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * Retorna o conceito do cliente
     * 
     * @return Conceito
     */
    public String getConceito() {
        return conceito;
    }

    /**
     * Define o conceito do cliente
     * 
     * @param conceito 
     */
    public void setConceito(String conceito) {
        this.conceito = conceito;
    }

    /**
     * Retorna o CPF/CNPJ do cliente
     * 
     * @return CPF/CNPJ do cliente
     */
    public String getCpfCnpj() {
        return cpfCnpj;
    }

    /**
     * Define o CPF/CNPJ do cliente
     * 
     * @param cpfCnpj 
     */
    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    /**
     * Retorna a data de última atualização do cliente
     * 
     * @return Data de atualização
     */
    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    /**
     * Define a data de última atualização do cliente
     * 
     * @param dataAtualizacao 
     */
    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    /**
     * Retorna a data de cadastro do cliente
     * 
     * @return Data de cadastro
     */
    public Date getDataCadastro() {
        return dataCadastro;
    }

    /**
     * Define a data de cadastro do cliente
     * 
     * @param dataCadastro 
     */
    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    /**
     * Retorna o endereço do cliente
     * 
     * @return Endereço
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Define o endereço do cliente
     * 
     * @param endereco 
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Retorna o nome do cliente
     * 
     * @return Nome do cliente
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do cliente
     * 
     * @param nome 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o nome fantasia do cliente
     * 
     * @return Nome fantasia do cliente
     */
    public String getNomeFantasia() {
        return nomeFantasia;
    }

    /**
     * Define o nome fantasia do cliente
     * 
     * @param nomeFantasia 
     */
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    /**
     * Retorna o telefone principal do cliente
     * 
     * @return Telefone principal do cliente
     */
    public String getTelefonePrincipal() {
        return telefonePrincipal;
    }

    /**
     * Define o telefone principal do cliente
     * 
     * @param telefonePrincipal 
     */
    public void setTelefonePrincipal(String telefonePrincipal) {
        this.telefonePrincipal = telefonePrincipal;
    }

    /**
     * Retorna o telefone secundário do cliente
     * 
     * @return Telefone secundário do cliente
     */
    public String getTelefoneSecundario() {
        return telefoneSecundario;
    }

    /**
     * Define o telefone secundário do cliente
     * 
     * @param telefoneSecundario 
     */
    public void setTelefoneSecundario(String telefoneSecundario) {
        this.telefoneSecundario = telefoneSecundario;
    }

    /**
     * Retorna a UF do cliente
     * 
     * @return UF
     */
    public String getUf() {
        return uf;
    }

    /**
     * Define a UF do cliente
     * 
     * @param uf 
     */
    public void setUf(String uf) {
        this.uf = uf;
    }

    /**
     * Retorna a versão atual do cliente
     * 
     * @return Versão atual
     */
    public String getVersaoAtual() {
        return versaoAtual;
    }

    /**
     * Define a versão atual do cliente
     * 
     * @param versaoAtual 
     */
    public void setVersaoAtual(String versaoAtual) {
        this.versaoAtual = versaoAtual;
    }

    /**
     * Retorna a versão liberada para o cliente
     * 
     * @return Versão liberada
     */
    public String getVersaoLiberada() {
        return versaoLiberada;
    }

    /**
     * Define a versão liberada para o cliente
     * 
     * @param versaoLiberada 
     */
    public void setVersaoLiberada(String versaoLiberada) {
        this.versaoLiberada = versaoLiberada;
    }

    /**
     * Retorna o GDC do cliente
     * 
     * @return GDC
     */
    public Tecnico getGdc() {
        return gdc;
    }

    /**
     * Define o GDC do cliente
     * 
     * @param gdc 
     */
    public void setGdc(Tecnico gdc) {
        this.gdc = gdc;
    }

    /**
     * Retorna a média dos scores de atendimento
     * 
     * @return Média dos scores
     */
    public Double getScoreMedia() {
        return scoreMedia;
    }

    /**
     * Define a média dos scores de atendimento
     * 
     * @param scoreMedia 
     */
    public void setScoreMedia(Double scoreMedia) {
        this.scoreMedia = scoreMedia;
    }

    /**
     * Retona os sitemas contratados do cliente
     * 
     * @return Sistemas contratados
     */
    public String getSistemas() {
        return sistemas;
    }

    /**
     * Define os sistemas contratados do cliente
     * 
     * @param sistemas 
     */
    public void setSistemas(String sistemas) {
        this.sistemas = sistemas;
    }
    
}
