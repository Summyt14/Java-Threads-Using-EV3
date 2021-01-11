public class MsgEspacar extends Mensagem {

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
