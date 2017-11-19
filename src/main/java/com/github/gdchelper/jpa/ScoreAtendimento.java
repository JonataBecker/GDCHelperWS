package com.github.gdchelper.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidade de score do atendimento
 */
@Entity
@Table(name = "ScoreAtendimento")
public class ScoreAtendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idAtendimento;
    private double score;

    public ScoreAtendimento() {
    }

    /**
     * Retorna o ID do score
     * 
     * @return ID do score
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID do score
     * 
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o ID do atendimento
     * 
     * @return ID do atendimento
     */
    public int getIdAtendimento() {
        return idAtendimento;
    }

    /**
     * Define o ID do atendimento
     * 
     * @param idAtendimento 
     */
    public void setIdAtendimento(int idAtendimento) {
        this.idAtendimento = idAtendimento;
    }

    /**
     * Retorna o score do atendimento
     * 
     * @return Score
     */
    public double getScore() {
        return score;
    }

    /**
     * Define o score do atendimento
     * 
     * @param score 
     */
    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ScoreAtendimento{" + "id=" + id + ", idAtendimento=" + idAtendimento + ", score=" + score + '}';
    }
    
}
