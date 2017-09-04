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
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

/**
 * Gera a base de treinamento do ApacheNLP à partir do Watson
 * 
 * Registros = 1263813;
 */
public class AutoClassificador {

    static final int START = 205000;
//    static final int END = 1263813; // Último registro
    static final int END = 206000;
    static final int TAMANHO_MINIMO = 5;
    static final double TREINAMENTO = 0.7;
    static final int SEED = 2;

    public static void main(String[] args) throws Exception {
        AutoClassificador t = new AutoClassificador();
        List<Atendimento> atendimentos = new DataFileReader(START, END).loadAtendimentos("atendimentos.csv");
        List<FraseTreinamento> classificados = t.loadTreinamentos();
        
        // TESTE: USA SÓ BOM E RUIM
        classificados = classificados.stream().map((frase) -> new FraseTreinamento(frase.getCategoria().replaceFirst("^m", ""), frase.getFrase())).collect(Collectors.toList());
        // TESTE: IGNORA NEUTRO
        classificados = classificados.stream().filter((frase) -> !frase.getCategoria().equals("neutro")).collect(Collectors.toList());
        
        TestaClassificacaoApacheNlp testador = new TestaClassificacaoApacheNlp();
        
        List<FraseTreinamento> treinamento = new ArrayList<>(classificados);
        ApacheCategorizer categorizer = ApacheCategorizer.fromTraining(treinamento);
        double base = testador.classificaTestaVariosComMedia(treinamento);
        System.out.println("base: " + base);
        
        NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
                NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
                "35ab4377-2c9a-448c-83ab-d4583503bb6f",
                "NOvvgbAGbAT3"
        );
        SentimentOptions sentiment = new SentimentOptions.Builder().build();
        Features features = new Features.Builder().sentiment(sentiment).build();
        
        
        DecimalFormat df = new DecimalFormat("#0.00");
        
        for (Atendimento atendimento : atendimentos) {
            List<String> sentences = t.extractSentences(atendimento.getMensagem());
            for (int i = 0; i < sentences.size(); i++) {
                String sentence = sentences.get(i);

                CategorizerResult outcomes = categorizer.categorize(sentence);
                double scoreBom = outcomes.getTrust("bom");
                double scoreRuim = outcomes.getTrust("ruim");
                double score = scoreBom - scoreRuim;
                double scoreWatson = 0;
                        
                
                try {
                    PrintStream original = System.out;
                    System.setOut(new PrintStream(new ByteArrayOutputStream()));
                    AnalyzeOptions parameters = new AnalyzeOptions.Builder().text(sentence).features(features).build();
                    AnalysisResults response = service.analyze(parameters).execute();
                    scoreWatson = response.getSentiment().getDocument().getScore();
                    System.setOut(original);
                } catch (Exception e) {
//                    System.out.println("erro: " + sentence);
                }

                if (Math.abs(scoreWatson - score) > 0.25) {
                    continue;
                }
                
                if (score > -0.75 && score < 0.75) {
                    continue;   
                }
                String classe;
                if (score < 0) {
                    classe = "ruim";
                } else {
                    classe = "bom";
                }
                
                
                treinamento.add(new FraseTreinamento(classe, sentence));
//                System.out.println("tentando...");
//                double newBase = testador.classificaTestaVariosComMedia(treinamento);
//                if (newBase > base || base - newBase > 5) {
//                    if (newBase > base) {
//                        base = newBase;
//                        System.out.println("aumentei pra " + newBase);
//                    } else {
//                        base += (newBase - base) * 0.01;
//                        System.out.println("baixei pouco: " + newBase + " " + (newBase - base));
//                    }
//                    
                    BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.home") + "\\novo_modelo.bin"));
                    for (FraseTreinamento fraseTreinamento : treinamento) {
                        writer.write(fraseTreinamento.getCategoria() + "\t" + fraseTreinamento.getFrase() + "\n");
                    }
                    writer.close();
//
//                    
//                } else {
//                    treinamento.remove(treinamento.size() - 1);
//                }
//                System.out.println(df.format(score) + "\t" + df.format(scoreBom) + "\t" + df.format(scoreRuim) + "\t" + sentence);
            }
        }
        
        System.out.println("base final: " + base);
        
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
        String path = AutoClassificador.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("target/test-classes/", "");
        return path + "src\\main\\java\\com\\github\\gdchelper\\gdchelperws\\models\\" + name;
    }

}
