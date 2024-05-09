import java.util.Random;

public class Hospede extends Thread {
    private final Hotel hotel;  // Referência ao hotel onde o hóspede irá ficar
    private final String nome;  // Nome do hóspede
    private final int tamanhoDoGrupo;  // Tamanho do grupo do hóspede
    private int hospedesSemQuarto;  // Número de membros do grupo que ainda não foram alocados em um quarto
    private Chave chave;  // Chave do quarto atualmente ocupado pelo hóspede
    private int numeroDoQuarto;  // Número do quarto ocupado pelo hóspede

    // Quantidade de vezes que o hóspede tentou conseguir um quarto
    private int qtdDeTentativas;

    // Construtor para inicializar o hóspede com o hotel, nome e tamanho do grupo
    public Hospede(Hotel hotel, String nome, int tamanhoDoGrupo) {
        this.hotel = hotel;
        this.nome = nome;
        this.tamanhoDoGrupo = tamanhoDoGrupo;
        this.hospedesSemQuarto = tamanhoDoGrupo;  // Inicializa com todos os membros do grupo sem quarto
        this.qtdDeTentativas = 0;  // Inicializa as tentativas de alocação em zero
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            // Simula um tempo de chegada aleatório para o hóspede
            Thread.sleep(random.nextInt(5000));

            // Inicia o processo de tentativa de alocação em um quarto
            allocateGuestInTheRoom(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Método sincronizado para tentar alocar o hóspede e seu grupo em quartos disponíveis
    private synchronized void allocateGuestInTheRoom(Random random) throws InterruptedException {
        // Busca por um quarto disponível no hotel
        Quarto quarto = hotel.getQuartoDisponivel();

        if (quarto != null) {
            // Se encontrou um quarto, aloca o hóspede e parte de seu grupo
            occupyRoom(quarto, random);

            // Verifica se ainda existem membros do grupo sem quarto
            if (this.hospedesSemQuarto <= 0) {
                return;
            } else {
                // Se ainda existem membros sem quarto, tenta alocar em outro quarto disponível
                allocateGuestInTheRoom(random);
            }
        } else {
            // Aumenta a quantidade de tentativas de alocação
            this.qtdDeTentativas += 1;

            // Caso exceda o limite de tentativas, o hóspede desiste e deixa o hotel
            if (qtdDeTentativas >= 2) {
                System.out.println("Hóspede " + this.nome + " não encontrou quartos disponíveis e saiu com uma reclamação.");
                this.hotel.getHospedes().remove(this);
                return;
            } else {
                // Se não excedeu o limite, o hóspede é adicionado à lista de espera
                hotel.addHospedeNaListaDeEspera(this);
                System.out.println(nome + " adicionado à lista de espera.");
            }
        }
    }

    // Método para ocupar o quarto e distribuir os membros do grupo
    private void occupyRoom(Quarto quarto, Random random) throws InterruptedException {
        this.hospedesSemQuarto -= quarto.getCapacidade();  // Reduz o número de hóspedes sem quarto pela capacidade do quarto

        // Bloqueia o acesso ao recepcionista para sincronizar a ocupação
        synchronized (hotel.getRecepcaoLock()) {
            quarto.ocupadoEReservado();  // Marca o quarto como ocupado e reservado
            this.chave = new Chave(this, quarto);  // Atribui uma chave ao hóspede
            this.numeroDoQuarto = quarto.getNumeroDoQuarto();  // Armazena o número do quarto ocupado

            if (this.hospedesSemQuarto > 0) {
                System.out.println(nome + " ocupou quarto " + quarto.getNumeroDoQuarto() + " com um grupo de " + quarto.getCapacidade() + " hóspedes");
            } else {
                System.out.println(nome + " ocupou quarto " + quarto.getNumeroDoQuarto() + " com o grupo completo de " + this.tamanhoDoGrupo + " hóspedes");
            }
        }
        // Simula o tempo que o hóspede permanece no quarto
        Thread.sleep(random.nextInt(2000));

        if (random.nextBoolean()) {
            hangout(random);  // Simula uma saída do hóspede do quarto
        }

        // Simula mais um período de estadia no quarto
        Thread.sleep(random.nextInt(2000));

        // Libera o quarto após a estadia
        synchronized (hotel.getRecepcaoLock()) {
            quarto.tornarDisponivel();  // Marca o quarto como disponível novamente
            System.out.println(nome + " desocupou quarto " + quarto.getNumeroDoQuarto());
        }
    }

    // Método que simula o hóspede saindo para passear
    private void hangout(Random random) throws InterruptedException {
        System.out.println(nome + " saiu do quarto...");
        int index = this.hotel.getQuartos().indexOf(this.chave.getQuarto());
        Quarto quarto = this.hotel.getQuartos().get(index);
        quarto.tornarDisponivel();  // Torna o quarto disponível temporariamente

        Recepcionista r = selectReceptionist();  // Seleciona um recepcionista aleatoriamente

        r.receiveKeyFromGuest(this);  // O recepcionista recebe a chave do hóspede

        Thread.sleep(random.nextInt(1000));  // Simula um tempo fora do quarto

        r.returnKeyToGuest(this);  // O recepcionista devolve a chave ao hóspede

        System.out.println(nome + " voltou ao quarto");
        quarto.ocupadoEReservado();  // O hóspede retorna e o quarto é marcado como ocupado novamente
    }

    // Método para selecionar um recepcionista aleatoriamente
    private Recepcionista selectReceptionist() {
        try {
            Random random = new Random();
            int i = random.nextInt(hotel.getRecepcionistas().size());  // Seleciona um índice aleatório da lista de recepcionistas
            return hotel.getRecepcionistas().get(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Getters e setters
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
