import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Recepcionista extends Thread {
    private final Hotel hotel;  // Referência ao hotel associado ao recepcionista
    private List<Chave> chaves = new ArrayList<>();  // Lista de chaves que o recepcionista gerencia
    private Semaphore semaphore = new Semaphore(1);  // Semáforo para controlar o acesso às chaves

    // Construtor para inicializar o recepcionista com um hotel
    public Recepcionista(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {  // Loop infinito para o recepcionista sempre estar disponível
            synchronized (hotel.getRecepcaoLock()) {  // Bloqueia o acesso à recepção para sincronização
                Hospede hospede = hotel.getNextGuestFromWaitingList();  // Pega o próximo hóspede da lista de espera
                if (hospede != null) {
                    hospede.run();  // Processa o hóspede retirado da lista de espera
                }
            }

            try {
                Thread.sleep(random.nextInt(5000)); // Espera aleatória antes de tentar alocar outro hóspede
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para receber a chave de um hóspede
    public void receiveKeyFromGuest(Hospede hospede) {
        try {
            semaphore.acquire();  // Adquire o semáforo para garantir exclusividade na operação
            Chave chaveGuest = hospede.getKey();  // Pega a chave do hóspede
            hospede.setKey(null);  // Define a chave do hóspede como nula (o hóspede entrega a chave)

            chaves.add(chaveGuest);  // Adiciona a chave à lista de chaves gerenciadas
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();  // Libera o semáforo após a operação
        }
    }

    // Método para retornar a chave para um hóspede
    public Chave returnKeyToGuest(Hospede hospede) {
        try {
            semaphore.acquire();  // Adquire o semáforo para garantir exclusividade na operação
            for (Chave chave : chaves) {
                if (chave.getQuarto().getNumeroDoQuarto() == hospede.getNumeroDoQuarto()) {  // Verifica se a chave corresponde ao quarto do hóspede
                    chaves.remove(chave);  // Remove a chave da lista
                    hospede.setKey(chave);  // Devolve a chave ao hóspede

                    return chave;  // Retorna a chave removida
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();  // Libera o semáforo após a operação
        }

        return null;  // Retorna nulo se não encontrar a chave correspondente
    }

    // Getter para o semáforo
    public Semaphore getSemaphore() {
        return semaphore;
    }
}
