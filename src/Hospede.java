import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Hospede extends Thread {
    private List<Recepcionista> recepcionistas;
    private int idHospede;
    private Quarto quartoAlocado; // Referência ao objeto Quarto
    private Random random = new Random();  // Gerador de números aleatórios para o tempo de estadia
    private boolean emPasseio;

    public Hospede(int idHospede, List<Recepcionista> recepcionistas) {
        super("Hospede-" + idHospede); // Dando um nome mais descritivo à thread
        this.recepcionistas = recepcionistas;
        this.idHospede = idHospede;
        this.quartoAlocado = null; // Inicialmente, não está alocado em nenhum quarto
        this.emPasseio = false;    // Inicialmente, não está em passeio
    }

    //Setter e Getter do Quarto
    public void setQuartoAlocado(Quarto quartoAlocado) {
        this.quartoAlocado = quartoAlocado;
    }

    public Quarto getQuartoAlocado() {
        return quartoAlocado;
    }

    //Setter e getter do emPasseio
    public void setEmPasseio(boolean emPasseio) {
        this.emPasseio = emPasseio;
    }

    public boolean isEmPasseio() {
        return emPasseio;
    }

    // getter e setter do idHospede
    public int getIdHospede() {
        return idHospede;
    }

    public void setIdHospede(int idHospede) {
        this.idHospede = idHospede;
    }

    @Override
    public String toString() {
        String quartoInfo = (quartoAlocado != null) ? String.valueOf(quartoAlocado.getNumeroDoQuarto()) : "Nenhum";
        return String.format("Hospede[ID=%d, Quarto=%s, Em Passeio=%s]",
                idHospede, quartoInfo, emPasseio ? "Sim" : "Não");
    }

    @Override
    public void run() {
        try {
            // Tentativa única de alocar um quarto
            while (quartoAlocado == null) {
                System.out.println("Hóspede " + idHospede + " está tentando alocar um quarto...");
                for (Recepcionista recepcionista : recepcionistas) {
                    if (recepcionista.alocarQuarto(this)) {
                        System.out.println("Hóspede " + idHospede + " alocou o quarto " + quartoAlocado.getNumeroDoQuarto());
                        break; // Saia do loop assim que um quarto for alocado
                    }
                }
                if (quartoAlocado == null) {
                    Thread.sleep(5000); // Esperar 5 segundos antes de tentar novamente
                }
            }

            // Permanecer no quarto por um tempo aleatório entre 1 e 5 segundos
            int estadia = 1 + random.nextInt(5);
            TimeUnit.SECONDS.sleep(estadia);
            System.out.println("Hóspede " + idHospede + " está saindo do quarto " + quartoAlocado.getNumeroDoQuarto() + " após " + estadia + " segundos.");

            // Liberar o quarto
            recepcionistas.get(0).liberarQuarto(quartoAlocado); // Assume o primeiro recepcionista para simplificação
            setQuartoAlocado(null);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt(); // Restaurar o status de interrupção
        } finally {
            System.out.println("Hóspede " + idHospede + " completou sua estadia e está saindo do hotel.");
        }
    }
}
