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
    public void atendimentoHistorico(String dataInicial, String dataFinal, String gdc, String cliente) {
        EntityManager em = persistenceManager.create();
        try {
            String sql = "SELECT \n"
                    + "	CONCAT(Atendimento.cliente, '-', Cliente.nome) AS Cliente, \n"
                    + "	COUNT(*) AS quantidade, \n"
                    + "	FORMAT(SUM(Segundos / 60 / 60), 2) AS tempo, \n"
                    + " MAX(Tecnico.apelido),"
                    + " Atendimento.cliente as Codigo_Cliente "
                    + "FROM Atendimento \n"
                    + "INNER JOIN Cliente\n"
                    + "	ON (Atendimento.cliente = Cliente.codigo) \n"
                    + "INNER JOIN Tecnico\n"
                    + "	ON (Cliente.gdc = Tecnico.codigo)\n"
                    + "WHERE \n"
                    + "	Atendimento.dataInicio >= :dataInicio AND\n"
                    + "	Atendimento.dataFim <= :dataFim AND\n"
                    + "	Tecnico.apelido LIKE :gdc AND \n"
                    + "	Cliente.nome LIKE :cliente \n"
                    + "GROUP BY Atendimento.Cliente \n"
                    + "ORDER BY Quantidade DESC, Tempo DESC";
            List list = new ArrayList();
            if (gdc == null) {
                gdc = "";
            }
            if (cliente == null) {
                cliente = "";
            }
            Query q = em.createNativeQuery(sql);
            q.setParameter("dataInicio", dataInicial);
            q.setParameter("dataFim", dataFinal);
            q.setParameter("gdc", "%" + gdc + "%");
            q.setParameter("cliente", "%" + cliente + "%");
            q.getResultList().forEach((obj) -> {
                Object[] item = (Object[]) obj;
                Map resultMap = new LinkedHashMap();
                resultMap.put("cliente", item[0]);
                resultMap.put("quantidade", item[1]);
                resultMap.put("tempo", item[2]);
                resultMap.put("gdc", item[3]);
                resultMap.put("idCliente", item[4]);
                list.add(resultMap);
            });
            result.use(Results.json()).withoutRoot().from(list).serialize();
        } finally {
            em.close();
        }
    }
    
    @Get("/atendimentos/historico/contato")
    public void atendimentoHistoricoContato(int idCliente, String dataInicial, String dataFinal) {
        EntityManager em = persistenceManager.create();
        try {
            String sql = "SELECT \n" +
                        "  COUNT(*) AS quantidade, \n" +
                        "  FORMAT(SUM(Segundos / 60 / 60), 2) AS tempo, \n" +
                        "  IFNULL(Contato.nome, 'Não definido')\n" +
                        "FROM Atendimento\n" +
                        "  LEFT JOIN Contato\n" +
                        "    ON (Contato.id = Atendimento.contato)\n" +
                        "WHERE \n" + 
                        "  Atendimento.dataInicio >= :dataInicio AND\n" +
                        "  Atendimento.dataFim <= :dataFim AND\n" + 
                        "  Atendimento.cliente = :cliente \n" + 
                        "GROUP BY Atendimento.contato\n" +
                        "ORDER BY Quantidade DESC, Tempo DESC";
            List list = new ArrayList();
            Query q = em.createNativeQuery(sql);
            q.setParameter("dataInicio", dataInicial);
            q.setParameter("dataFim", dataFinal);
            q.setParameter("cliente", idCliente);
            q.getResultList().forEach((obj) -> {
                Object[] item = (Object[]) obj;
                Map resultMap = new LinkedHashMap();
                resultMap.put("quantidade", item[0]);
                resultMap.put("tempo", item[1]);
                resultMap.put("contato", item[2]);
                list.add(resultMap);
            });
            result.use(Results.json()).withoutRoot().from(list).serialize();
        } finally {
            em.close();
        }
    }
    
     @Get("/atendimentos/historico/sistema")
    public void atendimentoHistoricoSistema(int idCliente, String dataInicial, String dataFinal) {
        EntityManager em = persistenceManager.create();
        try {
            String sql = "SELECT \n" +
                        "  COUNT(*) AS quantidade, \n" +
                        "  FORMAT(SUM(Segundos / 60 / 60), 2) AS tempo, \n" +
                        "  IFNULL(SistemaContratado.descricao, 'Não definido')\n" +
                        "FROM Atendimento\n" +
                        "  LEFT JOIN SistemaContratado \n" +
                        "    ON (SistemaContratado.codigoSistema = Atendimento.sistema)\n" +
                        "WHERE \n" + 
                        "  Atendimento.dataInicio >= :dataInicio AND\n" +
                        "  Atendimento.dataFim <= :dataFim AND\n" + 
                        "  Atendimento.cliente = :cliente \n" + 
                        "GROUP BY SistemaContratado.descricao \n" +
                        "ORDER BY Quantidade DESC, Tempo DESC";
            List list = new ArrayList();
            Query q = em.createNativeQuery(sql);
            q.setParameter("dataInicio", dataInicial);
            q.setParameter("dataFim", dataFinal);
            q.setParameter("cliente", idCliente);
            q.getResultList().forEach((obj) -> {
                Object[] item = (Object[]) obj;
                Map resultMap = new LinkedHashMap();
                resultMap.put("quantidade", item[0]);
                resultMap.put("tempo", item[1]);
                resultMap.put("sistema", item[2]);
                list.add(resultMap);
            });
            result.use(Results.json()).withoutRoot().from(list).serialize();
        } finally {
            em.close();
        }
    }

}
