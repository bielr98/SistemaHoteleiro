public class Camareira extends Thread {
    private final Hotel hotel;

    public Camareira(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (hotel.getCamareiraLock()) {
                synchronized (hotel.getRecepcaoLock()) {
                    for (Quarto quarto : hotel.getQuartos()) {
                        if (!quarto.getOcupado() && !quarto.estaLimpo()) {
                            quarto.tornarLimpo();
                            System.out.println("Camareira Limpou o quarto " + quarto.getNumeroDoQuarto());
                        }
                    }
                }
            }
        }
    }
}