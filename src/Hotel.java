import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private List<Quarto> quartos;
    private List<Recepcionista> recepcionistas;
    private List<Camareira> camareiras;
    private List<Hospede> hospedes;

    public Hotel(int numeroDeQuartos, int numeroDeHospedes, int numeroDeCamareiras, int numeroDeRecepcionistas) {
        quartos = new ArrayList<>();
        recepcionistas = new ArrayList<>();
        camareiras = new ArrayList<>();
        hospedes = new ArrayList<>();

        // Inicializa todas as entidades do hotel
        inicializarQuartos(numeroDeQuartos);
        inicializarRecepcionistas(numeroDeRecepcionistas);
        inicializarCamareiras(numeroDeCamareiras);
        inicializarHospedes(numeroDeHospedes);
    }

    private void inicializarQuartos(int numeroDeQuartos) {
        for (int i = 0; i < numeroDeQuartos; i++) {
            quartos.add(new Quarto(i + 1));
            System.out.println("Quarto " + (i + 1) + " criado.");
        }
    }

    private void inicializarRecepcionistas(int numeroDeRecepcionistas) {
        for (int i = 0; i < numeroDeRecepcionistas; i++) {
            Recepcionista recepcionista = new Recepcionista(i + 1);
            quartos.forEach(recepcionista::addQuartoDisponivel);
            recepcionistas.add(recepcionista);
            System.out.println("Recepcionista " + (i + 1) + " criado e quartos disponíveis adicionados.");
        }
    }

    private void inicializarCamareiras(int numeroDeCamareiras) {
        for (int i = 0; i < numeroDeCamareiras; i++) {
            Camareira camareira = new Camareira(i + 1);
            camareiras.add(camareira);
            System.out.println("Camareira " + (i + 1) + " criada.");
        }
    }

    private void inicializarHospedes(int numeroDeHospedes) {
        for (int i = 0; i < numeroDeHospedes; i++) {
            Hospede hospede = new Hospede(i + 1);
            hospedes.add(hospede);
            System.out.println("Hóspede " + (i + 1) + " criado.");
        }
    }

    public void startSimulation() {
        System.out.println("Iniciando simulação do hotel.");
        recepcionistas.forEach(Thread::start);
        camareiras.forEach(Thread::start);
        hospedes.forEach(Thread::start);

        for (Hospede hospede : hospedes) {
            try {
                hospede.join(); // Aguarda a finalização de cada hóspede
            } catch (InterruptedException e) {
                System.err.println("A thread do hóspede foi interrompida.");
            }
        }
        System.out.println("Simulação do hotel concluída.");
    }
}
