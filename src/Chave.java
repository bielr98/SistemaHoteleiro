public class Chave {
    // Referência ao quarto associado à chave
    private Quarto quarto;
    // Referência ao hóspede que possui a chave
    private Hospede hospede;

    // Construtor que inicializa a chave com um hóspede e um quarto associados
    public Chave(Hospede hospede, Quarto quarto) {
        this.hospede = hospede;  // Armazena o hóspede associado à chave
        this.quarto = quarto;    // Armazena o quarto associado à chave
    }

    // Método para obter o quarto associado à chave
    public Quarto getQuarto() {
        return quarto;
    }

    // Método para obter o hóspede associado à chave
    public Hospede getHospede() {
        return hospede;
    }
}
