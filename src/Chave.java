public class Chave {
    private Quarto quarto;
    private Hospede guest;

    public Chave(Hospede hospede, Quarto quarto) {
        this.quarto = quarto;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public Hospede getHospede() {
        return hospede;
    }
}