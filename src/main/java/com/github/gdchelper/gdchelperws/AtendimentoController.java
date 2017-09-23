package com.github.gdchelper.gdchelperws;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.github.gdchelper.jpa.PersistenceManager;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author JonataBecker
 */
@Controller
public class AtendimentoController {

    private final Result result;
    private final PersistenceManager persistenceManager;
    private final HistoricoAntedimentos historicoAntedimentos;

    /**
     * @deprecated CDI eyes only
     */
    public AtendimentoController() {
        this(null, null, null);
    }

    @Inject
    public AtendimentoController(Result result, PersistenceManager persistenceManager, HistoricoAntedimentos historicoAntedimentos) {
        this.result = result;
        this.persistenceManager = persistenceManager;
        this.historicoAntedimentos = historicoAntedimentos;
    }

    @Get("/atendimentos/historico")
    public void atendimentoHistorico(String dataInicial, String dataFinal, String gdc, String cliente) {
        EntityManager em = persistenceManager.create();
        try {
            List list = historicoAntedimentos.getAtendimentos(em, dataInicial, dataFinal, gdc, cliente);
            result.use(Results.json()).withoutRoot().from(list).serialize();
        } finally {
            em.close();
        }
    }
    
    @Get("/atendimentos/historico/contato")
    public void atendimentoHistoricoContato(int idCliente, String dataInicial, String dataFinal) {
        EntityManager em = persistenceManager.create();
        try {
            List list = historicoAntedimentos.getContato(em, idCliente, dataInicial, dataFinal);
            result.use(Results.json()).withoutRoot().from(list).serialize();
        } finally {
            em.close();
        }
    }
    
     @Get("/atendimentos/historico/sistema")
    public void atendimentoHistoricoSistema(int idCliente, String dataInicial, String dataFinal) {
        EntityManager em = persistenceManager.create();
        try {
            List list = historicoAntedimentos.getSitema(em, idCliente, dataInicial, dataFinal);
            result.use(Results.json()).withoutRoot().from(list).serialize();
        } finally {
            em.close();
        }
    }

}
