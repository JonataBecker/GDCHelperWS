package com.github.gdchelper.gdchelperws;

import java.util.Objects;

/**
 * Classe responsável por informações das frases de treinamento
 */
public class FraseTreinamento {
    
    private final String categoria;
    private final String frase;

    public FraseTreinamento(String categoria, String frase) {
        this.categoria = categoria;
        this.frase = frase;
    }

    /**
     * Retorna a categoria da frase de treinamento
     * 
     * @return Categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Retorna a frase de treinamento
     * 
     * @return Frase
     */
    public String getFrase() {
        return frase;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.categoria);
        hash = 67 * hash + Objects.hashCode(this.frase);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FraseTreinamento other = (FraseTreinamento) obj;
        if (!Objects.equals(this.categoria, other.categoria)) {
            return false;
        }
        if (!Objects.equals(this.frase, other.frase)) {
            return false;
        }
        return true;
    }
    
    
    
}
