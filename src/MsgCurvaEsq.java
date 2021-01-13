import java.io.Serializable;

public class MsgCurvaEsq extends Mensagem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5232271624026947085L;

	public MsgCurvaEsq(int raio, int angulo) {
		super(1, raio, angulo, false);
	}

	@Override
	public String toString() {
		return "Curva Esquerda: Raio = " + getRaio() + ", Angulo = " + getAngulo();
	}

	@Override
	public void executarComando(RobotLegoEV3 r) {
		r.CurvarEsquerda(getRaio(), getAngulo());
	}
}
