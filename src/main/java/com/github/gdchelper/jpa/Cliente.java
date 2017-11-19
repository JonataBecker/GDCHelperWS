package com.github.gdchelper.jpa;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entidade de cliente
 */
@Entity
@Table(name = "Cliente")
public class Cliente {
    
    @Id
    private int codigo;
    private String nome;
    private String nomeFantasia;
    private String cpfCnpj;
    private String telefonePrincipal;
    private String telefoneSecundario;
    private String celular;
    private String endereco;
    private String bairro;
    private String cidade;
    private String cep;
    private String uf;
    private Date dataCadastro;
    @ManyToOne
    @JoinColumn(name = "gdc")
    private Tecnico gdc;
    private String conceito;
    private String versaoAtual;
    private Date dataAtualizacao;
    private String versaoLiberada;
    
    public Cliente() {
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
    
}
