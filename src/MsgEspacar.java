import java.io.Serializable;

public class MsgEspacar extends Mensagem implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = -6693531347029306457L;

	public MsgEspacar(int distancia) {
        super(4, distancia, 0, false);
    }

    @Override
    public String toString() {
        return "Espaçamento: Lado = " + getRaio();
    }

    @Override
    public void executarComando(RobotLegoEV3 r) {
        r.Reta(getRaio());
    }
}
