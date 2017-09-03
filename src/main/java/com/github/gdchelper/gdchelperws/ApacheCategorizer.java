/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.gdchelper.gdchelperws;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Side;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

/**
 *
 */
public class ApacheCategorizer {

    /** Categorizador */
    private final DocumentCategorizerME categorizer;

    /**
     * Cria o classificador da Apache
     *
     * @param categorizer
     */
    private ApacheCategorizer(DocumentCategorizerME categorizer) {
        this.categorizer = categorizer;
    }

    /**
     * Cria o classificador a partir dos dados de treinamento padrões
     *
     * @return ApacheCategorizer
     * @throws java.io.IOException
     */
    public static ApacheCategorizer fromTraining() throws IOException {
        return fromTraining(loadTreinamentos());
    }

    /**
     * Cria o classificador a partir dos dados de treinamento
     *
     * @param treinamento
     * @return ApacheCategorizer
     * @throws java.io.IOException
     */
    public static ApacheCategorizer fromTraining(List<FraseTreinamento> treinamento) throws IOException {
        ObjectStream lineStream = new PlainTextByLineStream(() -> getStreamFrom(treinamento), "UTF-8");
        ObjectStream sampleStream = new DocumentSampleStream(lineStream);
        TrainingParameters params = TrainingParameters.defaultParams();
        params.put(TrainingParameters.CUTOFF_PARAM, 10);
        params.put(TrainingParameters.ITERATIONS_PARAM, treinamento.size());
        PrintStream original = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        DoccatModel model = DocumentCategorizerME.train("pt", sampleStream, params, new DoccatFactory());
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
        System.setOut(original);
        return new ApacheCategorizer(myCategorizer);
    }
    
    private static List<FraseTreinamento> loadTreinamentos() throws IOException {
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
    
    private static String getModel(String name) {
        String path = ApacheCategorizer.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("target/test-classes/", "");
        return path + "src\\main\\java\\com\\github\\gdchelper\\gdchelperws\\models\\" + name;
    }

    /**
     * Retorna o Stream à partir da lista de treinamento
     * 
     * @param treinamento
     * @return InputStream
     */
    private static InputStream getStreamFrom(List<FraseTreinamento> treinamento) {
        StringBuilder buffer = new StringBuilder();
        for (FraseTreinamento fraseTreinamento : treinamento) {
            buffer.append(fraseTreinamento.getCategoria());
            buffer.append('\t');
            buffer.append(fraseTreinamento.getFrase());
            buffer.append('\n');
        }
        return new ByteArrayInputStream(buffer.toString().getBytes());
    }
    
    /**
     * Classifica uma frase
     * 
     * @param sentence
     * @return 
     */
    public CategorizerResult categorize(String sentence) {
        Map<String, Double> scoreMap = categorizer.scoreMap(new SimpleTokenizer().tokenize(sentence));
        return new CategorizerResult(scoreMap);
    }

}
