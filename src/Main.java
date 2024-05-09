public class Main {
    public static void main(String[] args) {
        final int NUMERO_DE_QUARTOS = 10;
        final int NUMERO_DE_HOSPEDES = 50;
        final int NUMERO_DE_CAMAREIRAS = 10;
        final int NUMERO_DE_RECEPCIONISTAS = 5;

        System.out.println("Criando o hotel com a configuração definida.");
        Hotel hotel = new Hotel(NUMERO_DE_QUARTOS, NUMERO_DE_HOSPEDES, NUMERO_DE_CAMAREIRAS, NUMERO_DE_RECEPCIONISTAS);
        hotel.startSimulation();
    }
}
