public class MsgCurvaEsq extends Mensagem{

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
