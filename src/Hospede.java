import java.util.Random;

public class Hospede extends Thread {
    private int idHospede;
    private Quarto quartoAlocado;
    private boolean emPasseio;

    public Hospede(int idHospede) {
        super("Hospede-" + idHospede);
        this.idHospede = idHospede;
        this.quartoAlocado = null;
        this.emPasseio = false;
    }

    public void setQuartoAlocado(Quarto quartoAlocado) {
        this.quartoAlocado = quartoAlocado;
    }

    public Quarto getQuartoAlocado() {
        return quartoAlocado;
    }

    public void setEmPasseio(boolean emPasseio) {
        this.emPasseio = emPasseio;
    }

    public boolean isEmPasseio() {
        return emPasseio;
    }

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
        Random random = new Random();
        while (true) {
            // Simular interação com recepcionista para alocar quarto
            if (quartoAlocado == null) {
                System.out.println("Hospede " + idHospede + " está procurando um quarto...");

                // Simulando tempo para interação com recepcionista (entre 1 e 5 segundos)
                try {
                    Thread.sleep(random.nextInt(5000) + 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Simular alocar um quarto
                if (quartoAlocado == null) {
                    System.out.println("Hospede " + idHospede + " não encontrou um quarto disponível.");
                }
            }

            // Simular o hóspede saindo para passear
            try {
                Thread.sleep(random.nextInt(5000) + 5000); // Tempo de passeio (entre 5 e 10 segundos)
                setEmPasseio(true);
                System.out.println("Hospede " + idHospede + " saiu para passear.");
                Thread.sleep(random.nextInt(5000) + 5000); // Tempo de passeio
                setEmPasseio(false);
                System.out.println("Hospede " + idHospede + " voltou do passeio.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Outras lógicas de comportamento podem ser implementadas aqui
        }
    }
}
