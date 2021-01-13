import java.io.Serializable;

public abstract class Mensagem implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5453087572800218536L;
	private int tipo;
    private int raio;
    private int angulo;
    private boolean parar;

    public Mensagem(int tipo, int raio, int angulo, boolean parar) {
        this.tipo = tipo;
        this.raio = raio;
        this.angulo = angulo;
        this.parar = parar;
    }

    public abstract void executarComando(RobotLegoEV3 r);

    public int getTipo() {
        return tipo;
    }

    public int getRaio() {
        return raio;
    }

    public int getAngulo() {
        return angulo;
    }

    public boolean getParar() {
        return parar;
    }
}
