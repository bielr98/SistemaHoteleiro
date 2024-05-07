public class Quarto {
    private int numeroDoQuarto;
    private final int capacidadeMaxima;
    private int hospedesAtualmente;
    private boolean ocupado;
    private boolean chaveNaRecepcao;

    public Quarto(int numeroDoQuarto) {
        this.numeroDoQuarto = numeroDoQuarto; // Número do quarto é definido na criação
        this.capacidadeMaxima = 4; // Capacidade máxima é sempre 4 e não muda
        this.hospedesAtualmente = 0; // Inicia com 0 hóspedes
        this.ocupado = false; // Inicia como não ocupado
        this.chaveNaRecepcao = true; // Chave começa na recepção
    }

    // Método para definir a situação da chave
    public synchronized void setChaveNaRecepcao(boolean chaveNaRecepcao) {
        this.chaveNaRecepcao = chaveNaRecepcao;
    }


    // Método para obter a situação atual da chave
    public synchronized boolean isChaveNaRecepcao() {
        return this.chaveNaRecepcao;
    }

    // Método para verificar se o quarto está ocupado
    public synchronized boolean isOcupado() {
        return ocupado;
    }

    // Método para definir o estado ocupado do quarto
    public synchronized void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    // Método para obter o número atual de hóspedes no quarto
    public synchronized int getHospedesAtualmente() {
        return hospedesAtualmente;
    }


    /**
     * Altera a quantidade de hóspedes no quarto.
     *
     * @param numHospedes O número de hóspedes a ser adicionado ou subtraído.
     *                     Valores positivos adicionam hóspedes, valores negativos removem hóspedes.
     *                     A quantidade de hóspedes nunca excederá a capacidade máxima ou será menor que zero.
     */

    // Método para adicionar ou remover hóspedes do quarto
    public synchronized void alterarHospedes(int numHospedes) {
        this.hospedesAtualmente += numHospedes;
        // Garantir que o número de hóspedes não exceda a capacidade máxima nem seja negativo
        this.hospedesAtualmente = Math.min(this.hospedesAtualmente, capacidadeMaxima);
        this.hospedesAtualmente = Math.max(this.hospedesAtualmente, 0);
    }

    // Método para obter a capacidade máxima de hóspedes no quarto
    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }


    // Outros getters e setters conforme necessário

}
