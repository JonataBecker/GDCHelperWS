package com.github.gdchelper.gdchelperws;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.github.gdchelper.jpa.PersistenceManager;
import com.github.gdchelper.jpa.ViewSistemaContratado;
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
            Query q = em.createQuery("SELECT s.codigoSistema, s.descricao FROM ViewSistemaContratado s GROUP BY s.codigoSistema");
            List<ViewSistemaContratado> userList = q.getResultList();
            result.use(Results.json()).withoutRoot().from(userList).serialize();
        } finally {
            em.close();
        }

    }

    @Get("/sistema")
    public void Sistema(int idCliente, String ordem) {
        EntityManager em = persistenceManager.create();
        ordem = ordem == null ? "" : ordem;
        try {
            String query = "SELECT s FROM ViewSistemaContratado s WHERE s.codigoCliente = :idCliente";
            if (!ordem.isEmpty()) {
                query += " ORDER BY " + ordem;
            }
            Query q = em.createQuery(query);
            q.setParameter("idCliente", idCliente);
            List<ViewSistemaContratado> userList = q.getResultList();
            result.use(Results.json()).withoutRoot().from(userList).serialize();
        } finally {
            em.close();
        }
    }

}
