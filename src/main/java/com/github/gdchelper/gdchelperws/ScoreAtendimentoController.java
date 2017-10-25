package com.github.gdchelper.gdchelperws;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.github.gdchelper.jpa.PersistenceManager;
import com.github.gdchelper.jpa.ViewScoreAtendimento;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import static org.hibernate.engine.profile.Fetch.Style.SELECT;

@Controller
public class ScoreAtendimentoController {
    
    private final Result result;
    private final PersistenceManager persistenceManager;

    /**
     * @deprecated CDI eyes only
     */
    public ScoreAtendimentoController() {
        this(null, null);
    }

    @Inject
    public ScoreAtendimentoController(Result result, PersistenceManager persistenceManager) {
        this.result = result;
        this.persistenceManager = persistenceManager;
    }
    
    @Get("/score")
    public void score(String filter) {
        EntityManager em = persistenceManager.create();
        Query q;
        try {
            String query = "SELECT s FROM ViewScoreAtendimento s";
            if (filter != null && !filter.isEmpty()) {
                query += " WHERE " + filter;
            }
            q = em.createQuery(query);
            List<ViewScoreAtendimento> userList = q.getResultList();
            result.use(Results.json()).withoutRoot().from(userList).serialize();
        } finally {
            em.close();
        }
    }
    
    
//    SELECT DATE_FORMAT(dataCriacao, '%m/%Y'), AVG(score)
//   FROM atendimento atd join scoreatendimento score on atd.id = score.idAtendimento
//      WHERE dataCriacao IS NOT NULL
//   GROUP BY DATE_FORMAT(dataCriacao, '%m/%Y')
//    
}
