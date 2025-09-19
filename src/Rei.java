public class Rei extends Peca {

    public Rei(String cor, int x, int y) {
        super(cor, x, y);
    }

    @Override
    public boolean movimentoValido(int newX, int newY, Peca[][] board) {
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);

        if ((dx <= 1 && dy <= 1) && !(dx == 0 && dy == 0)) {
            if (board[newX][newY] == null || !board[newX][newY].getCor().equals(cor)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getValor() {
        return 0;
    }
}