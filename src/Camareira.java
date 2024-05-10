import java.util.ArrayList;
import java.util.List;

public class Camareira extends Thread {
    private int idCamareira;
    private List<Quarto> quartosParaLimpar;

    public Camareira(int idCamareira) {
        super("Camareira-" + idCamareira);
        this.idCamareira = idCamareira;
        this.quartosParaLimpar = new ArrayList<>();
    }

    public synchronized void adicionarQuartoParaLimpar(Quarto quarto) {
        if (!quartosParaLimpar.contains(quarto)) {
            quartosParaLimpar.add(quarto);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Quarto quarto = null;
                synchronized (this) {
                    if (!quartosParaLimpar.isEmpty()) {
                        quarto = quartosParaLimpar.remove(0); // Pega o primeiro quarto na lista
                    }
                }
                if (quarto != null) {
                    Thread.sleep(1000); // Simular o tempo de limpeza
                    synchronized (quarto) {
                        quarto.setLimpo(true);
                        quarto.setChaveNaRecepcao(true);
                    }
                    System.out.println("Quarto " + quarto.getNumeroDoQuarto() + " está limpo.");
                }
                Thread.sleep(500); // Pequena pausa entre as limpezas
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
