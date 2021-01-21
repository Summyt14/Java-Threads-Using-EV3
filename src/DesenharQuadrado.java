public class DesenharQuadrado extends Comportamento {

    private int angulo = 91;
    private int raio = 0;
    private int dist;

    public DesenharQuadrado(boolean isLadoEsq, ClienteDoRobot clienteDoRobot, IGUI gui) {
        super(isLadoEsq, clienteDoRobot, gui);
    }

    public int getDistancia() {
        return dist;
    }

    public void desenho() {
        dist = getGUI().getDist();
        if (isLadoEsq()) {
            for (int i = 0; i < 4; i++) {
                getCliente().reta(dist);
                getCliente().parar(false);
                getCliente().curvaEsq(raio, angulo);
                getCliente().parar(false);
                setEspera(dist + 13.75f);
            }

        } else {
            for (int i = 0; i < 4; i++) {
                getCliente().reta(dist);
                getCliente().parar(false);
                getCliente().curvaDir(raio, angulo);
                getCliente().parar(false);
                setEspera(dist + 13.75f);
            }
        }
    }

    public void setAngulo(int angulo) {
        this.angulo = angulo;
    }
}
