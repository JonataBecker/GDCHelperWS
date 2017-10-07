package com.github.gdchelper.gdchelperws;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.github.gdchelper.jpa.Cliente;
import com.github.gdchelper.jpa.PersistenceManager;
import com.github.gdchelper.jpa.ViewAtendimentoPeriodoQuantidade;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author JonataBecker
 */
@Controller
public class DashController {

    private final Result result;
    private final PersistenceManager persistenceManager;

    /**
     * @deprecated CDI eyes only
     */
    public DashController() {
        this(null, null);
    }

    @Inject
    public DashController(Result result, PersistenceManager persistenceManager) {
        this.result = result;
        this.persistenceManager = persistenceManager;
    }

    @Get("/dash/atendimento/quantidade")
    public void atendimentoQuantidade() {
        EntityManager em = persistenceManager.create();
        try {
            Query q = em.createQuery("SELECT v FROM ViewAtendimentoPeriodoQuantidade v ORDER BY v.periodoUSA ASC");
            List<ViewAtendimentoPeriodoQuantidade> list = q.getResultList();
            result.use(Results.json()).withoutRoot().from(list).serialize();
        } finally {
            em.close();
        }
    }

    @Get("/dash/atendimento/tempo")
    public void atendimentoTempo() {
        EntityManager em = persistenceManager.create();
        try {
            Query q = em.createQuery("SELECT v FROM ViewAtendimentoPeriodoTempo v ORDER BY v.periodoUSA ASC");
            List<ViewAtendimentoPeriodoQuantidade> list = q.getResultList();
            result.use(Results.json()).withoutRoot().from(list).serialize();
        } finally {
            em.close();
        }
    }

}
