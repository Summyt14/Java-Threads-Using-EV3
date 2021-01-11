import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;

public class GravarFormas extends Thread {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    // ficheiro
    private File ficheiro;

    private String filename;
    private EstadoGravador estadoGravador;
    private ArrayList<Mensagem> mensagensAEscrever;
    private GUIGravarFormas guiGF;

    public GravarFormas(GUIGravarFormas guiGF) {
        this.guiGF = guiGF;
        ficheiro = null;
        estadoGravador = EstadoGravador.PARADO;
        mensagensAEscrever = new ArrayList<Mensagem>();
    }

    public void adicionarMensagem(Mensagem m) {
        mensagensAEscrever.add(m);
    }

    public void espiar() {
        try {
            if (mensagensAEscrever.size() > 0) {
                Mensagem m = mensagensAEscrever.remove(0);
                guiGF.log(m.toString());
                out.writeObject(m.getTipo());
                out.writeObject(m.getRaio());
                out.writeObject(m.getAngulo());
                out.writeObject(m.getParar());
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reproduzir() {
        try {
            if (in == null) {
                in = new ObjectInputStream(new FileInputStream(filename));
            }

            int tipo = in.readInt();
            int raio = in.readInt();
            int angulo = in.readInt();
            boolean parar = in.readBoolean();

            Mensagem m = null;
            switch (tipo) {
                case 0:
                    m = new MsgReta(raio);
                    break;
                case 1:
                    m = new MsgCurvaEsq(raio, angulo);
                    break;
                case 2:
                    m = new MsgParar(parar);
                    break;
                case 3:
                    m = new MsgCurvaDir(raio, angulo);
                    break;
                case 4:
                    m = new MsgEspacar(raio);
                    break;
            }

            System.out.println(m.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void novoFicheiro(String filename) {
        // cria um ficheiro com o nome dado
        if (filename.equals("")) filename = "formas.dat";
        this.filename = filename;
        ficheiro = new File(filename);

        try {
            out = new ObjectOutputStream(new FileOutputStream(filename, true));
        } catch (IOException e) {
            System.err.println("Erro na criação das streams");
            e.printStackTrace();
        }
    }

    public void setEstadoGravador(EstadoGravador estado) {
        estadoGravador = estado;
    }

    public EstadoGravador getEstado() {
        return estadoGravador;
    }

    public String getFicheiro() {
        return filename;
    }

    public boolean isParado() {
        return estadoGravador == EstadoGravador.PARADO;
    }

    @Override
    public void run() {
        for (; ; ) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (estadoGravador) {
                case PARADO:
                    break;
                case GRAVAR: //escrever no ficheiro
                    espiar();
                    break;
                case REPRODUZIR: //ler do ficheiro
                    if (in == null)
                        reproduzir();
                    if (mensagensAEscrever.size() == 0)
                        estadoGravador = EstadoGravador.PARADO;

                    break;
            }
        }
    }
}