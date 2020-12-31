public abstract class Mensagem {

    protected int id;
    protected int tipo;
    protected int raio;
    protected int angulo;
    protected boolean parar;

    public Mensagem(int tipo, int raio, int angulo, boolean parar) {
        this.tipo = tipo;
        this.raio = raio;
        this.angulo = angulo;
        this.parar = parar;
    }

    public int getId() {
        return id;
    }

    public int getTipo() {
        return tipo;
    }

    public int getRaio() {
        return raio;
    }

    public int getAngulo() {
        return angulo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setRaio(int raio) {
        this.raio = raio;
    }

    public void setAngulo(int angulo) {
        this.angulo = angulo;
    }

    @Override
    public String toString() {
        return this.tipo + "," + this.raio + "," + this.angulo + "," + this.parar;
    }

    public boolean isParar() {
        return parar;
    }

    public void setParar(boolean parar) {
        this.parar = parar;
    }

}
