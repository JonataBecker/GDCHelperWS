package com.github.gdchelper.gdchelperws;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.github.gdchelper.jpa.Atendimento;
import com.github.gdchelper.jpa.PersistenceManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
        try {
            Query q = em.createQuery("SELECT a FROM Atendimento a");
            List<Atendimento> userList = q.getResultList();
            result.use(Results.json()).withoutRoot().from(userList).serialize();
        } finally {
            em.close();
        }
    }

    @Get("/atendimentos/historico")
    public void atendimentoHistorico() {
        EntityManager em = persistenceManager.create();
        try {
            List list = new ArrayList();
            Query q = em.createNativeQuery("SELECT cliente, COUNT(*) AS quantidade, FORMAT(SUM(Segundos / 60 / 60), 2) AS tempo FROM Atendimento GROUP BY Cliente ORDER BY Quantidade DESC, Tempo DESC");
            q.getResultList().forEach((obj) -> {
                Object[] item = (Object[]) obj;
                Map resultMap = new LinkedHashMap();
                resultMap.put("cliente", item[0]);
                resultMap.put("quantidade", item[1]);
                resultMap.put("tempo", item[2]);
                list.add(resultMap);
            });
            result.use(Results.json()).withoutRoot().from(list).serialize();
        } finally {
            em.close();
        }
    }

}
