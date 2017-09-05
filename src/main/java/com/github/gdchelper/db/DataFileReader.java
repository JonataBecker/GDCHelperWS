package com.github.gdchelper.db;

import com.github.gdchelper.jpa.Atendimento;
import com.github.gdchelper.jpa.Cliente;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author JonataBecker
 */
public class DataFileReader {
    
    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
    
    private final int start;
    private final int end;

    public DataFileReader() {
        this(0, 3000);
    }
    
    public DataFileReader(int start, int end) {
        this.start = start;
        this.end = end;
    }
    
    public List<Cliente> loadClientes(String file) throws IOException {
        List<Cliente> clientes = new ArrayList<>();
        int i = 0;
        try (FileReader reader = new FileReader(System.getProperty("user.home") + "/" + file)) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(reader);
            for (CSVRecord record : records) {
                if (i == 0) {
                    i++;
                    continue;
                }
                if (!record.get(2).equals("C")) { // Carrega apenas clientes
                    continue;
                }
//                if (getInt(record.get(38)) != 1) { // Carrega apenas ativos
//                    continue;
//                }
                Cliente cliente = new Cliente();
                cliente.setCodigo(getInt(record.get(0)));
                cliente.setNome(record.get(1));
                cliente.setNomeFantasia(record.get(3));
                cliente.setCpfCnpj(record.get(6));
                cliente.setTelefonePrincipal(record.get(10));
                cliente.setTelefoneSecundario(record.get(12));
                cliente.setCelular(record.get(16));
                cliente.setEndereco(record.get(17));
                cliente.setBairro(record.get(18));
                cliente.setCidade(record.get(19));
                cliente.setCep(record.get(21));
                cliente.setUf(record.get(22));
//                cliente.setDataCadastro(getDate(record.get(36)));
                cliente.setGdc(getInt(record.get(39)));
                cliente.setConceito(record.get(43));
//                cliente.setVersaoAtual(record.get(67));
//                cliente.setDataAtualizacao(getDate(record.get(66)));
//                cliente.setVersaoLiberada(record.get(65));
                clientes.add(cliente);
            }
        }
        return clientes;
    }
    
    public List<Atendimento> loadAtendimentos(String file) throws IOException {
        return loadAtendimentos(System.getProperty("user.home"), file);
    }
    
    public List<Atendimento> loadAtendimentos(String path, String file) throws IOException {
        List<Atendimento> atendimentos = new ArrayList<>();
        int i = 0;
        try (FileReader reader = new FileReader(path + "/" + file)) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(reader);
            for (CSVRecord record : records) {
                if (i < start + 1) { // Pula cabeçalho e linhas iniciais
                    i++;
                    continue;
                }
                Atendimento atendimento = new Atendimento();
                atendimento.setDataInicio(getDate(record.get(1)));
                atendimento.setDataFim(getDate(record.get(2)));
                atendimento.setTecnico(getInt(record.get(3)));
                atendimento.setCliente(getInt(record.get(4)));
                atendimento.setMensagem(record.get(6));
                atendimento.setDataCriacao(getDate(record.get(14)));
                atendimento.setSegundos((int) (atendimento.getDataFim().getTime() - atendimento.getDataInicio().getTime()) / 1000);
//                atendimento.setSistema(record.get(22));
                atendimentos.add(atendimento);
                if (i++ > end) {
                    break;
                }
            }
        }
        return atendimentos;
    }
    
    private int getInt(String value) {
        if (isEmpty(value)) {
            return 0;
        }
        return Integer.valueOf(value);
    }

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
    
    private boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }
    
}
