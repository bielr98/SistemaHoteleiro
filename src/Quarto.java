public class Quarto {
    private final int numeroDoQuarto;  // Número identificador do quarto

    // Estado do quarto: se está ocupado por algum hóspede
    private boolean ocupado;
    // Estado de limpeza do quarto: se está limpo ou não
    private boolean limpo;
    // Estado de reserva do quarto: se está reservado ou não
    private boolean reservado;

    // Construtor para inicializar o quarto com um número específico
    public Quarto(int numeroDoQuarto) {
        this.numeroDoQuarto = numeroDoQuarto;
        this.ocupado = false;  // Inicialmente o quarto não está ocupado
        this.limpo = true;     // Inicialmente o quarto está limpo
        this.reservado = false; // Inicialmente o quarto não está reservado
    }

    // Método para verificar se o quarto está disponível para alocação
    public synchronized boolean disponivel() {
        // Um quarto disponível não está reservado e está limpo
        return !reservado && limpo;
    }

    // Método para marcar o quarto como ocupado, não limpo e reservado
    public synchronized void ocupadoEReservado() {
        ocupado = true;
        limpo = false;
        reservado = true;
    }

    // Método para tornar o quarto disponível, ou seja, desocupado e não reservado
    public synchronized void tornarDisponivel() {
        ocupado = false;
        reservado = false;
    }

    // Método para marcar o quarto como limpo
    public synchronized void tornarLimpo() {
        limpo = true;
    }

    // Método para verificar se o quarto está limpo
    public synchronized boolean estaLimpo() {
        return limpo;
    }

    // Método para simular o hóspede saindo do quarto por um tempo
    public synchronized void getOut() {
        this.ocupado = false;
    }

    // Método para simular o hóspede retornando ao quarto
    public synchronized void getBackToTheRoom() {
        this.ocupado = true;
    }

    // Getter para o estado de ocupação do quarto
    public boolean getOcupado() {
        return ocupado;
    }

    // Getter para o número do quarto
    public int getNumeroDoQuarto() {
        return numeroDoQuarto;
    }

    // Getter para a capacidade do quarto, assumindo uma capacidade padrão de 4 hóspedes
    public int getCapacidade() {
        return 4;
    }
}
