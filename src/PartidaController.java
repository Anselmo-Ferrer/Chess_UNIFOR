import java.util.List;

public class PartidaController {
    private Partida partida;

    public PartidaController() {
        partida = new Partida();
    }

    public String moverPeca(String origem, String destino) {
        int yO = origem.charAt(0) - 'A';
        int xO = 8 - Character.getNumericValue(origem.charAt(1));
        int yD = destino.charAt(0) - 'A';
        int xD = 8 - Character.getNumericValue(destino.charAt(1));

        return partida.mover(xO, yO, xD, yD);
    }

    public List<String> movimentosPossiveis(String posicao) {
        int y = posicao.charAt(0) - 'A';
        int x = 8 - Character.getNumericValue(posicao.charAt(1));
        return partida.possiveisMovimentos(x, y);
    }

    public void imprimirTabuleiro() { partida.imprimirTabuleiro(); }
    public void imprimirHistorico() { partida.imprimirHistorico(); }
    public void exibirPontuacao() { partida.calcularEExibirPontuacao(); }
    public String getTurno() { return partida.getTurno(); }
}