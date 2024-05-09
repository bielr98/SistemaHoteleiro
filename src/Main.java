import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Variáveis conforme o comando do exercício
        final int NUMERO_DE_QUARTOS = 10;
        final int NUMERO_DE_HOSPEDES = 50;
        final int NUMERO_DE_CAMAREIRAS = 10;
        final int NUMERO_DE_RECEPCIONISTAS = 5;

        // Lista de quartos no hotel
        List<Quarto> quartos = new ArrayList<>();
        for (int i = 0; i < NUMERO_DE_QUARTOS; i++) {
            quartos.add(new Quarto(i + 1)); // Quartos numerados de 1 a 10
        }
        // Imprimir cada quarto em uma linha separada
        quartos.forEach(quarto -> System.out.println(quarto));

        // Lista de recepcionistas
        List<Recepcionista> recepcionistas = new ArrayList<>();
        for (int i = 0; i < NUMERO_DE_RECEPCIONISTAS; i++) {
            Recepcionista recepcionista = new Recepcionista(i + 1);
            quartos.forEach(recepcionista::addQuartoDisponivel);
            recepcionistas.add(recepcionista);
            recepcionista.start();
        }
        // Imprimir cada recepcionista em uma linha separada
        recepcionistas.forEach(recepcionista -> System.out.println(recepcionista));

        // Lista de camareiras
        List<Camareira> camareiras = new ArrayList<>();
        for (int i = 0; i < NUMERO_DE_CAMAREIRAS; i++) {
            Camareira camareira = new Camareira(i + 1);
            camareiras.add(camareira);
            camareira.start();
        }
        // Imprimir cada camareira em uma linha separada
        camareiras.forEach(camareira -> System.out.println(camareira));

        // Lista de hóspedes
        List<Hospede> hospedes = new ArrayList<>();
        for (int i = 0; i < NUMERO_DE_HOSPEDES; i++) {
            Hospede hospede = new Hospede(i + 1, recepcionistas);
            hospedes.add(hospede);
            hospede.start();
        }
        // Imprimir cada hóspede em uma linha separada
        hospedes.forEach(hospede -> System.out.println(hospede));

        // Aguardar o término de todos os hóspedes
        for (Hospede hospede : hospedes) {
            try {
                hospede.join(); // Espera todos os hóspedes terminarem suas atividades
            } catch (InterruptedException e) {
                System.err.println("A thread do hóspede foi interrompida.");
                Thread.currentThread().interrupt(); // Boa prática ao lidar com InterruptedException
            }
        }

        System.out.println("Todos os hóspedes completaram suas estadias. Programa encerrando.");
    }
}
