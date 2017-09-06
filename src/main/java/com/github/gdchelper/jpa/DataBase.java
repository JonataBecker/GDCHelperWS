package com.github.gdchelper.jpa;

import com.github.gdchelper.db.DataFileReader;
import com.github.gdchelper.gdchelperws.ScoreUpdater;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author JonataBecker
 */
public class DataBase {

    private final PersistenceManager persistenceManager;

    public DataBase() {
        this(null);
    }

    @Inject
    public DataBase(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    public void create() throws Exception {
        EntityManager em = persistenceManager.create();
        DataFileReader reader = new DataFileReader();
//        List<Atendimento> atendimentos = reader.loadAtendimentos("atendimentos.dsv");
        List<Cliente> clientes = reader.loadClientes("clientes.dsv");
        List<Tecnico> tecnicos = reader.loadTecnicos("tecnicos.csv");
        try {
            em.getTransaction().begin();
//            atendimentos.forEach((at) -> {
//                em.persist(at);
//            });
            clientes.forEach((cli) -> {
                em.persist(cli);
            });
            tecnicos.forEach((tec) -> {
                em.persist(tec);
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
        ScoreUpdater updater = new ScoreUpdater(persistenceManager);
        updater.updateAtendimentosSemScore();
    }

}
