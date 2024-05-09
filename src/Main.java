public class Main {
    public static void main(String[] args) {
        // Cria uma instância do hotel, inicializando todas as entidades necessárias
        Hotel hotel = new Hotel();
        hotel.monitorarEstadoDoHotel();  // Inicia o monitoramento do estado do hotel


        // Inicializa e inicia as threads das camareiras
        for (Camareira camareira : hotel.getCamareiras()) {
            new Thread(camareira).start();  // Cria uma nova thread para cada camareira e inicia sua execução
            // As camareiras começam a verificar e limpar os quartos que estão desocupados e sujos
        }

        // Inicializa e inicia as threads dos recepcionistas
        for (Recepcionista recepcionista : hotel.getRecepcionistas()) {
            new Thread(recepcionista).start();  // Cria uma nova thread para cada recepcionista e inicia sua execução
            // Os recepcionistas começam a gerenciar a recepção e o atendimento dos hóspedes na lista de espera
        }

        // Inicializa e inicia as threads dos hóspedes
        for (Hospede hospede : hotel.getHospedes()) {
            new Thread(hospede).start();  // Cria uma nova thread para cada hóspede e inicia sua execução
            // Os hóspedes começam a tentar alocar quartos, ou são colocados em uma lista de espera se necessário
        }
    }
}
