package nicolas;

import com.github.gdchelper.gdchelperws.SentencePreprocessor;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    static final int LIMITE = 3000;
    static final int TAMANHO_MINIMO = 5;

    public static void main(String[] args) throws Exception {
        Teste teste = new Teste();
        long l = System.currentTimeMillis();
        List<Atendimento> atendimentos = teste.loadAtendimentos();
        System.out.println(atendimentos.size() + " atendimentos carregados em " + (System.currentTimeMillis() - l) + "ms");

        int cutoff = 2;
        int trainingIterations = 34;
        InputStream dataIn = new FileInputStream("D:\\Projects\\GDCHelper\\GDCHelperWS\\src\\main\\java\\com\\github\\gdchelper\\gdchelperws\\models\\sentiment.bin");
        ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
        ObjectStream sampleStream = new DocumentSampleStream(lineStream);
        DoccatModel model = DocumentCategorizerME.train("pt", sampleStream, cutoff, trainingIterations);
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);

        Map<String, Integer> totais = new HashMap<>();
        
        for (Atendimento atendimento : atendimentos) {
            List<String> sentences = teste.extractSentences(atendimento.getTexto());
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
        
    }

    private List<String> extractSentences(String text) throws IOException {
        List<String> list = new ArrayList<>();
        // always start with a model, a model is learned from training data
//      InputStream is = Teste.class.getResourceAsStream("/com/github/gdchelper/gdchelperws/models/pt-sent.bin");
        InputStream is = new FileInputStream("D:\\Projects\\GDCHelper\\GDCHelperWS\\src\\main\\java\\com\\github\\gdchelper\\gdchelperws\\models\\pt-sent.bin");
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
        try (FileReader reader = new FileReader("c:\\users\\pichau\\desktop\\exportar.csv")) {
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
