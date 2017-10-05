package com.github.gdchelper.jpa;

import com.github.gdchelper.db.DataFileReader;
import com.github.gdchelper.gdchelperws.ScoreUpdater;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            runSql(em);
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

    private void runSql(EntityManager em) throws IOException {
        InputStream file = getClass().getResourceAsStream("/com/github/gdchelper/gdchelperws/models/VIEWS.sql");
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {
            while (true) {
                String l = reader.readLine();
                if (l == null) {
                    break;
                }
                if (l.trim().isEmpty()) {
                    continue;
                }
                sb.append(l).append(' ');
            }
        }
        for (String command : sb.toString().split(";")) {
            if (!command.trim().isEmpty()) {
                em.createNativeQuery(command).executeUpdate();
            }
        }
    }

    public void updateScore() throws Exception {
        scoreUpdater.updateAtendimentosSemScore();
    }

}
