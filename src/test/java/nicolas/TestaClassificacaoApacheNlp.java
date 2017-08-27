package nicolas;

import com.github.gdchelper.db.DataFileReader;
import com.github.gdchelper.gdchelperws.SentencePreprocessor;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

public class TestaClassificacaoApacheNlp {
    
    static final double TREINAMENTO = 0.8;

    public static void main(String[] args) throws Exception {
        TestaClassificacaoApacheNlp t = new TestaClassificacaoApacheNlp();
        DataFileReader data = new DataFileReader();
        List<FraseTreinamento> classificados = t.loadTreinamentos();
        
        // TESTE: USA SÓ BOM E RUIM
        classificados = classificados.stream().map((frase) -> new FraseTreinamento(frase.getCategoria().replaceFirst("^m", ""), frase.getFrase())).collect(Collectors.toList());
        // TESTE: IGNORA NEUTRO
        classificados = classificados.stream().filter((frase) -> !frase.getCategoria().equals("neutro")).collect(Collectors.toList());
        
        List<FraseTreinamento> treinamento = new ArrayList<>(classificados);
        List<FraseTreinamento> teste = t.divide(treinamento, 1);
        Set<String> classes = new HashSet<>();
        for (FraseTreinamento classificado : classificados) {
            classes.add(classificado.getCategoria());
        }
        System.out.println(treinamento.size() + " registros de treinamento");
        System.out.println(teste.size() + " registros de teste");
        int cutoff = 2;
        ObjectStream lineStream = new PlainTextByLineStream(t.getStreamFrom(treinamento), "UTF-8");
        ObjectStream sampleStream = new DocumentSampleStream(lineStream);
        DoccatModel model = DocumentCategorizerME.train("pt", sampleStream, cutoff, treinamento.size());
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
        
        t.testa(teste, myCategorizer, classes, true);
        
        List<Double> pcts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            teste = t.divide(new ArrayList<>(classificados), i);
            pcts.add(t.testa(teste, myCategorizer, classes, false));
        }
        double menor = 9999;
        double maior = 0;
        double soma = 0;
        for (Double pct : pcts) {
            soma += pct;
            menor = Math.min(menor, pct);
            maior = Math.max(maior, pct);
        }
        System.out.println("");
        System.out.println("Média : " + (soma / pcts.size()));
        System.out.println("Melhor: " + maior);
        System.out.println("Menor : " + menor);
        
    }

    
    
    private List<FraseTreinamento> divide(List<FraseTreinamento> treinamento, long seed) {
        List<FraseTreinamento> teste = new ArrayList<>();
        int registrosTeste = (int) (treinamento.size() * (1d - TREINAMENTO));
        Random r = new Random(seed + treinamento.size());
        for (int i = 0; i < registrosTeste; i++) {
            int index = r.nextInt(treinamento.size());
            teste.add(treinamento.remove(index));
        }
        return teste;
    }
    
    private double testa(List<FraseTreinamento> teste, DocumentCategorizerME myCategorizer, Set<String> classes, boolean echo) {
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
            double[] outcomes = myCategorizer.categorize(fraseTreinamento.getFrase());
            String classificado = myCategorizer.getBestCategory(outcomes);
            String esperado = fraseTreinamento.getCategoria();
            if (classificado.equals(esperado)) {
                certos++;
            } else {
                errados++;
            }
            int atual = matriz.get(classificado).get(esperado);
            matriz.get(classificado).put(esperado, atual + 1);
        }
        double pct = ((double)certos / (double)(certos + errados) * 100);
        if (!echo) {
            return pct;
        }
        System.out.println("Acertei " + certos + "/" + (certos + errados) + " (" + pct + "%)");
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
        return pct;
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
