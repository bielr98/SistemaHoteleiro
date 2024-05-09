public class Camareira extends Thread {
    private final Hotel hotel;  // Referência ao hotel onde a camareira trabalha

    // Construtor para inicializar a camareira com um hotel
    public Camareira(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void run() {
        while (true) {  // Loop infinito para a camareira sempre estar disponível para limpeza
            // Bloqueia o acesso ao lock específico da camareira para sincronização
            synchronized (hotel.getCamareiraLock()) {
                // Bloqueia o acesso ao lock da recepção para evitar conflitos com recepcionistas
                synchronized (hotel.getRecepcaoLock()) {
                    // Itera sobre todos os quartos do hotel
                    for (Quarto quarto : hotel.getQuartos()) {
                        // Verifica se o quarto está desocupado e sujo
                        if (!quarto.getOcupado() && !quarto.estaLimpo()) {
                            quarto.tornarLimpo();  // Limpa o quarto
                            // Imprime uma mensagem informando que o quarto foi limpo
                            System.out.println("Camareira Limpou o quarto " + quarto.getNumeroDoQuarto());
                        }
                    }
                }
            }
        }
    }
}
