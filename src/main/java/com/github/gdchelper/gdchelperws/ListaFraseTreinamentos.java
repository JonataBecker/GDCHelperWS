package com.github.gdchelper.gdchelperws;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Lista de frases de treinamento
 */
public class ListaFraseTreinamentos {
    
    /** Lista em cache */
    private Set<FraseTreinamento> cachedList;
    
    /**
     * Retorna a lista de frases de treinamento
     * 
     * @return List
     */
    public synchronized List<FraseTreinamento> getList() {
        if (cachedList == null) {
            cachedList = load();
        }
        return new ArrayList<>(cachedList);
    }
    
    /**
     * Adiciona uma frase a lista
     * 
     * @param frase 
     */
    public void add(FraseTreinamento frase) {
        getList();
        for (FraseTreinamento theFrase : getList()) {
            if (theFrase.getFrase().equalsIgnoreCase(frase.getFrase())) {
                cachedList.remove(theFrase);
            }
        }
        cachedList.add(frase);
        persist();
    }
    
    /**
     * Retorna se uma frase é de treinamento ou não
     * 
     * @param frase
     * @return boolean
     */
    public boolean isFraseTreinamento(String frase) {
        for (FraseTreinamento theFrase : getList()) {
            if (theFrase.getFrase().equalsIgnoreCase(frase)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Carrega a lista de frases
     * 
     * @return Set
     */
    private Set<FraseTreinamento> load() {
        Set<FraseTreinamento> treinamentos = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getModel("sentiment.bin")))) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        treinamentos = treinamentos.stream().map((frase) -> new FraseTreinamento(frase.getCategoria().replaceFirst("^m", ""), frase.getFrase())).collect(Collectors.toSet());
        treinamentos = treinamentos.stream().filter((frase) -> !frase.getCategoria().equals("pergu")).collect(Collectors.toSet());
        return treinamentos;
    }

    /**
     * Persiste a lista de frases
     */
    private void persist() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(getModelOut("sentiment.bin")))) {
            List<FraseTreinamento> frases = getList();
            frases.sort((o1, o2) -> {
                if (o1.getCategoria().equals(o2.getCategoria())) {
                    return o1.getFrase().compareTo(o2.getFrase());
                }
                return o1.getCategoria().compareTo(o2.getCategoria());
            });
            for (FraseTreinamento fraseTreinamento : frases) {
                writer.write(fraseTreinamento.getCategoria() + "\t" + fraseTreinamento.getFrase());
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Retorna o arquivo do modelo
     * 
     * @param name
     * @return InputStream
     */
    private InputStream getModel(String name) {
        return ApacheCategorizer.class.getResourceAsStream("/com/github/gdchelper/gdchelperws/models/" + name);
    }
    
    private OutputStream getModelOut(String name) throws IOException {
        String path = ListaFraseTreinamentos.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                .replace("target/GDCHelperWS-1.0/WEB-INF/classes", "")
                .replace("target\\GDCHelperWS-1.0\\WEB-INF\\classes", "")
                .replace("target/classes/", "");
        return new FileOutputStream(path + "src\\main\\resources\\com\\github\\gdchelper\\gdchelperws\\models\\" + name);
    }

    
}
