public class Peao extends Peca {

    private boolean primeiroMovimento = true;

    public Peao(String cor, int x, int y) {
        super(cor, x, y);
    }

    @Override
    public boolean movimentoValido(int newX, int newY, Peca[][] tabuleiro) {
        int direcao = this.cor.equals("Branco") ? -1 : 1;

        int dx = newX - this.x;
        int dy = newY - this.y;

        // 1. Movimento simples para frente
        if (dy == 0 && dx == direcao && tabuleiro[newX][newY] == null) {
            primeiroMovimento = false;
            return true;
        }

        // 2. Primeiro movimento -> pode andar duas casas
        if (dy == 0 && dx == 2 * direcao && primeiroMovimento) {
            if (tabuleiro[this.x + direcao][this.y] == null && tabuleiro[newX][newY] == null) {
                primeiroMovimento = false;
                return true;
            }
        }

        // 3. Captura na diagonal
        if (Math.abs(dy) == 1 && dx == direcao) {
            if (tabuleiro[newX][newY] != null && !tabuleiro[newX][newY].getCor().equals(this.cor)) {
                primeiroMovimento = false;
                return true;
            }
        }

        return false;
    }

    @Override
    public int getValor() {
        return 1;
    }
}