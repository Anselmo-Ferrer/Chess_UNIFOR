public class NotacaoXadrez {
    public static String paraNotacao(int x, int y) {
        String coluna = String.valueOf((char) ('A' + y));
        int linha = 8 - x;
        return coluna + linha;
    }

    public static int deNotacaoX(String posicao) {
        int linha = 8 - Character.getNumericValue(posicao.charAt(1));
        return linha;
    }

    public static int deNotacaoY(String posicao) {
        return posicao.charAt(0) - 'A';
    }
}