package com.github.gdchelper.gdchelperws;

import com.github.gdchelper.jpa.Atendimento;
import com.github.gdchelper.jpa.PersistenceManager;
import com.github.gdchelper.jpa.ScoreAtendimento;
import java.io.IOException;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Atualizador de scores
 */
@ApplicationScoped
public class ScoreUpdaterImpl implements ScoreUpdater {

    private final PersistenceManager persistenceManager;
    private final GeradorScoreAtendimento geradorScoreAtendimento;

    @Inject
    public ScoreUpdaterImpl(PersistenceManager persistenceManager) throws Exception {
        this.persistenceManager = persistenceManager;
        this.geradorScoreAtendimento = new GeradorScoreAtendimento();
    }

    public void updateAtendimentosSemScore() throws IOException {
        EntityManager em = persistenceManager.create();
        Query q = em.createQuery("SELECT a FROM Atendimento a WHERE NOT EXISTS (SELECT DISTINCT(idAtendimento) FROM ScoreAtendimento WHERE idAtendimento = a.id)");
        List<Atendimento> atendimentosSemScore = q.getResultList();
        int count = 0;
        try {
            em.getTransaction().begin();
            for (Atendimento atendimento : atendimentosSemScore) {
                ScoreAtendimento score = geradorScoreAtendimento.buildScore(atendimento);
                em.persist(score);
                count++;
                if (count > 100) {
                    em.getTransaction().commit();
                    em.getTransaction().begin();
                    count = 0;
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }

    }

}
