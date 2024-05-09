import java.util.*;

public class Hotel {
    // Constantes que definem a configuração inicial do hotel
    private static final int NUMERO_DE_QUARTOS = 10;
    private static final int NUMERO_DE_HOSPEDES = 50;
    private static final int NUMERO_DE_CAMAREIRAS = 10;
    private static final int NUMERO_DE_RECEPCIONISTAS = 5;

    // Listas para armazenar as entidades do hotel: quartos, hóspedes, camareiras e recepcionistas
    private final List<Quarto> quartos = new ArrayList<>();
    private final List<Hospede> hospedes = new ArrayList<>();
    private final List<Camareira> camareiras = new ArrayList<>();
    private final List<Recepcionista> recepcionistas = new ArrayList<>();
    // Fila para gerenciar a lista de espera de hóspedes
    private final Queue<Hospede> listaDeEspera = new LinkedList<>();

    // Locks para controle de sincronização entre diferentes threads
    private final Object recepcaoLock = new Object();
    private final Object camareiraLock = new Object();
    private final Object hospedeLock = new Object();

    // Construtor do hotel, inicializa os recursos do hotel
    public Hotel() {
        // Inicializa os quartos do hotel
        for (int i = 0; i < NUMERO_DE_QUARTOS; i++) {
            quartos.add(new Quarto(i + 1));
        }

        // Inicializa as camareiras Inicializa os recepcionistas
        iniciarCamareirasERecepcionistas();

        //Cria Grupos de hospedes de tamanho 1 a 12 em um tempo aleatorio entre 1 e 3segundos
        iniciarHospedesAssincronamente();

        imprimirEstadoDosQuartos();
    }

    // Método para iniciar camareiras e recepcionistas
    private void iniciarCamareirasERecepcionistas() {
        // Inicia camareiras
        for (int i = 0; i < NUMERO_DE_CAMAREIRAS; i++) {
            Camareira camareira = new Camareira(this);
            camareiras.add(camareira);
            new Thread(camareira).start();  // Inicia a thread da camareira
        }

        // Inicia recepcionistas
        for (int i = 0; i < NUMERO_DE_RECEPCIONISTAS; i++) {
            Recepcionista recepcionista = new Recepcionista(this);
            recepcionistas.add(recepcionista);
            new Thread(recepcionista).start();  // Inicia a thread do recepcionista
        }
    }

    private void iniciarHospedesAssincronamente() {
        new Thread(() -> {
            for (int i = 0; i < NUMERO_DE_HOSPEDES; i++) {
                try {
                    // Cria um novo Hospede com um nome sequencial e um tamanho de grupo aleatório entre 1 e 10
                    Hospede hospede = new Hospede(this, "Hospede " + i, 1 + (int) (Math.random() * 10));
                    hospedes.add(hospede);
                    new Thread(hospede).start();  // Inicia a thread do hóspede imediatamente após sua criação
                    // Pausa entre 1 e 3 segundos
                    Thread.sleep(1000 + new Random().nextInt(2000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Interrompido ao criar hospedes");
                }
            }
        }).start();  // Inicia a thread que gerencia a criação de hóspedes
    }

    // Método para imprimir o estado dos quartos
    private void imprimirEstadoDosQuartos() {
        for (Quarto quarto : quartos) {
            String estado = quarto.disponivel() ? "Disponível para Reserva" : "Não Disponível para Reserva";
            System.out.println("Quarto " + quarto.getNumeroDoQuarto() + " - " + estado);
        }
    }

    // Método para obter um quarto disponível
    public Quarto getQuartoDisponivel() {
        synchronized (recepcaoLock) { // Sincroniza acesso aos quartos
            for (Quarto quarto : quartos) {
                if (quarto.disponivel()) {
                    return quarto;
                }
            }
        }
        return null; // Retorna null se não houver quartos disponíveis
    }

    // Método para adicionar um hóspede à lista de espera
    public void addHospedeNaListaDeEspera(Hospede hospede) {
        synchronized (hospedeLock) { // Sincroniza o acesso à lista de espera
            listaDeEspera.add(hospede);
        }
    }

    // Método para obter o próximo hóspede da lista de espera
    public Hospede getNextGuestFromWaitingList() {
        synchronized (hospedeLock) { // Sincroniza o acesso à lista de espera
            return listaDeEspera.poll();
        }
    }

    // Método para iniciar o monitoramento do estado do hotel
    public void monitorarEstadoDoHotel() {
        new Thread(() -> {
            while (true) {
                if (todosHospedesAcomodadosOuForamEmboraeQuartosVazios()) {
                    System.out.println("Todos os hóspedes foram acomodados ou decidiram ir embora, e todos os quartos estão vazios. Encerrando o sistema.");
                    System.exit(0);  // Encerra a aplicação
                }
                try {
                    Thread.sleep(1000);  // Espera um segundo antes de verificar novamente
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Thread de monitoramento foi interrompida.");
                    return;
                }
            }
        }).start();
    }

    // Método para verificar se todos os hóspedes foram acomodados ou foram embora, e se todos os quartos estão vazios
    private boolean todosHospedesAcomodadosOuForamEmboraeQuartosVazios() {
        synchronized (hospedeLock) {
            if (!listaDeEspera.isEmpty() || !hospedes.isEmpty()) {
                return false;  // Se ainda há hóspedes na lista de espera ou ativos, não encerra
            }
        }

        synchronized (recepcaoLock) {
            for (Quarto quarto : quartos) {
                if (quarto.getOcupado() || !quarto.estaLimpo()) {
                    return false;  // Se algum quarto está ocupado ou não está limpo, não encerra
                }
            }
        }

        return true;  // Se todas as condições forem atendidas, retorna true
    }


    // Getters para acesso aos recursos e locks do hotel
    public Object getRecepcaoLock() {
        return recepcaoLock;
    }

    public Object getCamareiraLock() {
        return camareiraLock;
    }

    public List<Quarto> getQuartos() {
        return quartos;
    }

    public List<Hospede> getHospedes() {
        return hospedes;
    }

    public List<Camareira> getCamareiras() {
        return camareiras;
    }

    public List<Recepcionista> getRecepcionistas() {
        return recepcionistas;
    }

    public Queue<Hospede> getListaDeEspera() {
        return listaDeEspera;
    }
}
