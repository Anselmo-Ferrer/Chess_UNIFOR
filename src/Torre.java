public class Torre extends Peca {

    public Torre(String cor, int x, int y) {
        super(cor, x, y);
    }

    @Override
    public boolean movimentoValido(int newX, int newY, Peca[][] tabuleiro) {
        // Sai se destino fora do tabuleiro
        if (newX < 0 || newX > 7 || newY < 0 || newY > 7) return false;

        // 1. Movimento reto (linha ou coluna)
        if (this.x != newX && this.y != newY) return false;

        // 2. Movimento horizontal
        if (this.x == newX) {
            int passo = (newY > this.y) ? 1 : -1;
            for (int j = this.y + passo; j != newY; j += passo) {
                if (j < 0 || j > 7) return false; // proteção extra
                if (tabuleiro[this.x][j] != null) return false; // peça no caminho
            }
        }

        // 3. Movimento vertical
        if (this.y == newY) {
            int passo = (newX > this.x) ? 1 : -1;
            for (int i = this.x + passo; i != newX; i += passo) {
                if (i < 0 || i > 7) return false; // proteção extra
                if (tabuleiro[i][this.y] != null) return false; // peça no caminho
            }
        }

        // 4. Captura
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