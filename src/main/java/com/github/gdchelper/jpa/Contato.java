package com.github.gdchelper.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidade de contato do cliente
 */
@Entity
@Table(name = "Contato")
public class Contato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int codigo;
    private int codigoCliente;
    private String nome;
    private String cargo;
    private String email;
    private String telefone;

    public Contato() {
    }

    /**
     * Retorna o ID do contato
     * 
     * @return ID do contato
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do contato
     * 
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o código do contato do cliente
     * 
     * @return Código do contato
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Define o código do contato do cliente
     * 
     * @param codigo 
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
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
     * Retorna o nome do contato
     * 
     * @return Nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do contato
     * 
     * @param nome 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o cargo do contato
     * 
     * @return Cargo
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Define o cargo do contato
     * 
     * @param cargo 
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Retorna o e-mail do contato
     * 
     * @return E-mail
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o e-mail do contato
     * 
     * @param email 
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna o telefone do contato
     * 
     * @return Telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Define o telefone do contato
     * 
     * @param telefone 
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
}
