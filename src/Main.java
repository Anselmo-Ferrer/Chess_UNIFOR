import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        JogoController controller = new JogoController();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Jogo de Xadrez ===");
        controller.imprimirTabuleiro();

        while (true) {
            System.out.println("\nTurno: " + controller.getTurno());
            System.out.print("Digite jogada (Ex: E2 E4), MOVS E2 ou -1 para sair: ");
            String input = sc.nextLine().trim();
            if (input.equals("-1")) break;

            if (input.toUpperCase().startsWith("MOVS")) {
                String pos = input.split(" ")[1].toUpperCase();
                var movimentos = controller.movimentosPossiveis(pos);
                System.out.println(movimentos);
                continue;
            }

            String[] partes = input.split(" ");
            if (partes.length != 2) continue;

            String resultado = controller.moverPeca(partes[0].toUpperCase(), partes[1].toUpperCase());
            System.out.println(resultado);
            controller.imprimirTabuleiro();
            controller.imprimirHistorico();
            controller.exibirPontuacao();
        }

        sc.close();
        System.out.println("Jogo encerrado.");
    }
}