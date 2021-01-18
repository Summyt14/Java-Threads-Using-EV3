import Buffer.IBufferCircular;

public class ClienteDoRobot {

    private IBufferCircular<Mensagem> buffer;
    private Comportamento ultimoComportamento;
    private GravarFormas gf;

    public ClienteDoRobot(IBufferCircular<Mensagem> buffer, GravarFormas gf) {
        this.buffer = buffer;
        this.gf = gf;
    }

    public Comportamento getUltimoComportamento() {
        return ultimoComportamento;
    }

    public void setUltimoComportamento(Comportamento ultimoComportamento) {
        this.ultimoComportamento = ultimoComportamento;
    }

    public void reta(int dist) {
        escreverBuffer(new MsgReta(dist));
    }

    public void curvaEsq(int raio, int angulo) {
        escreverBuffer(new MsgCurvaEsq(raio, angulo));
    }

    public void curvaDir(int raio, int angulo) {
        escreverBuffer(new MsgCurvaDir(raio, angulo));
    }

    public void espacar(int dist) {
        escreverBuffer(new MsgEspacar(ultimoComportamento.getDistancia() + dist));
    }

    public void parar(boolean value) {
        escreverBuffer(new MsgParar(value));
    }

    public void escreverBuffer(Mensagem msg) {
        try {
        	// Enviar msg para o gravador
            gf.adicionarMensagem(msg);
            if (buffer == null) throw new InterruptedException();
            buffer.put(msg);
        } catch (InterruptedException e) {
            System.err.println("Problemas no buffer. Não foi enviada a mensagem " + msg);
        }
    }
    
    public void setBuffer(IBufferCircular<Mensagem> buffer) {
    	this.buffer = buffer;
    }
}
