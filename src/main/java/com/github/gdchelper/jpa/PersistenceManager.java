package com.github.gdchelper.jpa;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author JonataBecker
 */
@ApplicationScoped
public class PersistenceManager {

    private EntityManagerFactory factory;

    public EntityManager create() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("h2");
        }
        return factory.createEntityManager();
    }

    @PostConstruct
    public void init() {
        EntityManager em = create();
        Atendimento at1 = new Atendimento();
        at1.setCliente("Cliente 1");
        at1.setTecnico("Técnico 1");
        at1.setMensagem("Mensagem de teste");
        Atendimento at2 = new Atendimento();
        at2.setCliente("Cliente 2");
        at2.setTecnico("Técnico 2");
        at2.setMensagem("Outra mensagem de teste");
        em.getTransaction().begin();
        em.persist(at1);
        em.persist(at2);
        em.getTransaction().commit();
        em.close();
    }

    @PreDestroy 
    public void end() {
        factory.close();
    }

}
