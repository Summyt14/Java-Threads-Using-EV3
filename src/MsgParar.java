import java.io.Serializable;

public class MsgParar extends Mensagem implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7578689077290986064L;

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
