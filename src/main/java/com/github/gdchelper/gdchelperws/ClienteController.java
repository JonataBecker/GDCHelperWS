package com.github.gdchelper.gdchelperws;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.github.gdchelper.jpa.Cliente;
import com.github.gdchelper.jpa.Contato;
import com.github.gdchelper.jpa.PersistenceManager;
import com.github.gdchelper.jpa.ViewCliente;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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
    
    @Get("/cliente/{idCliente}")
    public void Cliente(int idCliente) {
        EntityManager em = persistenceManager.create();
        result.use(Results.json()).withoutRoot().from(em.find(ViewCliente.class, idCliente)).serialize();
        em.close();
    }
    
    @Get("/cliente")
    public void listaClientes(String gdc, String ordem) {
        String apelidoGdc = "";
        int codigoGdc = 0;
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
    
    @Get("/contatos")
    public void contatosCliente(int idCliente) {
        EntityManager em = persistenceManager.create();
        try {
            Query q = em.createQuery("SELECT c FROM Contato c WHERE c.codigoCliente = :idCliente");
            q.setParameter("idCliente", idCliente);
            List<Contato> userList = q.getResultList();
            result.use(Results.json()).withoutRoot().from(userList).serialize();
        } finally {
            em.close();
        }
    }
    
}
