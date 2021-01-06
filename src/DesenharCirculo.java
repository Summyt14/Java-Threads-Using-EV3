public class DesenharCirculo extends Comportamento {

    private int raio = 35;
    private int angulo = 360;
    private GUI gui;

    public DesenharCirculo(boolean isLadoEsq, ClienteDoRobot clienteDoRobot, GUI gui) {
        super(isLadoEsq, clienteDoRobot, gui);
        this.gui = gui;
    }

    public int getDistancia() {
        return raio * 2;
    }

    public void setDistancia(int raio) {
        this.raio = raio;
    }

    public void desenho() {
        raio = gui.getDist();
        if (isLadoEsq())
            getCliente().curvaEsq(raio, angulo);
        else
            getCliente().curvaDir(raio, angulo);
        setEspera((long) (2 * Math.PI * raio));
        getCliente().parar(false);
        setEspera(10);
    }

    public void setAngulo(int angulo) {
        this.angulo = angulo;
    }
}
