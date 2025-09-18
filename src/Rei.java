public class Rei extends Peca {

    public Rei(String cor, int x, int y) {
        super(cor, x, y);
    }

    @Override
    public boolean movimentoValido(int newX, int newY, Peca[][] board) {
        return true;
    }

    @Override
    public int getValor() {
        return 0;
    }
}