public class Bispo extends Peca{

    public Bispo(String cor, int x, int y) {
        super(cor, x, y);
    }

    @Override
    public boolean movimentoValido(int newX, int newY, Peca[][] tabuleiro) {
        return true;
    };

    @Override
    public int getValor() {
        return 3;
    }
}
