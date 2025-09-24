import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Partida {
    private Tabuleiro tabuleiro;
    private String turno; // "Branco" ou "Preto"
    private List<String> historico;
    private int pontosBranco = 0;
    private int pontosPreto = 0;

    public Partida() {
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

        // guarda o que havia no destino pra poder desfazer se necessário
        Peca capturada = grid[xDestino][yDestino];

        // --- Simula o movimento no próprio grid (faz e depois confirma) ---
        grid[xDestino][yDestino] = peca;
        grid[xOrigem][yOrigem] = null;
        int oldX = peca.getX(), oldY = peca.getY();
        peca.setX(xDestino); peca.setY(yDestino);

        // Se o movimento deixa o próprio rei em xeque, desfaz e rejeita
        if (estaEmXeque(turno)) {
            grid[xOrigem][yOrigem] = peca;
            grid[xDestino][yDestino] = capturada;
            peca.setX(oldX); peca.setY(oldY);
            return "Movimento inválido: deixa seu rei em xeque!";
        }

        // Promoção (se ocorrer, o método irá sobrescrever a peça no tabuleiro)
        promoverPeaoSeNecessario(peca, xDestino, yDestino);
        // recarrega referência (caso tenha sido promovido)
        peca = grid[xDestino][yDestino];

        // Atualiza pontuação caso tenha capturado algo
        if (capturada != null) {
            if (capturada.getCor().equals("Branco")) {
                pontosPreto += capturada.getValor();
            } else {
                pontosBranco += capturada.getValor();
            }
        }

        // Registra movimento no histórico
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String hora = LocalDateTime.now().format(formatter);
        String registro = hora + " - " + turno + " moveu " + peca.toString() +
                " de (" + xOrigem + "," + yOrigem + ") para (" + xDestino + "," + yDestino + ")";
        if (capturada != null) registro += " capturando " + capturada.toString();
        historico.add(registro);

        // Verifica se o adversário está em xeque / xeque-mate
        String adversario = turno.equals("Branco") ? "Preto" : "Branco";
        if (estaEmXequeMate(adversario)) {
            historico.add(turno + " deu xeque-mate!");
            imprimirTabuleiro();
            imprimirHistorico();
            System.out.println("=== FIM DA PARTIDA ===");
            System.exit(0);
        } else if (estaEmXeque(adversario)) {
            historico.add(turno + " deu xeque em " + adversario + "!");
            System.out.println("Xeque!");
        }

        // Alterna turno
        turno = adversario;

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

    private boolean estaEmXeque(String cor) {
        Peca[][] grid = tabuleiro.getGrid();
        int reiX = -1, reiY = -1;

        // localizar o rei
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (grid[i][j] instanceof Rei && grid[i][j].getCor().equals(cor)) {
                    reiX = i; reiY = j;
                }

        // verificar ataques, verifica se alguma peca adversaria pode atacar o rei, se puder retorna true
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                Peca p = grid[i][j];
                if (p != null && !p.getCor().equals(cor) && p.movimentoValido(reiX, reiY, grid))
                    return true;
            }

        return false;
    }

    // Verifica se é xeque-mate
    private boolean estaEmXequeMate(String cor) {
        if (!estaEmXeque(cor)) return false;

        Peca[][] grid = tabuleiro.getGrid();

        // 2 primeiros loops pra pegar todas as pecas do tabuleiro(as que nao sao nulas)
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Peca p = grid[i][j];
                if (p != null && p.getCor().equals(cor)) {
                    // 2 loops internos pra ver todos os movimentos que a peca poderia fazer
                    for (int x = 0; x < 8; x++) {
                        for (int y = 0; y < 8; y++) {
                            if (p.movimentoValido(x, y, grid)) {
                                // Simula o movimento (faz e desfaz)
                                Peca destino = grid[x][y];
                                grid[x][y] = p; grid[i][j] = null;
                                int oldX = p.getX(), oldY = p.getY();
                                p.setX(x); p.setY(y);

                                // Chama o método estaEmXeque para ver se, depois desse movimento, o rei ainda estaria em perigo.
                                boolean aindaEmXeque = estaEmXeque(cor);

                                // desfaz
                                grid[i][j] = p; grid[x][y] = destino;
                                p.setX(oldX); p.setY(oldY);

                                // Se algum movimento permite que o rei não fique em xeque, então não é xeque-mate
                                if (!aindaEmXeque) return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }





}
