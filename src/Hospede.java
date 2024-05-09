import java.util.Random;

public class Hospede extends Thread {
    private final Hotel hotel;
    private final String nome;
    private final int tamanhoDoGrupo;
    private int hospedesSemQuarto;
    private Chave chave;
    private int numeroDoQuarto;

    // Quantidade de vezes que o hóspede tentou se hospedar em um quarto.
    private int qtdDeTentativas;

    public Hospede(Hotel hotel, String nome, int tamanhoDoGrupo) {
        this.hotel = hotel;
        this.nome = nome;
        this.tamanhoDoGrupo = tamanhoDoGrupo;
        this.hospedesSemQuarto = tamanhoDoGrupo;
        this.qtdDeTentativas = 0;
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            // Tempo aleatório de chegada do hóspede
            Thread.sleep(random.nextInt(5000));

            // Tenta alocar o hóspede em um quarto
            allocateGuestInTheRoom(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void allocateGuestInTheRoom(Random random) throws InterruptedException {
        // Procura um quarto disponível no hotel
        Quarto quarto = hotel.getQuartoDisponivel();

        if (quarto != null) {
            // Ocupa o quarto encontrado
            occupyRoom(quarto, random);

            // Verifica se ainda há hóspedes sem quarto alocado
            if (this.hospedesSemQuarto <= 0) {
                return;
            } else {
                // Tenta alocar os hóspedes restantes em outro quarto
                allocateGuestInTheRoom(random);
            }
        } else {
            // Aumenta o número de tentativas do hóspede
            this.qtdDeTentativas += 1;

            if (qtdDeTentativas >= 2) {
                // Se o número de tentativas exceder o limite, o hóspede vai embora com uma reclamação
                System.out.println("Hóspede " + this.nome + " não encontrou quartos disponíveis e saiu com uma reclamação.");
                this.hotel.getHospedes().remove(this);
                return;
            } else {
                // Adiciona o hóspede à lista de espera
                hotel.addHospedeNaListaDeEspera(this);
                System.out.println(nome + " adicionado à lista de espera.");
            }
        }
    }

    private void occupyRoom(Quarto quarto, Random random) throws InterruptedException {
        this.hospedesSemQuarto -= quarto.getCapacidade();

        // Sincroniza o acesso ao recepcionista
        synchronized (hotel.getRecepcaoLock()) {
            quarto.ocupadoEReservado();
            this.chave = new Chave(this, quarto);
            this.numeroDoQuarto = quarto.getNumeroDoQuarto();

            if (this.hospedesSemQuarto > 0) {
                System.out.println(nome + " ocupou quarto " + quarto.getNumeroDoQuarto() + " com um grupo de " + quarto.getCapacidade() + " hóspedes");
            } else {
                System.out.println(nome + " ocupou quarto " + quarto.getNumeroDoQuarto() + " com o grupo completo de " + this.tamanhoDoGrupo + " hóspedes");
            }
        }
        // Tempo de estadia no quarto
        Thread.sleep(random.nextInt(2000));

        if (random.nextBoolean()) {
            hangout(random);
        }

        // Mais um período de estadia no quarto
        Thread.sleep(random.nextInt(2000));

        // Libera o quarto
        synchronized (hotel.getRecepcaoLock()) {
            quarto.tornarDisponivel();
            System.out.println(nome + " desocupou quarto " + quarto.getNumeroDoQuarto());
        }
    }

    // Método que simula o hóspede saindo para passear
    private void hangout(Random random) {
        System.out.println(nome + " saiu do quarto...");
        int index = this.hotel.getQuartos().indexOf(this.chave.getQuarto());
        Quarto quarto = this.hotel.getQuartos().get(index);
        quarto.tornarDisponivel();

        Recepcionista r = selectReceptionist();

        r.receiveKeyFromGuest(this);

        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        r.returnKeyToGuest(this);

        System.out.println(nome + " voltou ao quarto");
        quarto.ocupadoEReservado();
    }

    // Seleciona uma recepcionista de maneira aleatória
    private Recepcionista selectReceptionist() {
        try {
            Random random = new Random();
            int i = random.nextInt(hotel.getRecepcionistas().size());
            return hotel.getRecepcionistas().get(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getNome() {
        return nome;
    }

    public int getTamanhoDoGrupo() {
        return tamanhoDoGrupo;
    }

    public void setKey(Chave chave) {
        this.chave = chave;
    }

    public Chave getKey() {
        return chave;
    }

    public int getNumeroDoQuarto() {
        return numeroDoQuarto;
    }
}
