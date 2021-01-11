public class MsgReta extends Mensagem{

	public MsgReta(int distancia) {
		super(0, distancia,0 , false);
	}

	@Override
	public String toString() {
		return "Reta: Lado = " + getRaio();
	}

	@Override
	public void executarComando(RobotLegoEV3 r) {
		r.Reta(getRaio());
	}
}
