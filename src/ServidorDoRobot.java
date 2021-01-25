import Buffer.IBufferCircular;

public class ServidorDoRobot extends Thread {
	private IBufferCircular<Mensagem> buffer;
	private boolean endApp;
    private GUIServidor guiServidor;
    private RobotDesenhador robotDesenhador;

	public ServidorDoRobot(IBufferCircular<Mensagem> buffer) {
		this.buffer = buffer;
		this.guiServidor = new GUIServidor("Servidor");
		guiServidor.setVisible(true);
		this.robotDesenhador = null;
		endApp = false;
	}

	public void setEnd() {
		this.endApp = true;
		guiServidor.dispose();
	}
	
	public void setRobotDesenhador(RobotDesenhador robotDesenhador) {
		this.robotDesenhador = robotDesenhador;
	}

    public void mostrarGUI(boolean value) {
    	guiServidor.setVisible(value);
    }

	@Override
	public void run() {
		for (;;) {
			try {
				Thread.sleep(10);
				if (this.endApp)
					break;

				Mensagem m = buffer.get();
				guiServidor.log("Buffer: " + m.toString());
				
				if (robotDesenhador != null) {
					robotDesenhador.desenhar(m);
					guiServidor.log("Robot Desenhador: " + m.toString());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
