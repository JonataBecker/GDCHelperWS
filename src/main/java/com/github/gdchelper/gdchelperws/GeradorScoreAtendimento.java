package com.github.gdchelper.gdchelperws;

import com.github.gdchelper.jpa.Atendimento;
import com.github.gdchelper.jpa.ScoreAtendimento;
import java.io.IOException;
import java.util.List;

/**
 * Classe responsável pela geração do score do atendimento ao cliente
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
    public ScoreAtendimento buildScore(Atendimento atendimento) throws IOException {
        ScoreAtendimento score = new ScoreAtendimento();
        score.setIdAtendimento(atendimento.getId());
        score.setScore(computeScore(atendimento));
        return score;
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
            double scoreBom = outcomes.getTrust("bom");
            double scoreRuim = outcomes.getTrust("ruim");
            double score = scoreBom - scoreRuim;
            totalScore += score;
        }
        double score = totalScore / sentences.size();
        if (Double.isInfinite(score) || Double.isNaN(score)) {
            score = 0;
        }
        return score;
    }

}
