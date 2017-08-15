package com.github.gdchelper.gdchelperws;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.github.gdchelper.jpa.Atendimento;
import com.github.gdchelper.jpa.PersistenceManager;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author JonataBecker
 */
@Controller
public class AtendimentoController {

    private final Result result;
    private final PersistenceManager persistenceManager;

    /**
     * @deprecated CDI eyes only
     */
    public AtendimentoController() {
        this(null, null);
    }

    @Inject
    public AtendimentoController(Result result, PersistenceManager persistenceManager) {
        this.result = result;
        this.persistenceManager = persistenceManager;
    }   

    @Get("/atendimentos")
    public void atendimento() {
        EntityManager em = persistenceManager.create();
        Query q = em.createQuery("SELECT a FROM Atendimento a");
        List<Atendimento> userList = q.getResultList();
        em .close();
        result.use(Results.json()).withoutRoot().from(userList).serialize();
    }
}
