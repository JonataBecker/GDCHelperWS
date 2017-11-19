package com.github.gdchelper.gdchelperws;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

/**
 * Classe responsável por separar as frases de uma string
 */
public class SentenceSplitter {

    private final SentenceDetectorME sdetector;

    public SentenceSplitter() throws Exception {
        InputStream is = getModel("pt-sent.bin");
        SentenceModel model = new SentenceModel(is);
        is.close();
        sdetector = new SentenceDetectorME(model);
    }

    public List<String> extractSentences(String text) throws IOException {
        List<String> list = new ArrayList<>();
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
        return list;
    }

    private static InputStream getModel(String name) {
        return ApacheCategorizer.class.getResourceAsStream("/com/github/gdchelper/gdchelperws/models/" + name);
    }

}
