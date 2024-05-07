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
}
