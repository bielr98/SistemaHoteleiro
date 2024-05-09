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
                quarto.setChaveNaRecepcao(false);
                hospede.setQuartoAlocado(quarto);
                iterator.remove(); // Remove o quarto da lista de disponíveis
                return true;
            }
        }
        return false;
    }

    public synchronized void liberarQuarto(Quarto quarto) {
        if (quarto != null) {
            quarto.setOcupado(false);
            quarto.setChaveNaRecepcao(true); // Devolve a chave para a recepção
            if (!quartosDisponiveis.contains(quarto) && quarto.isChaveNaRecepcao()) {
                quartosDisponiveis.add(quarto); // Adiciona de volta à lista de quartos disponíveis
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Recepcionista[ID=%d, Quartos Disponíveis=%d]", idRecepcionista, quartosDisponiveis.size());
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(1000); // Intervalo para reduzir a carga
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
