import java.util.List;

public class JogoController {
    private Jogo jogo;

    public JogoController() {
        jogo = new Jogo();
    }

    public String moverPeca(String origem, String destino) {
        int yO = origem.charAt(0) - 'A';
        int xO = 8 - Character.getNumericValue(origem.charAt(1));
        int yD = destino.charAt(0) - 'A';
        int xD = 8 - Character.getNumericValue(destino.charAt(1));

        return jogo.mover(xO, yO, xD, yD);
    }

    public List<String> movimentosPossiveis(String posicao) {
        int y = posicao.charAt(0) - 'A';
        int x = 8 - Character.getNumericValue(posicao.charAt(1));
        return jogo.possiveisMovimentos(x, y);
    }

    public void imprimirTabuleiro() { jogo.imprimirTabuleiro(); }
    public void imprimirHistorico() { jogo.imprimirHistorico(); }
    public void exibirPontuacao() { jogo.calcularEExibirPontuacao(); }
    public String getTurno() { return jogo.getTurno(); }
}