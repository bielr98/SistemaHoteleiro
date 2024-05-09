import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//Variaveis Conforme o comando do exercício
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
        for (Quarto quarto : quartos) {
            System.out.println(quarto);
        }

// Lista de recepcionistas
        List<Recepcionista> recepcionistas = new ArrayList<>();
        for (int i = 0; i < NUMERO_DE_RECEPCIONISTAS; i++) {
            Recepcionista recepcionista = new Recepcionista(i + 1);
            quartos.forEach(recepcionista::addQuartoDisponivel);
            recepcionistas.add(recepcionista);
            recepcionista.start();
        }
// Imprimir cada Recepsionista em uma linha separada
        for (Recepcionista recepcionista : recepcionistas) {
            System.out.println(recepcionista);
        }

// Lista de camareiras
        List<Camareira> camareiras = new ArrayList<>();
        for (int i = 0; i < NUMERO_DE_CAMAREIRAS; i++) {
            Camareira camareira = new Camareira(i + 1);
            camareiras.add(camareira);
            camareira.start();
        }
// Imprimir cada Camareira em uma linha separada
        for (Camareira camareira : camareiras) {
            System.out.println(camareira);
        }

// Lista de hospedes
        List<Hospede> hospedes = new ArrayList<>();
        for (int i = 0; i < NUMERO_DE_HOSPEDES; i++) {
            Hospede hospede = new Hospede(i + 1, recepcionistas);
            hospedes.add(hospede);
            hospede.start();
        }
        for (Hospede hospede : hospedes) {
            System.out.println(hospede);
        }

// Simulação continua rodando enquanto há threads ativas
        for (Hospede hospede : hospedes) {
            try {
                hospede.join(); // Espera todos os hóspedes terminarem suas atividades
            } catch (InterruptedException e) {
                System.err.println("A thread do hóspede foi interrompida.");
            }
        }
    }

}