import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// public class Recepcionista extends Thread {
//     private final int idRecepcionista;
//     private List<Quarto> quartosDisponiveis;

//     public Recepcionista(int idRecepcionista) {
//         super("Recepcionista-" + idRecepcionista); // Dando um nome mais descritivo à thread
//         this.idRecepcionista = idRecepcionista;
//         this.quartosDisponiveis = new ArrayList<>();
//     }

//     public synchronized void addQuartoDisponivel(Quarto quarto) {
//         if (!quartosDisponiveis.contains(quarto) && quarto.isChaveNaRecepcao()) {
//             quartosDisponiveis.add(quarto);
//         }
//     }

//     public synchronized boolean alocarQuarto(Hospede hospede) {
//         if (!quartosDisponiveis.isEmpty()) {
//             for (Quarto quarto : quartosDisponiveis) {
//                 if (!quarto.isOcupado() && quarto.getHospedesAtualmente() < quarto.getCapacidadeMaxima()) {
//                     quarto.setOcupado(true);  // Definindo o quarto como ocupado
//                     hospede.setQuartoAlocado(quarto);
//                     quartosDisponiveis.remove(quarto);
//                     return true;
//                 }
//             }
//         }
//         return false;
//     }

//     @Override
//     public String toString() {
//         return String.format("Recepcionista[ID=%d, Quartos Disponíveis=%d]", idRecepcionista, quartosDisponiveis.size());
//     }

//     @Override
//     public void run() {
//         while (true) {
//             // Implementar lógica de alocação de quartos, ou outras tarefas relacionadas
//             try {
//                 Thread.sleep(1000); // Simulação de delay para processamento
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             }
//         }
//     }
// }

public class Recepcionista extends Thread {
    private int idRecepcionista;
    private List<Quarto> quartosDisponiveis;

    public Recepcionista(int idRecepcionista) {
        super("Recepcionista-" + idRecepcionista); // Dando um nome mais descritivo à thread
        this.idRecepcionista = idRecepcionista;
        this.quartosDisponiveis = new ArrayList<>();
    }

    public synchronized void addQuartoDisponivel(Quarto quarto) {
        if (!quartosDisponiveis.contains(quarto) && quarto.isChaveNaRecepcao()) {
            quartosDisponiveis.add(quarto);
        }
    }

    public synchronized boolean alocarQuarto(Hospede hospede) {
        if (!quartosDisponiveis.isEmpty()) {
            for (Quarto quarto : quartosDisponiveis) {
                if (!quarto.isOcupado() && quarto.isChaveNaRecepcao() && quarto.getHospedesAtualmente() < quarto.getCapacidadeMaxima()) {
                    quarto.setOcupado(true);  // Definindo o quarto como ocupado
                    quarto.setChaveNaRecepcao(false); // Retirando a chave da recepção
                    hospede.setQuartoAlocado(quarto);
                    quartosDisponiveis.remove(quarto);
                    return true;
                }
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
        while (true) {
            // Verificar periodicamente o estado dos quartos e atualizar a lista de quartos disponíveis
            verificarQuartos();
            
            // Outras tarefas relacionadas, se necessário

            try {
                Thread.sleep(1000); // Simulação de delay para processamento
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void verificarQuartos() {
    Iterator<Quarto> iterator = quartosDisponiveis.iterator();
    while (iterator.hasNext()) {
        Quarto quarto = iterator.next();
        if (quarto.isOcupado() || !quarto.isChaveNaRecepcao() || quarto.getHospedesAtualmente() >= quarto.getCapacidadeMaxima()) {
            iterator.remove(); // Remover quarto da lista se não estiver disponível
        }
    }
}

}
