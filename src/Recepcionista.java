import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;


public class Recepcionista extends Thread {
    private final Hotel hotel;
    private List<Chave> chaves = new ArrayList<Chave> ();
    private Semaphore semaphore = new Semaphore (1);

    public Recepcionista(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            synchronized (hotel.getRecepcaoLock()) {
                Hospede hospede = hotel.getNextGuestFromWaitingList();
                if (hospede != null) {
                    hospede.run();
                }
            }

            try {
                Thread.sleep(random.nextInt(5000)); // Random wait time before next allocation attempt
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // FUNCOES RELACIONADAS AS CHAVES

    // receber a chave do hospede (simplesmente armazena na lista.
    public void receiveKeyFromGuest (Hospede hospede) {
        try {
            semaphore.acquire();
            Chave chaveGuest = hospede.getKey();
            hospede.setKey(null);

            chaves.add(chaveGuest);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    // retornar chaves para o hospede
    public Chave returnKeyToGuest (Hospede hospede) {
        try {
            semaphore.acquire();
            for (Chave chave : chaves) {
                if (chave.getQuarto().getNumeroDoQuarto() == hospede.getNumeroDoQuarto()) {

                    this.chaves.remove(chave);
                    hospede.setKey(chave);


                    return chave;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }

        return null;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}