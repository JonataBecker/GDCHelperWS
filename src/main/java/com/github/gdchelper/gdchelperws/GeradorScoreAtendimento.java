package com.github.gdchelper.gdchelperws;

import com.github.gdchelper.jpa.Atendimento;
import com.github.gdchelper.jpa.ScoreAtendimento;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Nícolas Pohren
 */
public class GeradorScoreAtendimento {

    /** Categorizador */
    private final ApacheCategorizer categorizer;
    /** Quebrador de frases */
    private final SentenceSplitter sentenceSplitter;

    /**
     * Cria o gerador de scores
     */
    public GeradorScoreAtendimento() throws Exception {
        categorizer = ApacheCategorizer.fromTraining();
        sentenceSplitter = new SentenceSplitter();
    }

    /**
     * Gera o score do atendimento
     *
     * @param atendimento
     * @return ScoreAtendimento
     */
    public ScoreAtendimento buildScore(Atendimento atendimento) {
        return null;
    }

    /**
     * Gera o score do atendimento
     *
     * @param atendimento
     * @return double
     */
    public double computeScore(Atendimento atendimento) throws IOException {
        double totalScore = 0;
        List<String> sentences = sentenceSplitter.extractSentences(atendimento.getMensagem());
        for (int i = 0; i < sentences.size(); i++) {
            String sentence = sentences.get(i);
            CategorizerResult outcomes = categorizer.categorize(sentence);
            double scoreBom = outcomes.get("bom");
            double scoreRuim = outcomes.get("ruim");
            double score = scoreBom - scoreRuim;
            totalScore += score;
        }
        return totalScore / sentences.size();
    }

}
