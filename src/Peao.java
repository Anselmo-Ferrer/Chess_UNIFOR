public class Peao extends Peca {

    private boolean primeiroMovimento = true;

    public Peao(String cor, int x, int y) {
        super(cor, x, y);
    }

    @Override
    public boolean movimentoValido(int newX, int newY, Peca[][] tabuleiro) {
        int direcao = cor.equals("Branco") ? -1 : 1;

        // Movimento simples
        if (newY == this.y && newX == this.x + direcao && tabuleiro[newX][newY] == null) {
            primeiroMovimento = false;
            return true;
        }

        // Movimento duplo no primeiro lance
        if (primeiroMovimento && newY == this.y && newX == this.x + 2 * direcao
                && tabuleiro[this.x + direcao][this.y] == null
                && tabuleiro[newX][newY] == null) {
            primeiroMovimento = false;
            return true;
        }

        // Captura na diagonal
        if (Math.abs(newY - this.y) == 1 && newX == this.x + direcao) {
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