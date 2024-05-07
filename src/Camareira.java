import java.util.List;
import java.util.ArrayList;

public class Camareira extends Thread {
    private int idCamareira;
    private boolean disponivel;
    private List<Quarto> quartosParaLimpar;


    public Camareira(int idCamareira) {
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
    public void run() {
        while (true) {
            try {
                if (!quartosParaLimpar.isEmpty() && disponivel) {
                    Quarto quarto = quartosParaLimpar.remove(0); // Assume que a camareira pega o primeiro quarto na lista
                    // Simula o processo de limpeza
                    Thread.sleep(1000); // Simular o tempo de limpeza
                    quarto.setChaveNaRecepcao(true); // Devolver a chave para a recepção
                    setDisponivel(true);
                }
                Thread.sleep(500); // Pequena pausa
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
