package com.github.gdchelper.gdchelperws;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.github.gdchelper.jpa.Atualizacao;
import com.github.gdchelper.jpa.PersistenceManager;
import com.github.gdchelper.jpa.ViewCliente;
import com.github.gdchelper.jpa.ViewClienteAtendimento;
import com.github.gdchelper.jpa.ViewContato;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Controller responsável por informações do cliente
 */
@Controller
public class ClienteController {

    private final Result result;
    private final PersistenceManager persistenceManager;

    /**
     * @deprecated CDI eyes only
     */
    public ClienteController() {
        this(null, null);
    }

    @Inject
    public ClienteController(Result result, PersistenceManager persistenceManager) {
        this.result = result;
        this.persistenceManager = persistenceManager;
    }

    /**
     * Retorna os dados de um determinado cliente
     * 
     * @param idCliente 
     */
    @Get("/cliente/{idCliente}")
    public void Cliente(int idCliente) {
        EntityManager em = persistenceManager.create();
        result.use(Results.json()).withoutRoot().from(em.find(ViewCliente.class, idCliente)).serialize();
        em.close();
    }

    /**
     * Retorna a lista de clientes de um determinado gerente de contas
     * 
     * @param gdc
     * @param ordem 
     */
    @Get("/cliente")
    public void listaClientes(String gdc, String ordem) {
        String apelidoGdc = "";
        int codigoGdc = 0;
        // Verifica se deve filtrar o GDC por código ou apelido
        if (gdc != null) {
            try {
                codigoGdc = Integer.parseInt(gdc);
            } catch (NumberFormatException e) {
                codigoGdc = 0;
                apelidoGdc = gdc;
            }
        }
        ordem = ordem == null ? "" : ordem;
        EntityManager em = persistenceManager.create();
        Query q;
        try {
            String query;
            Object gdcFilter;
            if (!apelidoGdc.isEmpty()) {
                query = "SELECT c, t FROM ViewCliente c INNER JOIN c.gdc t WHERE t.apelido = :gdc";
                gdcFilter = apelidoGdc;
            } else {
                query = "SELECT c, t FROM ViewCliente c INNER JOIN c.gdc t WHERE t.codigo = :gdc";
                gdcFilter = codigoGdc;
            }
            if (!ordem.isEmpty()) {
                query += " ORDER BY " + ordem;
            }
            q = em.createQuery(query);
            q.setParameter("gdc", gdcFilter);
            List<ViewCliente> userList = q.getResultList();
            result.use(Results.json()).withoutRoot().from(userList).serialize();
        } finally {
            em.close();
        }
    }

    /**
     * Retorna a lista de contatos de um determinado cliente
     * 
     * @param idCliente
     * @param ordem 
     */
    @Get("/contatos")
    public void contatosCliente(int idCliente, String ordem) {
        EntityManager em = persistenceManager.create();
        ordem = ordem == null ? "" : ordem;
        try {
            String query = "SELECT c FROM ViewContato c WHERE c.codigoCliente = :idCliente";
            if (!ordem.isEmpty()) {
                query += " ORDER BY " + ordem;
            }
            Query q = em.createQuery(query);
            q.setParameter("idCliente", idCliente);
            List<ViewContato> userList = q.getResultList();
            result.use(Results.json()).withoutRoot().from(userList).serialize();
        } finally {
            em.close();
        }
    }

    /**
     * Retorna atendimentos de um determinado cliente
     *
     * @param idCliente
     */
    @Get("/cliente/{idCliente}/atendimento")
    public void clienteAtendimento(int idCliente) {
        EntityManager em = persistenceManager.create();
        try {
            Query q = em.createQuery("SELECT c FROM ViewClienteAtendimento c WHERE c.cliente = :idCliente ORDER BY c.data DESC");
            q.setParameter("idCliente", idCliente);
            List<ViewClienteAtendimento> userList = q.getResultList();
            result.use(Results.json()).withoutRoot().from(userList).serialize();
        } finally {
            em.close();
        }
    }

    /**
     * Retorna atendimentos de um determinado contato
     *
     * @param idCliente
     * @param codigoContato
     */
    @Get("/cliente/{idCliente}/atendimento/{codigoContato}")
    public void clienteAtendimentoContato(int idCliente, int codigoContato) {
        EntityManager em = persistenceManager.create();
        try {
            Query q = em.createQuery("SELECT c FROM ViewClienteAtendimento c WHERE c.cliente = :idCliente AND c.codigoContato = :contato  ORDER BY c.data DESC");
            q.setParameter("idCliente", idCliente);
            q.setParameter("contato", codigoContato);
            List<ViewClienteAtendimento> userList = q.getResultList();
            result.use(Results.json()).withoutRoot().from(userList).serialize();
        } finally {
            em.close();
        }
    }

    /**
     * Retorna a data do último atendimento do cliente
     *
     * @param idCliente
     */
    @Get("/cliente/{idCliente}/atendimento/ultimo")
    public void clienteUltimoAtendimento(int idCliente) {
        EntityManager em = persistenceManager.create();
        try {
            String sql = "SELECT MAX(data) FROM ViewClienteAtendimento WHERE cliente = :cliente";
            Query q = em.createQuery(sql);
            q.setParameter("cliente", idCliente);
            Map ret = new HashMap();
            ret.put("data", (Timestamp) q.getSingleResult());
            result.use(Results.json()).withoutRoot().from(ret).serialize();
        } finally {
            em.close();
        }
    }

    /**
     * Retorna as atualizações de um determinado cliente
     * 
     * @param idCliente 
     */
    @Get("/cliente/{idCliente}/atualizacoes")
    public void clienteAtualizacoes(int idCliente) {
        EntityManager em = persistenceManager.create();
        try {
            String sql = "SELECT a FROM Atualizacao a WHERE codigoCliente = :cliente order by data desc";
            Query q = em.createQuery(sql);
            q.setParameter("cliente", idCliente);
            List<Atualizacao> userList = q.getResultList();
            result.use(Results.json()).withoutRoot().from(userList).serialize();
        } finally {
            em.close();
        }
    }
    
    /**
     * Retorna as atualizações de troca de versão de um determinado cliente
     * 
     * @param idCliente 
     */
    @Get("/cliente/{idCliente}/trocasVersao")
    public void clienteAtualizacoesTrocaVersao(int idCliente) {
        EntityManager em = persistenceManager.create();
        try {
            String sql = "SELECT versao, MIN(data) FROM Atualizacao WHERE codigoCliente = :cliente group by versao order by data desc";
            Query q = em.createQuery(sql);
            q.setParameter("cliente", idCliente);
            q.setMaxResults(5);
            List<Atualizacao> userList = q.getResultList();
            result.use(Results.json()).withoutRoot().from(userList).serialize();
        } finally {
            em.close();
        }
    }
}
