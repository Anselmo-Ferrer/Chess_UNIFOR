import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Jogo de Xadrez ===");
        jogo.imprimirTabuleiro();

        while (true) {
            System.out.println("\nTurno: " + jogo.getTurno());
            System.out.print("Digite jogada (Ex: E2 E4) ou -1 para sair: ");

            String input = sc.nextLine();
            if (input.equals("-1")) break;

            // Separa origem e destino
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
        }

        sc.close();
        System.out.println("Jogo encerrado.");
    }
}