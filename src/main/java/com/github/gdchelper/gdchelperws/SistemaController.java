package com.github.gdchelper.gdchelperws;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.github.gdchelper.jpa.PersistenceManager;
import com.github.gdchelper.jpa.SistemaContratado;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Controller
public class SistemaController {

    private final Result result;
    private final PersistenceManager persistenceManager;

    /**
     * @deprecated CDI eyes only
     */
    public SistemaController() {
        this(null, null);
    }

    @Inject
    public SistemaController(Result result, PersistenceManager persistenceManager) {
        this.result = result;
        this.persistenceManager = persistenceManager;
    }

    @Get("/sistemas")
    public void listaSistemasDisponiveis() {
        EntityManager em = persistenceManager.create();
        try {
            Query q = em.createQuery("SELECT s.codigoSistema, s.descricao FROM SistemaContratado s GROUP BY s.codigoSistema");
            List<SistemaContratado> userList = q.getResultList();
            result.use(Results.json()).withoutRoot().from(userList).serialize();
        } finally {
            em.close();
        }

    }

    @Get("/sistema")
    public void Sistema(int idCliente) {
        EntityManager em = persistenceManager.create();
        try {
            Query q = em.createQuery("SELECT s FROM SistemaContratado s WHERE s.codigoCliente = :idCliente");
            q.setParameter("idCliente", idCliente);
            List<SistemaContratado> userList = q.getResultList();
            result.use(Results.json()).withoutRoot().from(userList).serialize();
        } finally {
            em.close();
        }
    }

}
