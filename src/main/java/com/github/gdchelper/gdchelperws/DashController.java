package com.github.gdchelper.gdchelperws;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.github.gdchelper.jpa.Cliente;
import com.github.gdchelper.jpa.PersistenceManager;
import com.github.gdchelper.jpa.ViewAtendimentoPeriodoTecnico;
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

    @Get("/dash/atendimento")
    public void atendimentoQuantidade(String gdc) {
        EntityManager em = persistenceManager.create();
        try {
            if (gdc == null) {
                gdc = "";
            }
            Query q = em.createQuery("SELECT v FROM ViewAtendimentoPeriodoTecnico v WHERE v.tecnico = :gdc ORDER BY v.periodoUSA ASC");
            q.setParameter("gdc", gdc);
            List<ViewAtendimentoPeriodoTecnico> list = q.getResultList();
            result.use(Results.json()).withoutRoot().from(list).serialize();
        } finally {
            em.close();
        }
    }


}
