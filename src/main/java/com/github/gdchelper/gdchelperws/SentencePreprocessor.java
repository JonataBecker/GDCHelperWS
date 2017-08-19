package com.github.gdchelper.gdchelperws;

import java.text.Normalizer;

/**
 * Classe para ajustar as frases
 */
public class SentencePreprocessor {

    /**
     * Processa a frase
     *
     * @param sentence
     * @return
     */
    public String process(String sentence) {
        sentence = sentence.toLowerCase().trim();
        sentence = Normalizer.normalize(sentence, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        sentence = sentence.replaceAll("\\*\\*+", "").trim();
        sentence = sentence.replaceAll("\\-[\\s\\w]+:", "").trim();
        sentence = sentence.replaceAll("^\\s*\\[(\\d\\d/\\d\\d/\\d\\d\\d\\d )?\\d\\d:\\d\\d:\\d\\d\\] [\\s\\w]+:", "").trim();
        sentence = sentence.replaceAll("^\\s*\\-\\s+", "").trim();
        sentence = sentence.replaceAll("\\t", " ").trim();
        sentence = sentence.replaceAll("\\s+", " ").trim();
        sentence = sentence.replaceAll("\\-+>", "").trim();
        sentence = sentence.replaceAll("<.*?>:", "").trim();
        sentence = sentence.replaceAll(">+", "").trim();
        sentence = sentence.replaceAll("^\\d+[\\)\\-:]", "").trim();
        return sentence;
    }

}
