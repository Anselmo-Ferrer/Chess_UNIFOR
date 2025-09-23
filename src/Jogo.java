import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Jogo {
    private Tabuleiro tabuleiro;
    private String turno; // "Branco" ou "Preto"
    private List<String> historico;
    private int pontosBranco = 0;
    private int pontosPreto = 0;

    public Jogo() {
        tabuleiro = new Tabuleiro();
        turno = "Branco";
        historico = new ArrayList<>();
    }

    public Tabuleiro getTabuleiro() { return tabuleiro; }
    public String getTurno() { return turno; }
    public List<String> getHistorico() { return historico; }
    public int getPontosBranco() { return pontosBranco; }
    public int getPontosPreto() { return pontosPreto; }

    // Tenta mover uma peça
    public String mover(int xOrigem, int yOrigem, int xDestino, int yDestino) {
        Peca[][] grid = tabuleiro.getGrid();
        Peca peca = grid[xOrigem][yOrigem];

        if (peca == null) return "Não há peça na posição de origem!";
        if (!peca.getCor().equals(turno)) return "Não é o turno da sua cor!";
        if (!peca.movimentoValido(xDestino, yDestino, grid)) return "Movimento inválido para essa peça!";

        Peca capturada = grid[xDestino][yDestino];

        // Move a peça
        tabuleiro.setPeca(xDestino, yDestino, peca);
        tabuleiro.setPeca(xOrigem, yOrigem, null);

        promoverPeaoSeNecessario(peca, xDestino, yDestino);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String hora = LocalDateTime.now().format(formatter);

        String registro = hora + " - " + turno + " moveu " + peca.toString() +
                " de (" + xOrigem + "," + yOrigem + ") para (" + xDestino + "," + yDestino + ")";

        if (capturada != null) {
            registro += " capturando " + capturada.toString();

            // Atualiza pontuação do adversário
            if (capturada.getCor().equals("Branco")) {
                pontosPreto += capturada.getValor();
            } else {
                pontosBranco += capturada.getValor();
            }

            // Vitória se capturou o rei
            if (capturada instanceof Rei) {
                historico.add(turno + " venceu a partida!");
                imprimirTabuleiro();
                imprimirHistorico();
                System.out.println("=== FIM DE JOGO ===");
                System.exit(0);
            }
        }

        historico.add(registro);

        // Alterna o turno
        turno = turno.equals("Branco") ? "Preto" : "Branco";

        return "Movimento realizado com sucesso!";
    }

    // Retorna todos os movimentos possíveis para uma peça
    public List<String> possiveisMovimentos(int x, int y) {
        List<String> movimentos = new ArrayList<>();
        Peca[][] grid = tabuleiro.getGrid();
        Peca peca = grid[x][y];

        if (peca == null) return movimentos;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (peca.movimentoValido(i, j, grid)) {
                    movimentos.add("(" + i + "," + j + ")");
                }
            }
        }
        return movimentos;
    }

    // Exibe a pontuação atual
    public void calcularEExibirPontuacao() {
        System.out.println("Pontuação -> Branco: " + pontosBranco + " | Preto: " + pontosPreto);
        if (pontosBranco > pontosPreto) {
            System.out.println("Quem está vencendo: Branco");
        } else if (pontosPreto > pontosBranco) {
            System.out.println("Quem está vencendo: Preto");
        } else {
            System.out.println("Empate");
        }
    }

    // Exibe o tabuleiro
    public void imprimirTabuleiro() {
        tabuleiro.imprimir();
    }

    // Exibe o histórico de movimentos
    public void imprimirHistorico() {
        System.out.println("Histórico de movimentos:");
        for (String registro : historico) {
            System.out.println(registro);
        }
    }
    private void promoverPeaoSeNecessario(Peca peca, int xDestino, int yDestino) {
        if (peca instanceof Peao) {
            boolean chegouNaUltimaLinhaBranco = peca.getCor().equals("Branco") && xDestino == 0;
            boolean chegouNaUltimaLinhaPreto = peca.getCor().equals("Preto") && xDestino == 7;

            if (chegouNaUltimaLinhaBranco || chegouNaUltimaLinhaPreto) {
                Dama novaDama = new Dama(peca.getCor(), xDestino, yDestino);
                tabuleiro.setPeca(xDestino, yDestino, novaDama);

                historico.add(turno + " promoveu um peão a dama em (" + xDestino + "," + yDestino + ")");
            }
        }
    }







}
