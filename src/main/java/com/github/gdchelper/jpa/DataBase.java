package com.github.gdchelper.jpa;

import com.github.gdchelper.db.DataFileReader;
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
        List<Atendimento> atendimentos = reader.loadAtendimentos("exportar.csv");
        try {
            em.getTransaction().begin();
            atendimentos.forEach((at) -> {
                em.persist(at);
            });
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

}
