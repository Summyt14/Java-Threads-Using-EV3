import java.io.Serializable;

public class MsgReta extends Mensagem implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5771700311899782645L;

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
