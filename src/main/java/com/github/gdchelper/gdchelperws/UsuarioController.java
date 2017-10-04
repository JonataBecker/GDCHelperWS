package com.github.gdchelper.gdchelperws;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.github.gdchelper.jpa.PersistenceManager;
import com.github.gdchelper.jpa.Tecnico;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author JonataBecker
 */
@Controller
public class UsuarioController {

    private final Result result;
    private final PersistenceManager persistenceManager;

    /**
     * @deprecated CDI eyes only
     */
    public UsuarioController() {
        this(null, null);
    }

    @Inject
    public UsuarioController(Result result, PersistenceManager persistenceManager) {
        this.result = result;
        this.persistenceManager = persistenceManager;
    }
  
    @Get("/usuarios")
    public void usuarios() {
        EntityManager em = persistenceManager.create();
        try {
            Query q = em.createQuery("SELECT u FROM Tecnico u WHERE u.setor = :setor");
            q.setParameter("setor", Tecnico.SETOR_GDC);
            result.use(Results.json()).withoutRoot().from(q.getResultList()).serialize();
        } finally {
            em.close();
        }
    }
    
}
