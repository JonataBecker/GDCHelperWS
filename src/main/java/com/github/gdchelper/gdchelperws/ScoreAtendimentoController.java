package com.github.gdchelper.gdchelperws;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.github.gdchelper.jpa.PersistenceManager;
import com.github.gdchelper.jpa.ViewScoreAtendimento;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Controller responsável por informações do score de atendimento
 */
@Controller
public class ScoreAtendimentoController {
    
    private final Result result;
    private final PersistenceManager persistenceManager;
    private final SentenceSplitter sentenceSpliter;
    private final ApacheCategorizer categorizer;
    private final ListaFraseTreinamentos listaFrasesTreinamento;

    /**
     * @deprecated CDI eyes only
     */
    public ScoreAtendimentoController() throws IOException {
        this(null, null, null, null);
    }

    @Inject
    public ScoreAtendimentoController(Result result, PersistenceManager persistenceManager, 
            SentenceSplitter sentenceSpliter, ListaFraseTreinamentos listaFrasesTreinamento) throws IOException {
        this.result = result;
        this.persistenceManager = persistenceManager;
        this.sentenceSpliter = sentenceSpliter;
        this.categorizer = ApacheCategorizer.fromTraining();
        this.listaFrasesTreinamento = listaFrasesTreinamento;
    }
    
    /**
     * Retorna scores conforme filtro
     * 
     * @param filter 
     */
    @Get("/score")
    public void score(String filter) {
        EntityManager em = persistenceManager.create();
        Query q;
        try {
            String query = "SELECT s FROM ViewScoreAtendimento s";
            if (filter != null && !filter.isEmpty()) {
                query += " WHERE " + filter;
            }
            q = em.createQuery(query);
            List<ViewScoreAtendimento> userList = q.getResultList();
            result.use(Results.json()).withoutRoot().from(userList).serialize();
        } finally {
            em.close();
        }
    }
    
    /**
     * Adiciona frase na base de treinamento
     * 
     * @param classificacao
     * @param texto 
     */
    @Post("/score/treina")
    @Consumes("application/json")
    public void scoreTreina(String classificacao, String texto) {
        FraseTreinamento frase = new FraseTreinamento(classificacao, texto);
        listaFrasesTreinamento.add(frase);
        result.use(Results.json()).withoutRoot().from(frase).serialize();
    }
    
    /**
     * Calcula score de um atendimento
     * 
     * @param text 
     */
    @Get("/score/compute")
    public void compute(String text) {
        try {
            List<Sentence> scores = new ArrayList<>(); 
            List<String> sentences = sentenceSpliter.extractSentences(text);
            for (int i = 0; i < sentences.size(); i++) {
                String sentence = sentences.get(i);
                CategorizerResult outcomes = categorizer.categorize(sentence);
                double scoreBom = outcomes.getTrust("bom");
                double scoreRuim = outcomes.getTrust("ruim");
                double score = scoreBom - scoreRuim;
                scores.add(new Sentence(sentence, score, scoreBom, scoreRuim));
            }
            result.use(Results.json()).withoutRoot().from(scores).serialize();
        } catch (Exception e) {
            result.use(Results.status()).notFound();
        }
        
    }
    
    public static class Sentence {
        
        public final String sentence;
        public final double score;
        public final double pctBom;
        public final double pctRuim;

        public Sentence(String sentence, double score, double pctBom, double pctRuim) {
            this.sentence = sentence;
            this.score = score;
            this.pctBom = pctBom;
            this.pctRuim = pctRuim;
        }
        
    }
    
}
