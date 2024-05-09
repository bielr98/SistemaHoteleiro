import java.util.List;

public class Hospede extends Thread {
    private List<Recepcionista> recepcionistas;
    private int idHospede;
    private Quarto quartoAlocado; // Referência ao objeto Quarto
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

    //    getter e setter do idHospede
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
            // Tentar alocar um quarto
            while (quartoAlocado == null) {
                System.out.println("Hóspede " + idHospede + " está tentando alocar um quarto...");
                for (Recepcionista recepcionista : recepcionistas) {
                    if (recepcionista.alocarQuarto(this)) {
                        break; // Saia do loop assim que um quarto for alocado
                    }
                }
                if (quartoAlocado == null) {
                    Thread.sleep(5000); // Esperar 5 segundos antes de tentar novamente
                }
            }

            // Permanecer no quarto por 3 segundos
            Thread.sleep(3000);

            // Liberar o quarto e encerrar a thread
            System.out.println("Hóspede " + idHospede + " está saindo do quarto " + quartoAlocado.getNumeroDoQuarto());
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
