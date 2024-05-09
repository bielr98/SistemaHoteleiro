public class Quarto {
    private final int numeroDoQuarto;

    //o quarto esta ocupado ou não (quando o hóspede sai por um tempo do quarto ele não fica ocupado, entretanto a vaiável isReserved vai continuar true
    private boolean ocupado;
    private boolean limpo;

    // essa variavel diz respeito o quarto está reservado ou não.
    private boolean reservado;

    public Quarto(int numeroDoQuarto) {
        this.numeroDoQuarto = numeroDoQuarto;
        this.ocupado = false;
        this.limpo = true;
        this.reservado = false;
    }

    public synchronized boolean disponivel() {
        return !reservado && limpo;
    }

    public synchronized void ocupadoEReservado() {
        ocupado = true;
        limpo = false;
        reservado = true;
    }

    public synchronized void tornarDisponivel() {
        ocupado = false;
        reservado = false;
    }

    public synchronized void tornarLimpo() {
        limpo = true;
    }

    public synchronized boolean estaLimpo() {
        return limpo;
    }

    // FUNCOES QUE DIZEM RESPEITO A FUNCIONALIDADE DO HOSPEDE SAIR DO QUARTO POR UM TEMPO
    public synchronized void getOut() {
        this.ocupado = false;
    }

    public synchronized void getBackToTheRoom () {
        this.ocupado = true;
    }

    // GETTERS

    public boolean getOcupado() {
        return ocupado;
    }

    public int getNumeroDoQuarto() {
        return numeroDoQuarto;
    }

    public int getCapacidade() {
        return 4; // Assuming each room has a capacity of 4 guests
    }
}