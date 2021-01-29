
public abstract class Comportamento extends Thread {

	private static float velocidadeLinear = 22.0f;
	private boolean isLadoEsq;
	private ClienteDoRobot clienteDoRobot;
	private IGUI gui;
	private Estado estado;
	private Comportamento proxComportamento;
	private boolean endApp;

	public Comportamento(boolean isLadoEsq, ClienteDoRobot clienteDoRobot, IGUI gui) {
		this.isLadoEsq = isLadoEsq;
		this.clienteDoRobot = clienteDoRobot;
		this.gui = gui;
		estado = Estado.PARADO;
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

	public void ativar(Comportamento c) {
		proxComportamento = c;
		estado = Estado.ATIVO;
	}

	public void setLadoEsq(boolean value) {
		this.isLadoEsq = value;
	}

	public IGUI getGUI() {
		return this.gui;
	}
	
	public void setEnd(){
		this.endApp = true;
	}
	
	public Comportamento getProxComportamento() {
		return proxComportamento;
	}

	public abstract void desenho() throws InterruptedException;

	public abstract int getDistancia();

	@Override
	public void run() {
		for (;;) {
			if (endApp)
				break;			
			switch (estado) {
			case PARADO:
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case ATIVO:
				try {
					//gui.getMutex().acquire();
					desenho();
					if (!(this instanceof EspacarFormasGeometricas))
						gui.acabeiDesenho();
					//gui.getMutex().release();
					estado = Estado.PARADO;
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
}
