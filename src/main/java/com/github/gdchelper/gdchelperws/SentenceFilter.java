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
        // Se é só uma palavra
        if (sentence.indexOf(' ') < 0) {
            return false;
        }
        // Se é uma tag
        if (sentence.matches("^\\[[^\\[\\]]+\\]$")) {
            return false;
        }
        // Tag de e-mail
        if (sentence.equals("-------- mensagem encaminhada --------")) {
            return false;
        }
        // Tags de email
        if (sentence.startsWith("to:") || sentence.startsWith("from:") || sentence.startsWith("subject:") || sentence.startsWith("enviada em:") || 
                sentence.startsWith("enviada:") || sentence.startsWith("data:") || sentence.startsWith("para:") || sentence.startsWith("de:") ||
                sentence.startsWith("cc:") || sentence.startsWith("assunto:") || sentence.startsWith("sent:")) {
            return false;
        }
        return true;
    }

}
