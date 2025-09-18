public abstract class Peca {
    protected String cor;
    protected int x;
    protected int y;

    public Peca(String cor, int x, int y) {
        this.cor = cor;
        this.x = x;
        this.y = y;
    }

    public String getCor() {
        return cor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

