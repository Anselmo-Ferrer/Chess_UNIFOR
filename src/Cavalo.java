public class Cavalo extends Peca {

    public Cavalo(String cor, int x, int y) {
        super(cor, x, y);
    }

    @Override
    public boolean movimentoValido(int newX, int newY, Peca[][] tabuleiro) {
        return true;
    };

    @Override
    public int getValor() {
        return 5;
    }
}
