package nicolas;

public class Atendimento {
    
    private int cliente;
    private String texto;

    public Atendimento() {
        this.texto = "";
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    
}
