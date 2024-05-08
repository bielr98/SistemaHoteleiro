import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Recepcionista extends Thread {
    private int idRecepcionista;
    private List<Quarto> quartosDisponiveis;

    public Recepcionista(int idRecepcionista) {
        super("Recepcionista-" + idRecepcionista);
        this.idRecepcionista = idRecepcionista;
        this.quartosDisponiveis = new ArrayList<>();
    }

    public synchronized void addQuartoDisponivel(Quarto quarto) {
        if (!quartosDisponiveis.contains(quarto) && quarto.isChaveNaRecepcao()) {
            quartosDisponiveis.add(quarto);
        }
    }

    public synchronized boolean alocarQuarto(Hospede hospede) {
        Iterator<Quarto> iterator = quartosDisponiveis.iterator();
        while (iterator.hasNext()) {
            Quarto quarto = iterator.next();
            if (!quarto.isOcupado() && quarto.isChaveNaRecepcao()) {
                quarto.setOcupado(true);
                hospede.setQuartoAlocado(quarto);
                iterator.remove(); // Remover o quarto da lista de quartos disponíveis
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return String.format("Recepcionista[ID=%d, Quartos Disponíveis=%d]", idRecepcionista, quartosDisponiveis.size());
    }

    @Override
    public void run() {
        // Lógica do recepcionista ao longo do tempo (pode ser implementada conforme necessário)
    }
}
