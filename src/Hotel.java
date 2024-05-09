import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Hotel {
    //Variaveis Conforme o comando do exercício
    private static final int NUMERO_DE_QUARTOS = 10;
    private static final int NUMERO_DE_HOSPEDES = 50;
    private static final int NUMERO_DE_CAMAREIRAS = 10;
    private static final int NUMERO_DE_RECEPCIONISTAS = 5;

    private final List<Quarto> quartos = new ArrayList<>();
    private final List<Hospede> hospedes = new ArrayList<>();
    private final List<Camareira> camareiras = new ArrayList<>();
    private final List<Recepcionista> recepcionistas = new ArrayList<>();
    private final Queue<Hospede> listaDeEspera = new LinkedList<>();

    private final Object recepcaoLock = new Object();
    private final Object camareiraLock = new Object();
    private final Object hospedeLock = new Object();

    public Hotel() {
        // Inicia quartos de acordo com o Numero de quartos total
        for (int i = 0; i < NUMERO_DE_QUARTOS; i++) {
            quartos.add(new Quarto(i + 1));
        }

        // Inicia camareiras de acordo com o Numero de camareiras total
        for (int i = 0; i < NUMERO_DE_CAMAREIRAS; i++) {
            camareiras.add(new Camareira(this));
        }

        // Inicia recepcionistas
        for (int i = 0; i < NUMERO_DE_RECEPCIONISTAS; i++) {
            recepcionistas.add(new Recepcionista(this));
        }

        // Inicia Hospedes
        for (int i = 0; i < NUMERO_DE_HOSPEDES; i++) {
            hospedes.add(new Hospede(this, "Hospede " + i, 1 + (int) (Math.random() * 12))); // Group size between 1 and 12
        }
    }

    public Quarto getQuartoDisponivel() {
        synchronized (recepcaoLock) {
            for (Quarto quarto : quartos) {
                if (quarto.disponivel()) {
                    return quarto;
                }
            }
        }
        return null;
    }

//    public Quarto getAvailableRoomForGroup(int groupSize) {
//        synchronized (recepcaoLock) {
//            for (Quarto quarto : quartos) {
//                if (quarto.isAvailable() && quarto.getCapacity() >= groupSize) {
//                    return quarto;
//                }
//            }
//        }
//        return null;
//    }

    public void addHospedeNaListaDeEspera(Hospede hospede) {
        synchronized (hospedeLock) {
            listaDeEspera.add(hospede);
        }
    }

    public Hospede getNextGuestFromWaitingList() {
        synchronized (hospedeLock) {
            return listaDeEspera.poll();
        }
    }

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