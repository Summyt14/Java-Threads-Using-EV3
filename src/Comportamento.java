import java.util.concurrent.Semaphore;

public abstract class Comportamento extends Thread {

	private static float velocidadeLinear = 22.0f;
	private boolean isLadoEsq;
	private ClienteDoRobot clienteDoRobot;
	private IGUI gui;
	private Semaphore smpEstado;

	public Comportamento(boolean isLadoEsq, ClienteDoRobot clienteDoRobot, IGUI gui) {
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

	public void setEspera(float raioOrDist) {
		try {
			Thread.sleep((long) ((raioOrDist / velocidadeLinear) * 1000.0f));
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

	public IGUI getGUI(){
		return this.gui;
	}

	public abstract void desenho() throws InterruptedException;

	public abstract int getDistancia();

	@Override
	public void run() {
		for (;;) {
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
