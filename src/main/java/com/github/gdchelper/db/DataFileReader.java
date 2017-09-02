package com.github.gdchelper.db;

import com.github.gdchelper.jpa.Atendimento;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author JonataBecker
 */
public class DataFileReader {
    
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
                atendimento.setCliente(record.get(21));
                atendimento.setTecnico(record.get(4));
                atendimento.setMensagem(record.get(3));
                atendimentos.add(atendimento);
                if (i++ > end) {
                    break;
                }
            }
        }
        return atendimentos;
    }

}
