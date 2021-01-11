import java.util.concurrent.Semaphore;

public abstract class Comportamento extends Thread {

<<<<<<< Updated upstream
    public final int velocidadeLinear = 30;
=======
    private static int velocidadeLinear = 22;
>>>>>>> Stashed changes
    private boolean isLadoEsq;
    private ClienteDoRobot clienteDoRobot;
    private GUI gui;
    private Semaphore smpEstado;

    public Comportamento(boolean isLadoEsq, ClienteDoRobot clienteDoRobot, GUI gui) {
        this.isLadoEsq = isLadoEsq;
        this.clienteDoRobot = clienteDoRobot;
        this.gui = gui;
        smpEstado = new Semaphore(0);
    }

    public boolean isLadoEsq() {
        return isLadoEsq;
    }

    public ClienteDoRobot getCliente() {
        return clienteDoRobot;
    }

    public void setEspera(long raioOrDist) {
        try {
            Thread.sleep((raioOrDist / velocidadeLinear) * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ativar() {
        smpEstado.release();
    }

    public void setLadoEsq(boolean value) {
        this.isLadoEsq = value;
    }

    public abstract void desenho() throws InterruptedException;

    public abstract int getDistancia();

    public void run() {
        for (; ; ) {
            try {
                smpEstado.acquire();
                try {
                    gui.getMutex().acquire();
                    desenho();
                    gui.acabeiDesenho();
                    gui.getMutex().release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
