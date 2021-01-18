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

    @Override
    public void run() {
        for (; ; ) {
            try {
            	Thread.sleep(10);
                if (this.endApp)
                    break;

                robotDesenhador.desenhar(buffer.get());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
