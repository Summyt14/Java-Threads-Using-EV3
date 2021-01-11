public class MsgParar extends Mensagem{

	public MsgParar(boolean parar) {
		super(2, 0, 0, parar);
	}

	@Override
	public String toString() {
		return "Parar";
	}

	@Override
	public void executarComando(RobotLegoEV3 r) {
		r.Parar(getParar());
	}
}
