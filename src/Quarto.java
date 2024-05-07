public class Quarto {
    private int numeroDoQuarto;
    private final int capacidadeMaxima;
    private int hospedesAtualmente;
    private boolean ocupado;
    private boolean chaveNaRecepcao;

    public Quarto(int numeroDoQuarto) {
        this.numeroDoQuarto = numeroDoQuarto; // Número do quarto é definido na criação
        this.capacidadeMaxima = 4; // Capacidade máxima é sempre 4 e não muda
        this.hospedesAtualmente = 0; // Inicia com 0 hóspedes
        this.ocupado = false; // Inicia como não ocupado
        this.chaveNaRecepcao = true; // Chave começa na recepção
    }

    // Método para definir a situação da chave
    public synchronized void setChaveNaRecepcao(boolean chaveNaRecepcao) {
        this.chaveNaRecepcao = chaveNaRecepcao;
    }


    // Método para obter a situação atual da chave
    public synchronized boolean isChaveNaRecepcao() {
        return this.chaveNaRecepcao;
    }

    // Outros getters e setters conforme necessário

}
