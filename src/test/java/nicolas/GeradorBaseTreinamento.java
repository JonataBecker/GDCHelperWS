package nicolas;

import com.github.gdchelper.gdchelperws.FraseTreinamento;
import com.github.gdchelper.db.DataFileReader;
import com.github.gdchelper.gdchelperws.ApacheCategorizer;
import com.github.gdchelper.gdchelperws.CategorizerResult;
import com.github.gdchelper.gdchelperws.SentenceFilter;
import com.github.gdchelper.gdchelperws.SentencePreprocessor;
import com.github.gdchelper.jpa.Atendimento;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.SentimentOptions;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

/**
 * Gera a base de treinamento do ApacheNLP à partir do Watson
 * 
 * Registros = 1263813;
 */
public class GeradorBaseTreinamento {

    static final int START = 1262033;
//    static final int END = 1263813; // Último registro
    static final int END = 1263032;
    static final int TAMANHO_MINIMO = 5;
    static final double TREINAMENTO = 0.7;
    static final int SEED = 2;

    public static void main(String[] args) throws Exception {
        GeradorBaseTreinamento t = new GeradorBaseTreinamento();
        List<Atendimento> atendimentos = new DataFileReader(START, END).loadAtendimentos(System.getProperty("user.home") + "/exportar.csv");
        List<FraseTreinamento> classificados = t.loadTreinamentos();
        
        // TESTE: USA SÓ BOM E RUIM
        classificados = classificados.stream().map((frase) -> new FraseTreinamento(frase.getCategoria().replaceFirst("^m", ""), frase.getFrase())).collect(Collectors.toList());
        // TESTE: IGNORA NEUTRO
        classificados = classificados.stream().filter((frase) -> !frase.getCategoria().equals("neutro")).collect(Collectors.toList());
        
        List<FraseTreinamento> treinamento = new ArrayList<>(classificados);
        List<FraseTreinamento> teste = t.divide(treinamento);
        Set<String> classes = new HashSet<>();
        for (FraseTreinamento classificado : classificados) {
            classes.add(classificado.getCategoria());
        }
        
        System.out.println(treinamento.size() + " registros de treinamento");
        System.out.println(teste.size() + " registros de teste");
        
        NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
                NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
                "35ab4377-2c9a-448c-83ab-d4583503bb6f",
                "NOvvgbAGbAT3"
        );



        SentimentOptions sentiment = new SentimentOptions.Builder().build();
        Features features = new Features.Builder().sentiment(sentiment).build();

        ApacheCategorizer categorizer = ApacheCategorizer.fromTraining(treinamento);
        
        Map<String, Integer> totais = new HashMap<>();
        
        for (Atendimento atendimento : atendimentos) {
            List<String> sentences = t.extractSentences(atendimento.getMensagem());
//            System.out.println("----------------------");
            for (int i = 0; i < sentences.size(); i++) {
                String sentence = sentences.get(i);

                CategorizerResult outcomes = categorizer.categorize(sentence);
                String category = outcomes.getBest();
                double trust = outcomes.getTrust(category);
                
                if (trust < 0.9) {
                    continue;
                }
                
                
                if (!totais.containsKey(category)) {
                    totais.put(category, 0);
                }

//                if (prob < 0.75) {
//                    continue;
//                }

                totais.put(category, totais.get(category) + 1);
//                
//                if (category.equals("0") || category.equals("1") || category.equals("neutro")) {
//                    continue;
//                }
//                
//                if (prob < 0.90) {
//                    continue;
//                }
//                double score = 0;
//                try {
//                    AnalyzeOptions parameters = new AnalyzeOptions.Builder().text(sentence).features(features).build();
//                    AnalysisResults response = service.analyze(parameters).execute();
//                    score = response.getSentiment().getDocument().getScore();
//                } catch (Exception e) {
//                    System.out.println("erro: " + sentence);
//                }
//
//                String categoryWatson = "neutro";
//                if (score > 0.4) {
//                    categoryWatson = "bom";
//                } else {
//                    if (score < -0.4) {
//                        categoryWatson = "ruim";
//                    } else {
//                        continue;
//                    }
//                }
//                System.out.println(response);
                
//                System.out.println(categoryWatson+"\t" + score + "\t"+category +"\t"+sentence);
                System.out.println(category + "\t" + sentence);
            }
        }
        
        for (Map.Entry<String, Integer> entry : totais.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }

        
    }

    private List<FraseTreinamento> divide(List<FraseTreinamento> treinamento) {
        List<FraseTreinamento> teste = new ArrayList<>();
        int registrosTeste = (int) (treinamento.size() * (1d - TREINAMENTO));
        Random r = new Random(SEED + treinamento.size());
        for (int i = 0; i < registrosTeste; i++) {
            int index = r.nextInt(treinamento.size());
            teste.add(treinamento.remove(index));
        }
        return teste;
    }
    
    private List<String> extractSentences(String text) throws IOException {
        List<String> list = new ArrayList<>();
        InputStream is = new FileInputStream(getModel("pt-sent.bin"));
        SentenceModel model = new SentenceModel(is);
        SentenceDetectorME sdetector = new SentenceDetectorME(model);

        String[] lines = text.split("\n");
        for (String line : lines) {
            line = new SentencePreprocessor().process(line);
            String[] sentences = sdetector.sentDetect(line);
            for (String sentence : sentences) {
                if (!new SentenceFilter().test(sentence)) {
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

    private List<FraseTreinamento> loadTreinamentos() throws IOException {
        List<FraseTreinamento> treinamentos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getModel("sentiment.bin")))) {
            while (true) {
                String l = reader.readLine();
                if (l == null) {
                    break;
                }
                if (l.trim().isEmpty()) {
                    continue;
                }
                String[] parts = l.split("\\s+", 2);
                treinamentos.add(new FraseTreinamento(parts[0], new SentencePreprocessor().process(parts[1])));
            }
        }
        return treinamentos;
    }
    
    private String getModel(String name) {
        String path = GeradorBaseTreinamento.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("target/test-classes/", "");
        return path + "src\\main\\java\\com\\github\\gdchelper\\gdchelperws\\models\\" + name;
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
