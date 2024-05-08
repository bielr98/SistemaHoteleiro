import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final int NUMERO_DE_QUARTOS = 10;
        final int NUMERO_DE_HOSPEDES = 50;
        final int NUMERO_DE_CAMAREIRAS = 3; // Reduzi o número de camareiras para simplificar a simulação
        final int NUMERO_DE_RECEPCIONISTAS = 2; // Reduzi o número de recepcionistas

        // Lista de quartos no hotel
        List<Quarto> quartos = new ArrayList<>();
        for (int i = 0; i < NUMERO_DE_QUARTOS; i++) {
            quartos.add(new Quarto(i + 1)); // Quartos numerados de 1 a 10
        }

        // Lista de recepcionistas
        List<Recepcionista> recepcionistas = new ArrayList<>();
        for (int i = 0; i < NUMERO_DE_RECEPCIONISTAS; i++) {
            Recepcionista recepcionista = new Recepcionista(i + 1);
            recepcionistas.add(recepcionista);
            recepcionista.start();
        }

        // Lista de camareiras
        List<Camareira> camareiras = new ArrayList<>();
        for (int i = 0; i < NUMERO_DE_CAMAREIRAS; i++) {
            Camareira camareira = new Camareira(i + 1);
            camareiras.add(camareira);
            camareira.start();
        }

        // Lista de hóspedes
        List<Hospede> hospedes = new ArrayList<>();
        for (int i = 0; i < NUMERO_DE_HOSPEDES; i++) {
            Hospede hospede = new Hospede(i + 1);
            hospedes.add(hospede);
            hospede.start();
        }

        Random random = new Random();
        // Simulação continua rodando enquanto há threads ativas
        while (true) {
            // Simular alocação de hóspedes aos quartos pelos recepcionistas
            for (Hospede hospede : hospedes) {
                if (hospede.getQuartoAlocado() == null) {
                    for (Recepcionista recepcionista : recepcionistas) {
                        if (recepcionista.alocarQuarto(hospede)) {
                            break;
                        }
                    }
                }
            }

            // Simular limpeza de quartos pelas camareiras
            for (Camareira camareira : camareiras) {
                if (camareira.isDisponivel()) {
                    for (Quarto quarto : quartos) {
                        if (quarto.isOcupado() && !quarto.isChaveNaRecepcao()) {
                            camareira.adicionarQuartoParaLimpar(quarto);
                            break;
                        }
                    }
                }
            }

            // Simular o tempo de espera entre ciclos (1 a 5 segundos)
            try {
                Thread.sleep(random.nextInt(4000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
