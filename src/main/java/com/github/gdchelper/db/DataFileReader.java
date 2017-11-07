package com.github.gdchelper.db;

import com.github.gdchelper.jpa.Atendimento;
import com.github.gdchelper.jpa.Atualizacao;
import com.github.gdchelper.jpa.Cliente;
import com.github.gdchelper.jpa.Contato;
import com.github.gdchelper.jpa.SistemaContratado;
import com.github.gdchelper.jpa.Tecnico;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Classe responsável pela leitura dos arquivos da base de dados
 */
public class DataFileReader {

    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
    private final EntityManager entityManager;
    private final int start;
    private final int end;

    public DataFileReader() {
        this(null);
    }

    public DataFileReader(EntityManager entityManager) {
        this(623813, 626813, entityManager);
    }

    public DataFileReader(int start, int end) {
        this(start, end, null);
    }

    public DataFileReader(int start, int end, EntityManager entityManager) {
        this.start = start;
        this.end = end;
        this.entityManager = entityManager;
    }

    /**
     * Executa o carregamento de clientes
     *
     * @param file
     * @return {@code List<Cliente>}
     * @throws IOException
     */
    public List<Cliente> loadClientes(String file) throws IOException {
        List<Cliente> clientes = new ArrayList<>();
        int i = 0;
        try (FileReader reader = new FileReader(System.getProperty("user.home") + "/" + file)) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').parse(reader);
            for (CSVRecord record : records) {
                if (i == 0) { // Pula o cabeçalho
                    i++;
                    continue;
                }
                if (!record.get(2).equals("C")) { // Carrega apenas clientes
                    continue;
                }
                if (getInt(record.get(38)) != 1) { // Carrega apenas ativos
                    continue;
                }
                if (getInt(record.get(39)) == 0) { // Carrega apenas com GDC
                    continue;
                }
                Cliente cliente = new Cliente();
                cliente.setCodigo(getInt(record.get(0)));
                cliente.setNome(stringSuffle(record.get(1)));
                cliente.setNomeFantasia(stringSuffle(record.get(3)));
                cliente.setCpfCnpj(record.get(6));
                cliente.setTelefonePrincipal(record.get(10));
                cliente.setTelefoneSecundario(record.get(12));
                cliente.setCelular(record.get(16));
                cliente.setEndereco(record.get(17));
                cliente.setBairro(record.get(18));
                cliente.setCidade(record.get(19));
                cliente.setCep(record.get(21));
                cliente.setUf(record.get(22));
                cliente.setDataCadastro(getDate(record.get(36)));
                Tecnico tec = null;
                try {
                    tec = entityManager.find(Tecnico.class, getInt(record.get(39)));
                } catch (Exception e) {
                    tec = null;
                }
                cliente.setGdc(tec);
                cliente.setConceito(record.get(43));
                cliente.setVersaoAtual(record.get(67));
                cliente.setDataAtualizacao(getDate(record.get(66)));
                cliente.setVersaoLiberada(record.get(65));
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    /**
     * Executa carregamento de contatos
     *
     * @param file
     * @return {@code List<Contato>}
     * @throws IOException
     */
    public List<Contato> loadContatos(String file) throws IOException {
        List<Contato> contatos = new ArrayList<>();
        int i = 0;
        try (FileReader reader = new FileReader(System.getProperty("user.home") + "/" + file)) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').parse(reader);
            for (CSVRecord record : records) {
                if (i == 0) { // Pula o cabeçalho
                    i++;
                    continue;
                }
                if (getInt(record.get(24)) != 1) { // Carrega apenas ativos
                    continue;
                }
                Contato contato = new Contato();
                contato.setCodigoCliente(getInt(record.get(0)));
                contato.setCodigo(getInt(record.get(1)));
                contato.setNome(stringSuffle(record.get(2)));
                contato.setCargo(record.get(9));
                contato.setEmail(record.get(11));
                contato.setTelefone(record.get(20));
                contatos.add(contato);
            }
        }
        return contatos;
    }

    /**
     * Executa carregamento de técnicos
     *
     * @param file
     * @return {@code List<Tecnico>}
     * @throws IOException
     */
    public List<Tecnico> loadTecnicos(String file) throws IOException {
        List<Tecnico> tecnicos = new ArrayList<>();
        int i = 0;
        try (FileReader reader = new FileReader(System.getProperty("user.home") + "/" + file)) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(reader);
            for (CSVRecord record : records) {
                if (i == 0) { // Pula o cabeçalho
                    i++;
                    continue;
                }
                if (getInt(record.get(8)) != 1) { // Carrega apenas ativos
                    continue;
                }
                Tecnico tecnico = new Tecnico();
                tecnico.setCodigo(getInt(record.get(0)));
                tecnico.setApelido(record.get(1));
                tecnico.setNome(record.get(4));
                tecnico.setSetor(getInt(record.get(6)));
                tecnico.setFoto(record.get(97).replace("F:\\RECH\\Usr\\FOTOS\\", ""));
                tecnicos.add(tecnico);
            }
        }
        return tecnicos;
    }

    /**
     * Executa carregamento de sistemas contratados
     *
     * @param file
     * @return {@code List<SistemaContratado>}
     * @throws IOException
     */
    public List<SistemaContratado> loadSistemasContratados(String file) throws IOException {
        List<SistemaContratado> sistemas = new ArrayList<>();
        int i = 0;
        try (FileReader reader = new FileReader(System.getProperty("user.home") + "/" + file)) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(reader);
            for (CSVRecord record : records) {
                if (i == 0) { // Pula o cabeçalho
                    i++;
                    continue;
                }
                SistemaContratado sistema = new SistemaContratado();
                sistema.setCodigoCliente(getInt(record.get(0)));
                sistema.setCodigoSistema(getInt(record.get(1)));
                sistema.setDescricao(record.get(2));
                sistemas.add(sistema);
            }
        }
        return sistemas;
    }

    /**
     * Executa carregamento de informações referente a atualizações de clientes
     *
     * @param file
     * @return {@code List<Atualizacao>}
     * @throws IOException
     */
    public List<Atualizacao> loadAtualizacoes(String file) throws IOException {
        List<Atualizacao> atualizacoes = new ArrayList<>();
        int i = 0;
        try (FileReader reader = new FileReader(System.getProperty("user.home") + "/" + file)) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').parse(reader);
            for (CSVRecord record : records) {
                if (i == 0) { // Pula o cabeçalho
                    i++;
                    continue;
                }
                Atualizacao atualizacao = new Atualizacao();
                atualizacao.setCodigoCliente(getInt(record.get(0)));
                atualizacao.setData(getDate(record.get(1)));
                atualizacao.setVersao(record.get(10));
                atualizacoes.add(atualizacao);
            }
        }
        return atualizacoes;
    }

    /**
     * Executa carregamento de atendimentos
     * 
     * @param file
     * @return {@code List<Atendimento>}
     * @throws IOException 
     */
    public List<Atendimento> loadAtendimentos(String file) throws IOException {
        return loadAtendimentos(System.getProperty("user.home"), file);
    }

    /**
     * Executa carregamento de atendimentos
     * 
     * @param path
     * @param file
     * @return {@code List<Atendimento>}
     * @throws IOException 
     */
    public List<Atendimento> loadAtendimentos(String path, String file) throws IOException {
        List<Atendimento> atendimentos = new ArrayList<>();
        int i = 0;
        try (FileReader reader = new FileReader(path + "/" + file)) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').parse(reader);
            for (CSVRecord record : records) {
                if (i < start + 1) { // Pula cabeçalho e linhas iniciais
                    i++;
                    continue;
                }
                Atendimento atendimento = new Atendimento();
                atendimento.setDataInicio(getDate(record.get(23)));
                atendimento.setDataFim(getDate(record.get(27)));
                atendimento.setTecnico(getInt(record.get(4)));
                atendimento.setContato(getInt(record.get(20)));
                atendimento.setCliente(getInt(record.get(21)));
                String s = record.get(3);
                atendimento.setMensagem(s.length() > 20000 ? s.substring(0, 20000) : s);
                atendimento.setDataCriacao(getDate(record.get(34)));
                atendimento.setSegundos((int) (atendimento.getDataFim().getTime() - atendimento.getDataInicio().getTime()) / 1000);
                atendimento.setSistema(record.get(22));
                atendimentos.add(atendimento);
                if (i++ > end) {
                    break;
                }
            }
        }
        return atendimentos;
    }
    
    /**
     * Converte literal em valor inteiro
     * 
     * @param value
     * @return int
     */
    private int getInt(String value) {
        if (isEmpty(value)) {
            return 0;
        }
        return Integer.valueOf(value);
    }

    /**
     * Converte literal em data
     * 
     * @param value
     * @return 
     */
    private Date getDate(String value) {
        try {
            if (!isEmpty(value)) {
                return FORMAT_DATE.parse(value);
            }
        } catch (ParseException ex) {
            Logger.getLogger(DataFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Retorna verdadeiro se coluna não possui valor
     * 
     * @param value
     * @return boolean
     */
    private boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    private String stringSuffle(String value) {
        List bytes = Arrays.asList(ArrayUtils.toObject(value.getBytes()));
        Collections.shuffle(bytes);
        return new String(ArrayUtils.toPrimitive((Byte[]) bytes.toArray(new Byte[]{})));
    }

}
