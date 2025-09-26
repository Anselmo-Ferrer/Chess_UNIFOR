import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ChessUI extends JFrame {
    private PartidaController controller;
    private JButton[][] botoesTabuleiro;
    private JTextArea historicoArea;
    private JLabel turnoLabel;
    private JLabel placarLabel;
    private JLabel ganhandoLabel;
    private String origemSelecionada = null;

    public ChessUI() {
        controller = new PartidaController();
        setTitle("Xadrez - Java Swing");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel do tabuleiro
        JPanel tabuleiroPanel = new JPanel(new GridLayout(8, 8));
        botoesTabuleiro = new JButton[8][8];

        Color green = new Color(118, 147, 84);
        Color gray = new Color(147, 147, 147);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton botao = new JButton();
                botao.setFont(new Font("Arial", Font.BOLD, 50));
                botao.setFocusPainted(false);
                botao.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                botao.setBackground((i + j) % 2 == 0 ? green : gray);
                botao.setOpaque(true);
                botao.setForeground(Color.BLACK);
                final int x = i, y = j;
                botao.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tratarClique(x, y);
                    }
                });
                botoesTabuleiro[i][j] = botao;
                tabuleiroPanel.add(botao);
            }
        }
        tabuleiroPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Painel lateral
        JPanel lateralPanel = new JPanel();
        lateralPanel.setLayout(new BorderLayout());
        lateralPanel.setPreferredSize(new Dimension(500, getHeight()));

        // Top panel for info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        turnoLabel = new JLabel("Turno: " + controller.getTurno());
        turnoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        placarLabel = new JLabel("Placar: 0 - 0");
        placarLabel.setFont(new Font("Arial", Font.BOLD, 16));
        ganhandoLabel = new JLabel("Ganhando: " + winning());
        ganhandoLabel.setFont(new Font("Arial", Font.BOLD, 16));

        infoPanel.add(turnoLabel);
        infoPanel.add(Box.createVerticalStrut(15));
        infoPanel.add(placarLabel);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(ganhandoLabel);
        infoPanel.add(Box.createVerticalStrut(20));

        // History area with titled border
        historicoArea = new JTextArea();
        historicoArea.setEditable(false);
        historicoArea.setLineWrap(true);
        historicoArea.setWrapStyleWord(true);
        historicoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollHistorico = new JScrollPane(historicoArea);
        scrollHistorico.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Histórico de movimentos",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12)));

        // Panel for history area to fill center
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.add(scrollHistorico, BorderLayout.CENTER);
        historyPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));

        // Restart button at the bottom
        JButton restartButton = new JButton("Reiniciar Jogo");
        restartButton.setFont(new Font("Arial", Font.BOLD, 16));
        restartButton.setFocusPainted(false);
        restartButton.setBackground(new Color(13, 180, 18));
        restartButton.setForeground(Color.WHITE);
        restartButton.setOpaque(true);
        restartButton.setBorderPainted(false);
        restartButton.setPreferredSize(new Dimension(230, 40));
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller = new PartidaController();
                historicoArea.setText("");
                origemSelecionada = null;
                atualizarTabuleiro();
                turnoLabel.setText("Turno: " + controller.getTurno());
                placarLabel.setText("Pontuação -> Branco: 0 | Preto: 0");
                ganhandoLabel.setText("Ganhando: " + winning());
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        bottomPanel.add(restartButton);

        lateralPanel.add(infoPanel, BorderLayout.NORTH);
        lateralPanel.add(historyPanel, BorderLayout.CENTER);
        lateralPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(tabuleiroPanel, BorderLayout.CENTER);
        add(lateralPanel, BorderLayout.EAST);

        atualizarTabuleiro();
    }

    // No início da sua classe, crie uma lista para guardar os botões destacados
    private List<JButton> botoesDestacados = new ArrayList<>();

    private void tratarClique(int x, int y) {
        String posicao = NotacaoXadrez.paraNotacao(x, y);
        JButton botao = botoesTabuleiro[x][y];

        // Se não há origem selecionada, seleciona a peça
        if (origemSelecionada == null) {
            origemSelecionada = posicao;
            botao.setBackground(Color.LIGHT_GRAY); // casa selecionada

            Color darkGreen = new Color(97, 134, 55);
            List<String> movimentos = controller.movimentosPossiveis(posicao);
            botoesDestacados.clear(); // limpa qualquer botão previamente destacado

            for (String mov : movimentos) {
                String valores = mov.replace("(", "").replace(")", "");
                String[] partes = valores.split(",");
                int xTeste = Integer.parseInt(partes[0].trim());
                int yTeste = Integer.parseInt(partes[1].trim());

                JButton botaoMovimento = botoesTabuleiro[xTeste][yTeste];
                botaoMovimento.setBackground(darkGreen);
                botoesDestacados.add(botaoMovimento); // guarda para resetar depois
            }

        } else { // se já havia origem selecionada, tenta mover
            String destino = posicao;
            String resultado = controller.moverPeca(origemSelecionada, destino);
            historicoArea.append(resultado + "\n");

            // Resetar cor da origem
            int origemX = NotacaoXadrez.deNotacaoX(origemSelecionada);
            int origemY = NotacaoXadrez.deNotacaoY(origemSelecionada);
            botoesTabuleiro[origemX][origemY].setBackground(getCorOriginal(origemX, origemY));

            // Resetar cor de todos os movimentos destacados
            for (JButton b : botoesDestacados) {
                int bx = -1, by = -1;

                // opcional: descobrir as coordenadas do botão para pegar a cor original
                outer:
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (b == botoesTabuleiro[i][j]) {
                            bx = i;
                            by = j;
                            break outer;
                        }
                    }
                }
                if (bx != -1 && by != -1) {
                    b.setBackground(getCorOriginal(bx, by));
                }
            }

            botoesDestacados.clear(); // limpa lista
            origemSelecionada = null;
            atualizarTabuleiro();
        }
    }

    private void atualizarTabuleiro() {
        Peca[][] grid = controller.getGrid();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (grid[i][j] != null) {
                    botoesTabuleiro[i][j].setText(iconeDaPeca(grid[i][j]));
                    botoesTabuleiro[i][j].setForeground(
                            grid[i][j].getCor().equals("Branco") ? Color.WHITE : Color.BLACK
                    );
                } else {
                    botoesTabuleiro[i][j].setText("");
                }
            }
        }
        turnoLabel.setText("Turno: " + controller.getTurno());
        placarLabel.setText("Pontuação -> Branco: " +
                controller.getPontosBranco() +
                " | Preto: " + controller.getPontosPreto());
        ganhandoLabel.setText("Ganhando: " + winning());
    }

    private Color getCorOriginal(int x, int y) {
        Color green = new Color(118, 147, 84);
        Color gray = new Color(147, 147, 147);
        return (x + y) % 2 == 0 ? green : gray;
    }

    private String winning() {
        if (controller.getPontosBranco() == controller.getPontosPreto()) return "Empate";
        else if (controller.getPontosBranco() > controller.getPontosPreto()) return "Branco";
        return "Preto";
    }

    private String iconeDaPeca(Peca p) {
        String classe = p.getClass().getSimpleName().toLowerCase();
        String cor = p.getCor().toLowerCase();
        if (classe.contains("rei")) return cor.startsWith("b") ? "♔" : "♚";
        if (classe.contains("dama") || classe.contains("rainha")) return cor.startsWith("b") ? "♕" : "♛";
        if (classe.contains("torre")) return cor.startsWith("b") ? "♖" : "♜";
        if (classe.contains("bispo")) return cor.startsWith("b") ? "♗" : "♝";
        if (classe.contains("cavalo")) return cor.startsWith("b") ? "♘" : "♞";
        if (classe.contains("peao")) return cor.startsWith("b") ? "♙" : "♟";
        return "?";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessUI ui = new ChessUI();
            ui.setVisible(true);
        });
    }
}