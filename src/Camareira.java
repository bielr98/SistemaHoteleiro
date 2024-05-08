import java.util.ArrayList;
import java.util.List;

public class Camareira extends Thread {
    private int idCamareira;
    private boolean disponivel;
    private List<Quarto> quartosParaLimpar;

    public Camareira(int idCamareira) {
        super("Camareira-" + idCamareira);
        this.idCamareira = idCamareira;
        this.disponivel = true;
        this.quartosParaLimpar = new ArrayList<>();
    }

    public synchronized void adicionarQuartoParaLimpar(Quarto quarto) {
        if (!quartosParaLimpar.contains(quarto)) {
            quartosParaLimpar.add(quarto);
        }
    }

    public synchronized void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public synchronized boolean isDisponivel() {
        return disponivel;
    }

    @Override
    public String toString() {
        return String.format("Camareira[ID=%d, Disponível=%s]", idCamareira, disponivel ? "Sim" : "Não");
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (!quartosParaLimpar.isEmpty() && disponivel) {
                    Quarto quarto = quartosParaLimpar.remove(0);
                    Thread.sleep(2000); // Simular o tempo de limpeza
                    quarto.setChaveNaRecepcao(true);
                    System.out.println("Camareira " + idCamareira + " limpou o Quarto " + quarto.getNumeroDoQuarto());
                }
                Thread.sleep(1000); // Pequena pausa
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
