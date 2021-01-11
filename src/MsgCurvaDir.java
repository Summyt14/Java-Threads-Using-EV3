public class MsgCurvaDir extends Mensagem {

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
