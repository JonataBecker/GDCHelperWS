package com.github.gdchelper.gdchelperws;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Classe responsável por retornar informações de histótico de atendimentos
 */
public class HistoricoAntedimentos {

    /**
     * Retorna o histórico de atendimentos
     * 
     * @param em
     * @param dataInicial
     * @param dataFinal
     * @param gdc
     * @param cliente
     * @return List
     */
    public List getAtendimentos(EntityManager em, String dataInicial, String dataFinal, String gdc, String cliente) {
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
        return list;
    }
    
    /**
     * Retorna o histórico de atendimentos por contato
     * 
     * @param em
     * @param idCliente
     * @param dataInicial
     * @param dataFinal
     * @return List
     */
    public List getContato(EntityManager em, int idCliente, String dataInicial, String dataFinal) {
        String sql = "SELECT \n"
                + "  COUNT(*) AS quantidade, \n"
                + "  FORMAT(SUM(Segundos / 60 / 60), 2) AS tempo, \n"
                + "  IFNULL(Contato.nome, 'Não definido')\n"
                + "FROM Atendimento\n"
                + "  LEFT JOIN Contato\n"
                + "    ON (Contato.codigo = Atendimento.contato AND "
                + "        Atendimento.cliente = Contato.codigoCliente) \n"
                + "WHERE \n"
                + "  Atendimento.dataInicio >= :dataInicio AND\n"
                + "  Atendimento.dataFim <= :dataFim AND\n"
                + "  Atendimento.cliente = :cliente \n"
                + "GROUP BY Contato.nome \n"
                + "ORDER BY Quantidade DESC, Tempo DESC";
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
        return list;
    }
    
    /**
     * Retorna o histórico de atendimentos por sistema
     * 
     * @param em
     * @param idCliente
     * @param dataInicial
     * @param dataFinal
     * @return List
     */
    public List getSitema(EntityManager em, int idCliente, String dataInicial, String dataFinal) {
        String sql = "SELECT \n"
                + "  COUNT(*) AS quantidade, \n"
                + "  FORMAT(SUM(Segundos / 60 / 60), 2) AS tempo, \n"
                + "  IFNULL(SistemaContratado.descricao, 'Não definido')\n"
                + "FROM Atendimento\n"
                + "  LEFT JOIN SistemaContratado \n"
                + "    ON (SistemaContratado.codigoSistema = Atendimento.sistema AND "
                + "        SistemaContratado.codigoCliente = Atendimento.cliente) \n"
                + "WHERE \n"
                + "  Atendimento.dataInicio >= :dataInicio AND\n"
                + "  Atendimento.dataFim <= :dataFim AND\n"
                + "  Atendimento.cliente = :cliente \n"
                + "GROUP BY SistemaContratado.descricao \n"
                + "ORDER BY Quantidade DESC, Tempo DESC";
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
        return list;
    }
}
