import Buffer.IBufferCircular;

public class ServidorDoRobot extends Thread {
    private IBufferCircular<Mensagem> buffer;
    private RobotDesenhador robotDesenhador;
    private boolean endApp;

    public ServidorDoRobot(IBufferCircular<Mensagem> buffer, RobotDesenhador robotDesenhador) {
        this.buffer = buffer;
        this.robotDesenhador = robotDesenhador;
        endApp = false;
    }

    public void setEnd() {
        this.endApp = true;
    }

    public void run() {
        for (; ; ) {
            try {
            	Thread.sleep(1);
                if (this.endApp)
                    break;

                Mensagem m = buffer.get();
                System.out.println("Recebi " + m);
                if (robotDesenhador.isConectado())
                    robotDesenhador.desenhar(m);
                robotDesenhador.desenharGUI(m);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
