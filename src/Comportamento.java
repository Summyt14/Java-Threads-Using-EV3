import java.util.concurrent.Semaphore;

public abstract class Comportamento extends Thread {

    public final int velocidadeLinear = 30;
    private boolean isLadoEsq;
    private ClienteDoRobot clienteDoRobot;
    private Estado estado;
    private GUI gui;

    private Semaphore smp;

    public Comportamento(boolean isLadoEsq, ClienteDoRobot clienteDoRobot, GUI gui) {
        this.isLadoEsq = isLadoEsq;
        this.clienteDoRobot = clienteDoRobot;
        this.gui = gui;
        estado = Estado.PARADO;
        smp = new Semaphore(1);
    }

    public boolean isLadoEsq() {
        return isLadoEsq;
    }

    public ClienteDoRobot getCliente() {
        return clienteDoRobot;
    }

    public void setEspera(long raioOrDist) {
        try {
            Thread.sleep(raioOrDist * velocidadeLinear);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setLadoEsq(boolean value) { this.isLadoEsq = value; }

    public abstract void desenho();
    public abstract int getDistancia();

    // FIXME ESPETAR SEMAFOROS AQUI
    public void run() {
        for (; ; ) {
            try {
            	Thread.sleep(1);
                switch (estado) {
                    case PARADO:
                        break;
                    case ATIVO:
                        try {
                            gui.getMutex().acquire();
                            desenho();
                            gui.acabeiDesenho();
                            estado = Estado.PARADO;
                            gui.getMutex().release();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
