import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Jogo {
    private Tabuleiro tabuleiro;
    private String turno; // "Branco" ou "Preto"
    private List<String> historico;

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public String getTurno() {
        return turno;
    }

    public List<String> getHistorico() {
        return historico;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public void setHistorico(List<String> historico) {
        this.historico = historico;
    }

    public Jogo() {
        tabuleiro = new Tabuleiro();
        turno = "Branco";
        historico = new ArrayList<>();
    }

    // Tenta mover uma peça; retorna mensagem de sucesso ou erro
    public String mover(int xOrigem, int yOrigem, int xDestino, int yDestino) {
        Peca[][] grid = tabuleiro.getGrid();
        Peca peca = grid[xOrigem][yOrigem];

        if (peca == null) {
            return "Não há peça na posição de origem!";
        }

        if (!peca.getCor().equals(turno)) {
            return "Não é o turno da sua cor!";
        }

        if (!peca.movimentoValido(xDestino, yDestino, grid)) {
            return "Movimento inválido para essa peça!";
        }

        // Captura (se houver)
        Peca capturada = grid[xDestino][yDestino];

        // Move a peça
        tabuleiro.setPeca(xDestino, yDestino, peca);
        tabuleiro.setPeca(xOrigem, yOrigem, null);

        // Adiciona ao histórico
        String registro = LocalDateTime.now() + " - " + turno + " moveu " + peca.toString() +
                " de (" + xOrigem + "," + yOrigem + ") para (" + xDestino + "," + yDestino + ")";
        if (capturada != null) {
            registro += " capturando " + capturada.toString();
        }
        historico.add(registro);

        // Alterna o turno
        turno = turno.equals("Branco") ? "Preto" : "Branco";

        return "Movimento realizado com sucesso!";
    }

    public void imprimirTabuleiro() {
        tabuleiro.imprimir();
    }

    public void imprimirHistorico() {
        System.out.println("Histórico de movimentos:");
        for (String registro : historico) {
            System.out.println(registro);
        }
    }
}