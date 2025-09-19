public class Torre extends Peca {

    public Torre(String cor, int x, int y) {
        super(cor, x, y);
    }

    @Override
    public boolean movimentoValido(int newX, int newY, Peca[][] tabuleiro) {
        // 1. Verifica se movimento é reto (mesma linha ou mesma coluna)
        if (this.x != newX && this.y != newY) {
            return false;
        }

        // 2. Movimento horizontal (mesma linha)
        if (this.x == newX) {
            int passo = (newY > this.y) ? 1 : -1;
            for (int j = this.y + passo; j != newY; j += passo) {
                if (tabuleiro[this.x][j] != null) return false; // tem peça no caminho
            }
        }

        // 3. Movimento vertical (mesma coluna)
        if (this.y == newY) {
            int passo = (newX > this.x) ? 1 : -1;
            for (int i = this.x + passo; i != newX; i += passo) {
                if (tabuleiro[i][this.y] != null) return false; // tem peça no caminho
            }
        }

        // 4. Captura: só pode capturar se for peça de cor diferente
        if (tabuleiro[newX][newY] != null &&
                tabuleiro[newX][newY].getCor().equals(this.cor)) {
            return false;
        }

        return true;
    }

    @Override
    public int getValor() {
        return 5;
    }
}