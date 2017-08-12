package nicolas;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
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

    static final int LIMITE = 1000;
    static final int TAMANHO_MINIMO = 5;
    
    public static void main(String[] args) throws Exception {
        Teste teste = new Teste();
        long l = System.currentTimeMillis();
        List<Atendimento> atendimentos = teste.loadAtendimentos();
        System.out.println(atendimentos.size() + " atendimentos carregados em " + (System.currentTimeMillis() - l) + "ms");

        int cutoff = 2;
        int trainingIterations = 30;
        InputStream dataIn = new FileInputStream("E:\\Documentos\\Projetos\\GDCHelperWS\\src\\main\\java\\com\\github\\gdchelper\\gdchelperws\\models\\sentiment.bin");
        ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
        ObjectStream sampleStream = new DocumentSampleStream(lineStream);
        DoccatModel model = DocumentCategorizerME.train("pt", sampleStream, cutoff, trainingIterations);
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
        
        for (Atendimento atendimento : atendimentos) {
            List<String> sentences = teste.extractSentences(atendimento.getTexto());
//            System.out.println("----------------------");
            for (int i = 0; i < sentences.size(); i++) {
                String sentence = sentences.get(i);
                
                double[] outcomes = myCategorizer.categorize(sentence);
                String category = myCategorizer.getBestCategory(outcomes);
                int index = myCategorizer.getIndex(category);
                double prob = outcomes[index];
                
                if (prob < 0.8) {
                    continue;
                }
                
                System.out.println(i +"\t"+category +" (" + new DecimalFormat("#00.00").format(prob * 100) + "%)\t"+sentence);
            }
        }
    }
    
    private List<String> extractSentences(String text) throws IOException {
        List<String> list = new ArrayList<>();
        // always start with a model, a model is learned from training data
//      InputStream is = Teste.class.getResourceAsStream("/com/github/gdchelper/gdchelperws/models/pt-sent.bin");
        InputStream is = new FileInputStream("E:\\Documentos\\Projetos\\GDCHelperWS\\src\\main\\java\\com\\github\\gdchelper\\gdchelperws\\models\\pt-sent.bin");
        SentenceModel model = new SentenceModel(is);
        SentenceDetectorME sdetector = new SentenceDetectorME(model);

        String[] lines = text.split("\n");
        for (String line : lines) {
            line = preprocessLine(line);
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
    
    private String preprocessLine(String line) {
        line = line.replaceAll("^\\s*\\[\\d\\d:\\d\\d:\\d\\d\\] .*?: ", "");
        line = line.replaceAll("^\\- .*?: ", "");
        return line;
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
        try (FileReader reader = new FileReader("e:\\exportar\\exportar.csv")) {
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
    
    
    
}
