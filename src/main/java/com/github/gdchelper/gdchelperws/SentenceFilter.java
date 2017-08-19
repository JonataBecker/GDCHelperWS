
package com.github.gdchelper.gdchelperws;

import com.github.gdchelper.jpa.Atendimento;
import java.util.function.Predicate;

/**
 * Filtro de sentenças
 */
public class SentenceFilter implements Predicate<Atendimento> {
    
    /** Tamanho mínimo da frase para que ela seja considerada */
    static final int TAMANHO_MINIMO = 5;

    @Override
    public boolean test(Atendimento atendimento) {
        if (!test(atendimento.getMensagem())) {
            return false;
        }
        return true;
    }

    public boolean test(String sentence) {
        sentence = sentence.toLowerCase();
        if (sentence.length() < TAMANHO_MINIMO) {
            return false;
        }
//        if (sentence.contains("_Rech")) {
//            return true;
//        }
        if (sentence.startsWith("to:") || sentence.startsWith("from:") || sentence.startsWith("subject:") || sentence.startsWith("enviada em:") || sentence.startsWith("enviada:")) {
            return false;
        }
        return true;
    }

}
