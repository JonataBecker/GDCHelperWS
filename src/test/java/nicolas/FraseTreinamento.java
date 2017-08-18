package nicolas;

public class FraseTreinamento {
    
    private final String categoria;
    private final String frase;

    public FraseTreinamento(String categoria, String frase) {
        this.categoria = categoria;
        this.frase = frase;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getFrase() {
        return frase;
    }
    
}
