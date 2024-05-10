import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        final int NUMERO_DE_QUARTOS = 10;
        final int NUMERO_DE_HOSPEDES = 50;
        final int NUMERO_DE_CAMAREIRAS = 10;
        final int NUMERO_DE_RECEPCIONISTAS = 5;

        Random random = new Random();
        List<Quarto> quartos = new ArrayList<>();
        List<Hospede> hospedes = new ArrayList<>();
        List<Recepcionista> recepcionistas = new ArrayList<>();
        List<Camareira> camareiras = new ArrayList<>();

        // Criar quartos
        for (int i = 0; i < NUMERO_DE_QUARTOS; i++) {
            quartos.add(new Quarto(i + 1));
        }

        // Criar recepcionistas e iniciar suas threads
        for (int i = 0; i < NUMERO_DE_RECEPCIONISTAS; i++) {
            Recepcionista recepcionista = new Recepcionista(i + 1);
            quartos.forEach(recepcionista::addQuartoDisponivel);
            recepcionistas.add(recepcionista);
            recepcionista.start();
        }

        // Criar camareiras e iniciar suas threads
        for (int i = 0; i < NUMERO_DE_CAMAREIRAS; i++) {
            Camareira camareira = new Camareira(i + 1);
            camareiras.add(camareira);
            camareira.start();
        }

        // Criar hóspedes em intervalos aleatórios
        while (hospedes.size() < NUMERO_DE_HOSPEDES) {
            int numeroDeHospedes = 1 + random.nextInt(Math.min(10, NUMERO_DE_HOSPEDES - hospedes.size()));
            for (int i = 0; i < numeroDeHospedes; i++) {
                Hospede hospede = new Hospede(hospedes.size() + 1, recepcionistas);
                hospedes.add(hospede);
                hospede.start();
            }

            int sleepTime = 1 + random.nextInt(3); // Gera um número aleatório entre 1 e 3 segundos
            try {
                TimeUnit.SECONDS.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                break; // Encerra o loop se a thread for interrompida
            }
        }

        // Esperar o término de todos os hóspedes
        for (Hospede hospede : hospedes) {
            try {
                hospede.join();
            } catch (InterruptedException e) {
                System.err.println("A thread do hóspede foi interrompida.");
                Thread.currentThread().interrupt(); // Boa prática ao lidar com InterruptedException
            }
        }

        System.out.println("Todos os hóspedes completaram suas estadias. Programa encerrando.");
    }
}
