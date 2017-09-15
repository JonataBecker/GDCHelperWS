package com.github.gdchelper.jpa;

import com.github.gdchelper.db.DataFileReader;
import com.github.gdchelper.gdchelperws.ScoreUpdater;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author JonataBecker
 */
public class DataBase {

    private final PersistenceManager persistenceManager;
    private final ScoreUpdater scoreUpdater;

    public DataBase() {
        this(null, null);
    }

    @Inject
    public DataBase(PersistenceManager persistenceManager, ScoreUpdater scoreUpdater) {
        this.persistenceManager = persistenceManager;
        this.scoreUpdater = scoreUpdater;
    }

    public void create() throws Exception {
        EntityManager em = persistenceManager.create();
        DataFileReader reader = new DataFileReader(em);
        try {
            em.getTransaction().begin();
            reader.loadTecnicos("tecnicos.csv").forEach((tec) -> {
                em.persist(tec);
            });
            em.getTransaction().commit();
            em.getTransaction().begin();
            reader.loadClientes("clientes.dsv").forEach((cli) -> {
                em.persist(cli);
            });
            reader.loadContatos("contatos.dsv").forEach((con) -> {
                em.persist(con);
            });
            reader.loadAtendimentos("atendimentos.dsv").forEach((at) -> {
                em.persist(at);
            });
            reader.loadSistemasContratados("sistemas.csv").forEach((sis) -> {
                em.persist(sis);
            });
            reader.loadAtualizacoes("atualizacoes.dsv").forEach((atu) -> {
                em.persist(atu);
            });
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    public void updateScore() throws Exception {
        scoreUpdater.updateAtendimentosSemScore();
    }

}
