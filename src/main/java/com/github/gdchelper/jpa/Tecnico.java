package com.github.gdchelper.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidade de técnico
 */
@Entity
@Table(name = "Tecnico")
public class Tecnico {
    
    public static final int SETOR_GDC = 13;
    
    @Id
    private int codigo;
    private String nome;
    private String apelido;
    private int setor;
    private String foto;

    public Tecnico() {
    }
    
    /**
     * Retorna o código do técnico
     * 
     * @return Código
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Define o código do técnico
     * 
     * @param codigo 
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Retorna o nome do técnico
     * 
     * @return Nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do técnico
     * 
     * @param nome 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o apelido do técnico
     * 
     * @return Apelido
     */
    public String getApelido() {
        return apelido;
    }

    /**
     * Define o apelido do técnico
     * 
     * @param apelido 
     */
    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    /**
     * Retorna o setor do técnico
     * 
     * @return Setor
     */
    public int getSetor() {
        return setor;
    }

    /**
     * Define o setor do técnico
     * 
     * @param setor 
     */
    public void setSetor(int setor) {
        this.setor = setor;
    }

    /**
     * Retorna a foto do técnico
     * 
     * @return Foto
     */
    public String getFoto() {
        return foto;
    }

    /**
     * Define a foto do técnico
     * 
     * @param foto 
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }

}
