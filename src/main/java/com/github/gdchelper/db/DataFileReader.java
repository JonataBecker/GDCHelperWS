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
    
    public static final int LIMITE = 3000;

    public List<Atendimento> loadAtendimentos(String file) throws IOException {
        List<Atendimento> atendimentos = new ArrayList<>();
        int i = 0;
        try (FileReader reader = new FileReader(file)) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(reader);
            for (CSVRecord record : records) {
                if (i == 0) { // Pula cabeçalho
                    i++;
                    continue;
                }
                Atendimento atendimento = new Atendimento();
                atendimento.setCliente(record.get(21));
                atendimento.setTecnico(record.get(4));
                atendimento.setMensagem(record.get(3));
                atendimentos.add(atendimento);
                if (i++ > LIMITE) {
                    break;
                }
            }
        }
        return atendimentos;
    }

}
