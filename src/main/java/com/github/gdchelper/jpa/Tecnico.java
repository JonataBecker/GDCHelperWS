package com.github.gdchelper.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public int getSetor() {
        return setor;
    }

    public void setSetor(int setor) {
        this.setor = setor;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}
