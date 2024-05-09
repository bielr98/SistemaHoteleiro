public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();

        // Start camareiras
        for (Camareira camareira : hotel.getCamareiras()) {
            new Thread(camareira).start();
        }

        // Start recepcionistas
        for (Recepcionista recepcionista : hotel.getRecepcionistas()) {
            new Thread(recepcionista).start();
        }

        // Start hospedes
        for (Hospede hospede : hotel.getHospedes()) {
            new Thread(hospede).start();
        }
    }
}