public class Peao extends Peca {

    private boolean primeiroMovimento = true;

    public Peao(String cor, int x, int y) {
        super(cor, x, y);
    }

    @Override
    public boolean movimentoValido(int newX, int newY, Peca[][] tabuleiro) {
        return false;
    }

    @Override
    public int getValor() {
        return 1;
    }
}