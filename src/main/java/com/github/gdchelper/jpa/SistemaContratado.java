package com.github.gdchelper.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidade de sistema contratado
 */
@Entity
@Table(name = "SistemaContratado")
public class SistemaContratado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int codigoCliente;
    private int codigoSistema;
    private String descricao;
    
    public SistemaContratado() {
    }

    /**
     * Retorna o ID do sistema contratado
     * 
     * @return ID do sistema
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do sistema contratado
     * 
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o código do cliente
     * 
     * @return Código do cliente
     */
    public int getCodigoCliente() {
        return codigoCliente;
    }

    /**
     * Define o código do cliente
     * 
     * @param codigoCliente 
     */
    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    /**
     * Retorna o código do sistema
     * 
     * @return Código do sistema
     */
    public int getCodigoSistema() {
        return codigoSistema;
    }

    /**
     * Define o código do sistema
     * 
     * @param codigoSistema 
     */
    public void setCodigoSistema(int codigoSistema) {
        this.codigoSistema = codigoSistema;
    }
    
    /**
     * Retorna a descrição do sistema contratado
     * 
     * @return Descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do sistema contratado
     * 
     * @param descricao 
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
