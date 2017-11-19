package com.github.gdchelper.jpa;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidade de atualização
 */
@Entity
@Table(name = "Atualizacao")
public class Atualizacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int codigoCliente;
    private Date data;
    private String versao;

    public Atualizacao() {
        
    }

    /**
     * Retorna o ID da atualização
     * 
     * @return ID da atualização
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID da atualização
     * 
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o código do cliente da atualização
     * 
     * @return Código do cliente
     */
    public int getCodigoCliente() {
        return codigoCliente;
    }

    /**
     * Define o código do cliente da atualização
     * 
     * @param codigoCliente 
     */
    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    /**
     * Retorna a data da atualização
     * 
     * @return Data da atualização
     */
    public Date getData() {
        return data;
    }

    /**
     * Define a data da atualização
     * 
     * @param data 
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * Retorna a versão da atualização
     * 
     * @return Versão da atualização
     */
    public String getVersao() {
        return versao;
    }

    /**
     * Define a versão da atualização
     * 
     * @param versao 
     */
    public void setVersao(String versao) {
        this.versao = versao;
    }
    
}
