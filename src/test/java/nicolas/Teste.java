package nicolas;

import com.github.gdchelper.gdchelperws.FraseTreinamento;
import com.github.gdchelper.db.DataFileReader;
import com.github.gdchelper.gdchelperws.ApacheCategorizer;
import com.github.gdchelper.gdchelperws.SentenceFilter;
import com.github.gdchelper.gdchelperws.SentencePreprocessor;
import com.github.gdchelper.jpa.Atendimento;
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

public class Teste {
    
    static final int LIMITE = 5000;
    static final int TAMANHO_MINIMO = 5;
    static final double TREINAMENTO = 0.7;
    static final int SEED = 2;

    public static void main(String[] args) throws Exception {
        Teste t = new Teste();
        DataFileReader data = new DataFileReader();
        
        List<Atendimento> atendimentos = data.loadAtendimentos(System.getProperty("user.home") + "/atendimentos.csv");
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
        
        int cutoff = 2;
        ObjectStream lineStream = new PlainTextByLineStream(() -> t.getStreamFrom(treinamento), "UTF-8");
        ObjectStream sampleStream = new DocumentSampleStream(lineStream);
        TrainingParameters params = new TrainingParameters();
        params.put(TrainingParameters.CUTOFF_PARAM, 2);
        params.put(TrainingParameters.ITERATIONS_PARAM, treinamento.size());
        DoccatModel model = DocumentCategorizerME.train("pt", sampleStream, params, new DoccatFactory());
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);

        Map<String, Integer> totais = new HashMap<>();
        
        for (Atendimento atendimento : atendimentos) {
            List<String> sentences = t.extractSentences(atendimento.getMensagem());
//            System.out.println("----------------------");
            for (int i = 0; i < sentences.size(); i++) {
                String sentence = sentences.get(i);

                double[] outcomes = myCategorizer.categorize(new String[] {sentence});
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

        t.testa(teste, myCategorizer, classes);
        
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
    
    private void testa(List<FraseTreinamento> teste, DocumentCategorizerME myCategorizer, Set<String> classes) {
        int certos = 0;
        int errados = 0;
        
        Map<String, Map<String, Integer>> matriz = new HashMap<>();
        for (String chute : classes) {
            matriz.put(chute, new HashMap<>());
            for (String real : classes) {
                matriz.get(chute).put(real, 0);
            }
        }
        
        
        for (FraseTreinamento fraseTreinamento : teste) {
            double[] outcomes = myCategorizer.categorize(new String[] {fraseTreinamento.getFrase()});
            String classificado = myCategorizer.getBestCategory(outcomes);
            String esperado = fraseTreinamento.getCategoria();
            if (classificado.equals(esperado)) {
                certos++;
            } else {
                errados++;
            }
            System.out.println(classificado + " " + esperado);
            int atual = matriz.get(classificado).get(esperado);
            matriz.get(classificado).put(esperado, atual + 1);
        }
        System.out.println("Acertei " + certos + "/" + (certos + errados) + " (" + ((double)certos / (double)(certos + errados) * 100) + "%)");
        
        System.out.print("\t");
        for (String classe : classes) {
            System.out.print(classe + "\t");
        }
        System.out.println("<- Esperado");
        for (String chute : classes) {
            System.out.print(chute + "\t");
            for (String real : classes) {
                if (chute.equals(real)) {
                    System.out.print(matriz.get(chute).get(real) + "*\t");
                } else {
                    System.out.print(matriz.get(chute).get(real) + "\t");
                }
            }
            System.out.println();
        }
        System.out.println(" ^ \n | \n Classificado");
    }
    
    private List<String> extractSentences(String text) throws IOException {
        List<String> list = new ArrayList<>();
        // always start with a model, a model is learned from training data
//      InputStream is = Teste.class.getResourceAsStream("/com/github/gdchelper/gdchelperws/models/pt-sent.bin");
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
        int i = 0;
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
