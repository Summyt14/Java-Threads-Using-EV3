import java.io.Serializable;

public class MsgCurvaDir extends Mensagem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4779784962391177392L;

	public MsgCurvaDir(int raio, int angulo) {
		super(3, raio, angulo, false);
	}

	@Override
	public String toString() {
		return "Curva Direita: Raio = " + getRaio() + ", Angulo = " + getAngulo();
	}

	@Override
	public void executarComando(RobotLegoEV3 r) {
		r.CurvarDireita(getRaio(), getAngulo());
	}
}
