import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Jogo de Xadrez ===");
        jogo.imprimirTabuleiro();

        while (true) {
            System.out.println("\nTurno: " + jogo.getTurno());
            System.out.print("Digite jogada (Ex: E2 E4), MOVS E2 para ver movimentos ou -1 para sair: ");

            String input = sc.nextLine().trim();
            if (input.equals("-1")) break;

            // Comando para listar movimentos possíveis
            if (input.toUpperCase().startsWith("MOVS")) {
                String[] partes = input.split(" ");
                if (partes.length != 2) {
                    System.out.println("Formato inválido! Use Ex: MOVS E2");
                    continue;
                }

                String pos = partes[1].toUpperCase();
                int y = pos.charAt(0) - 'A'; // coluna
                int x = 8 - Character.getNumericValue(pos.charAt(1)); // linha

                System.out.println("Movimentos possíveis para " + pos + ":");
                var movimentos = jogo.possiveisMovimentos(x, y);

                if (movimentos.isEmpty()) {
                    System.out.println("Nenhum movimento válido encontrado.");
                } else {
                    for (String mov : movimentos) {
                        // Converter coordenadas (x,y) para notação tipo "E4"
                        String coluna = String.valueOf((char) ('A' + Integer.parseInt(mov.substring(3, 4))));
                        int linha = 8 - Integer.parseInt(mov.substring(1, 2));
                        System.out.print(coluna + linha + " ");
                    }
                    System.out.println();
                }
                continue;
            }

            // Comando de jogada normal: "E2 E4"
            String[] partes = input.split(" ");
            if (partes.length != 2) {
                System.out.println("Formato inválido! Use Ex: E2 E4");
                continue;
            }

            String origem = partes[0].toUpperCase();
            String destino = partes[1].toUpperCase();

            // Origem
            int yO = origem.charAt(0) - 'A';        // Coluna
            int xO = 8 - Character.getNumericValue(origem.charAt(1)); // Linha

            // Destino
            int yD = destino.charAt(0) - 'A';
            int xD = 8 - Character.getNumericValue(destino.charAt(1));

            String resultado = jogo.mover(xO, yO, xD, yD);
            System.out.println(resultado);

            jogo.imprimirTabuleiro();
            jogo.imprimirHistorico();
            jogo.calcularEExibirPontuacao();
        }

        sc.close();
        System.out.println("Jogo encerrado.");
    }
}