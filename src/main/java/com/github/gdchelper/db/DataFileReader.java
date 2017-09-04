package com.github.gdchelper.db;

import com.github.gdchelper.jpa.Atendimento;
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
                atendimento.setCliente(getInt(record.get(21)));
                atendimento.setTecnico(getInt(record.get(4)));
                atendimento.setMensagem(record.get(3));
                atendimento.setData(getDate(record.get(34)));
                atendimento.setDataInicio(getDate(record.get(23)));
                atendimento.setDataFim(getDate(record.get(28)));
                atendimento.setSegundos(getInt(record.get(39)));
                atendimento.setSistema(record.get(22));
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
