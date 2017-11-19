package com.github.gdchelper.gdchelperws;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

/**
 * Classe responsável por gerar o score de satisfação de uma string
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
        return fromTraining(new ListaFraseTreinamentos().getList());
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
