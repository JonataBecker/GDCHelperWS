package com.github.gdchelper.jpa;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe responsável pela criação de objetos responsáveis pela manipiação da base de dados
 */
@ApplicationScoped
public class PersistenceManager {

    private EntityManagerFactory factory;

    /**
     * Cria objeto responsável pela manipiação da base de dados
     * 
     * @return 
     */
    public EntityManager create() {
        if (factory == null) {
            if ("prod".equals(System.getenv("ENV_TYPE"))) {
                factory = Persistence.createEntityManagerFactory("h2");
            } else {
                factory = Persistence.createEntityManagerFactory("mysql");
            }
        }
        return factory.createEntityManager();
    }

    @PreDestroy
    public void end() {
        factory.close();
    }

}
