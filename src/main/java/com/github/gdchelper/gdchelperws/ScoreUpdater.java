package com.github.gdchelper.gdchelperws;

import com.github.gdchelper.jpa.Atendimento;
import com.github.gdchelper.jpa.PersistenceManager;
import com.github.gdchelper.jpa.ScoreAtendimento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Pichau
 */
public class ScoreUpdater {

    private final PersistenceManager persistenceManager;
    private final GeradorScoreAtendimento geradorScoreAtendimento;

    public ScoreUpdater(PersistenceManager persistenceManager) throws Exception {
        this.persistenceManager = persistenceManager;
        this.geradorScoreAtendimento = new GeradorScoreAtendimento();
    }

    public void updateAtendimentosSemScore() {
        EntityManager em = persistenceManager.create();
        Query q = em.createQuery("SELECT a FROM Atendimento a WHERE <não tem score>");
        List<Atendimento> atendimentosSemScore = q.getResultList();
        try {
            for (Atendimento atendimento : atendimentosSemScore) {
                ScoreAtendimento score = geradorScoreAtendimento.buildScore(atendimento);
                em.getTransaction().begin();
                em.persist(score);
                em.getTransaction().commit();

            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
        em.close();

    }

}
