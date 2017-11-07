package nicolas;

import com.github.gdchelper.gdchelperws.ApacheCategorizer;
import com.github.gdchelper.gdchelperws.CategorizerResult;
import com.github.gdchelper.gdchelperws.FraseTreinamento;
import com.github.gdchelper.gdchelperws.SentencePreprocessor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class TestaClassificacaoApacheNlp {
    
    static final double TREINAMENTO = 0.8;
    static final int SEED = 5;

    public static void main(String[] args) throws Exception {
        TestaClassificacaoApacheNlp t = new TestaClassificacaoApacheNlp();
        List<FraseTreinamento> classificados = t.loadTreinamentos();
        t.classificaTesta(classificados, SEED, true);
        List<Double> pcts = t.classificaTestaVarios(classificados, false);
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
    
    public double classificaTestaVariosComMedia(List<FraseTreinamento> classificados) throws IOException {
        List<Double> pcts = classificaTestaVarios(classificados, false);
        double soma = 0;
        for (Double pct : pcts) {
            soma += pct;
        }
        return (soma / (double)pcts.size());
    }
    
    public List<Double> classificaTestaVarios(List<FraseTreinamento> classificados, boolean exibe) throws IOException {
        List<Double> pcts = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            pcts.add(classificaTesta(classificados, i, exibe));
        }
        return pcts;
    }
    
    public double classificaTesta(List<FraseTreinamento> classificados, long seed, boolean exibe) throws IOException {
        classificados = new ArrayList<>(classificados);
        // TESTE: USA SÓ BOM E RUIM
        classificados = classificados.stream().map((frase) -> new FraseTreinamento(frase.getCategoria().replaceFirst("^m", ""), frase.getFrase())).collect(Collectors.toList());
        // TESTE: IGNORA NEUTRO
        //classificados = classificados.stream().filter((frase) -> !frase.getCategoria().equals("neutro")).collect(Collectors.toList());
        classificados = classificados.stream().filter((frase) -> !frase.getCategoria().equals("pergu")).collect(Collectors.toList());
        
        List<FraseTreinamento> treinamento = new ArrayList<>(classificados);
        List<FraseTreinamento> teste = divide(treinamento, seed);
        Set<String> classes = new HashSet<>();
        for (FraseTreinamento classificado : classificados) {
            classes.add(classificado.getCategoria());
        }
        if (exibe) {
            System.out.println(treinamento.size() + " registros de treinamento");
            System.out.println(teste.size() + " registros de teste");
        }

        ApacheCategorizer categorizer = ApacheCategorizer.fromTraining(treinamento);
        return testa(teste, categorizer, classes, exibe);
        
    }
    
    private List<FraseTreinamento> divide(List<FraseTreinamento> treinamento, long seed) {
        List<FraseTreinamento> teste = new ArrayList<>();
        int registrosTeste = (int) (treinamento.size() * (1d - TREINAMENTO));
        Random r = new Random(seed);
        for (int i = 0; i < registrosTeste; i++) {
            int index = r.nextInt(treinamento.size());
            teste.add(treinamento.remove(index));
        }
        return teste;
    }
    
    private double testa(List<FraseTreinamento> teste, ApacheCategorizer categorizer, Set<String> classes, boolean echo) {
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
            CategorizerResult outcomes = categorizer.categorize(fraseTreinamento.getFrase());
            String classificado = outcomes.getBest();
            String esperado = fraseTreinamento.getCategoria();

            if (classificado.equals(esperado)) {
                certos++;
            } else {
                if (echo) {
                    System.out.println("## Errou: " + fraseTreinamento.getFrase());
                    System.out.println("\tEsperado: " + esperado);
                    System.out.println("\tClassifi: " + classificado);
                }
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
        return path + "src\\main\\resources\\com\\github\\gdchelper\\gdchelperws\\models\\" + name;
    }

}
