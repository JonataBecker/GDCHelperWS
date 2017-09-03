package com.github.gdchelper.gdchelperws;

import java.util.Map;

/**
 * Resultado do classficiador
 */
public class CategorizerResult {
    
    /** Resultado */
    private final Map<String, Double> classes;

    /**
     * Cria o resultado
     * 
     * @param classes 
     */
    public CategorizerResult(Map<String, Double> classes) {
        this.classes = classes;
    }
    
    public String getBest() {
        String best = null;
        for (Map.Entry<String, Double> entry : classes.entrySet()) {
            if (best == null || entry.getValue() > classes.get(best)) {
                best = entry.getKey();
            }
        }
        return best;
    }
    
    /**
     * Retorna a confiança na categoria
     * 
     * @param category
     * @return double
     */
    public double getTrust(String category) {
        return classes.get(category);
    }
    
}
