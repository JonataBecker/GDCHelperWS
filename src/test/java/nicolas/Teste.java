package nicolas;

import com.github.gdchelper.gdchelperws.SentencePreprocessor;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Teste {

    static final String DADOS = "c:\\users\\pichau\\desktop\\exportar.csv";
    static final String SENTIMENT = "D:\\Projects\\GDCHelper\\GDCHelperWS\\src\\main\\java\\com\\github\\gdchelper\\gdchelperws\\models\\sentiment.bin";
    static final String SENTENCER = "D:\\Projects\\GDCHelper\\GDCHelperWS\\src\\main\\java\\com\\github\\gdchelper\\gdchelperws\\models\\pt-sent.bin";
    static final int LIMITE = 3000;
    static final int TAMANHO_MINIMO = 5;
    static final double TREINAMENTO = 0.8;

    public static void main(String[] args) throws Exception {
        Teste t = new Teste();
        List<Atendimento> atendimentos = t.loadAtendimentos();
        List<FraseTreinamento> classificados = t.loadTreinamentos();
        
        // TESTE: USA SÓ BOM E RUIM
        classificados = classificados.stream().map((frase) -> new FraseTreinamento(frase.getCategoria().replaceFirst("^m", ""), frase.getFrase())).collect(Collectors.toList());
        // TESTE: IGNORA NEUTRO
        classificados = classificados.stream().filter((frase) -> !frase.getCategoria().equals("neutro")).collect(Collectors.toList());
        
        int trainingIterations = (int) (classificados.size() * TREINAMENTO);
        List<FraseTreinamento> treinamento = classificados.subList(0, trainingIterations);
        List<FraseTreinamento> teste = classificados.subList(trainingIterations, classificados.size());
        
        System.out.println(treinamento.size() + " registros de treinamento");
        System.out.println(teste.size() + " registros de teste");
        
        int cutoff = 2;
        ObjectStream lineStream = new PlainTextByLineStream(t.getStreamFrom(treinamento), "UTF-8");
        ObjectStream sampleStream = new DocumentSampleStream(lineStream);
        DoccatModel model = DocumentCategorizerME.train("pt", sampleStream, cutoff, trainingIterations);
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);

        Map<String, Integer> totais = new HashMap<>();
        
        for (Atendimento atendimento : atendimentos) {
            List<String> sentences = t.extractSentences(atendimento.getTexto());
//            System.out.println("----------------------");
            for (int i = 0; i < sentences.size(); i++) {
                String sentence = sentences.get(i);

                double[] outcomes = myCategorizer.categorize(sentence);
                String category = myCategorizer.getBestCategory(outcomes);
                int index = myCategorizer.getIndex(category);
                double prob = outcomes[index];

                if (!totais.containsKey(category)) {
                    totais.put(category, 0);
                }

                if (prob < 0.75) {
                    continue;
                }

                totais.put(category, totais.get(category) + 1);
                
                if (category.equals("0") || category.equals("1") || category.equals("neutro")) {
                    continue;
                }
                
                if (prob < 0.90) {
                    continue;
                }

                System.out.println(i +"\t"+category +" (" + new DecimalFormat("#00.00").format(prob * 100) + "%)\t"+sentence);
            }
        }
        
        for (Map.Entry<String, Integer> entry : totais.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }

        t.testa(teste, myCategorizer);
        
    }

    private void testa(List<FraseTreinamento> teste, DocumentCategorizerME myCategorizer) {
        int certos = 0;
        int errados = 0;
        for (FraseTreinamento fraseTreinamento : teste) {
            double[] outcomes = myCategorizer.categorize(fraseTreinamento.getFrase());
            String category = myCategorizer.getBestCategory(outcomes);
            if (category.equals(fraseTreinamento.getCategoria())) {
                certos++;
            } else {
                errados++;
            }
        }
        System.out.println("Acertei " + certos + " (" + ((double)certos / (double)(certos + errados) * 100) + "%)");
        System.out.println("Errei " + errados + " (" + ((double)errados / (double)(certos + errados) * 100) + "%)");
    }
    
    private List<String> extractSentences(String text) throws IOException {
        List<String> list = new ArrayList<>();
        // always start with a model, a model is learned from training data
//      InputStream is = Teste.class.getResourceAsStream("/com/github/gdchelper/gdchelperws/models/pt-sent.bin");
        InputStream is = new FileInputStream(SENTENCER);
        SentenceModel model = new SentenceModel(is);
        SentenceDetectorME sdetector = new SentenceDetectorME(model);

        String[] lines = text.split("\n");
        for (String line : lines) {
            line = new SentencePreprocessor().process(line);
            String[] sentences = sdetector.sentDetect(line);
            for (String sentence : sentences) {
                if (filterSentence(sentence)) {
                    break;
                }
                list.add(sentence);
            }
        }

//        String sentences[] = sdetector.sentDetect();
//        System.out.println("----------------------");
//        for (int i = 0; i < sentences.length; i++) {
//            System.out.println(i +"\t"+sentences[i].replace("\n", "\\n"));
//        }
//        is.close();
        return list;
    }

    private boolean filterSentence(String sentence) {
        if (sentence.length() < TAMANHO_MINIMO) {
            return true;
        }
        if (sentence.contains("_Rech")) {
            return true;
        }
        if (sentence.startsWith("To:") || sentence.startsWith("From:") || sentence.startsWith("Subject:")) {
            return true;
        }
        return false;
    }

    private List<Atendimento> loadAtendimentos() throws IOException {
        List<Atendimento> atendimentos = new ArrayList<>();
        int i = 0;
        try (FileReader reader = new FileReader(DADOS)) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(reader);
            for (CSVRecord record : records) {
                if (i == 0) { // Pula cabeçalho
                    i++;
                    continue;
                }
                Atendimento atendimento = new Atendimento();
                atendimento.setCliente(Integer.parseInt(record.get(21)));
                atendimento.setTexto(record.get(3));
                atendimentos.add(atendimento);
                if (i++ > LIMITE) {
                    break;
                }
            }
        }
        return atendimentos;
    }

    private List<FraseTreinamento> loadTreinamentos() throws IOException {
        List<FraseTreinamento> treinamentos = new ArrayList<>();
        int i = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(SENTIMENT))) {
            while (true) {
                String l = reader.readLine();
                if (l == null) {
                    break;
                }
                String[] parts = l.split("\\s+", 2);
                treinamentos.add(new FraseTreinamento(parts[0], new SentencePreprocessor().process(parts[1])));
            }
        }
        return treinamentos;
    }
    
    private InputStream getStreamFrom(List<FraseTreinamento> treinamento) {
        StringBuilder buffer = new StringBuilder();
        for (FraseTreinamento fraseTreinamento : treinamento) {
            buffer.append(fraseTreinamento.getCategoria());
            buffer.append('\t');
            buffer.append(fraseTreinamento.getFrase());
            buffer.append('\n');
        }
        return new ByteArrayInputStream(buffer.toString().getBytes());
    }

}
