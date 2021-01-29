import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import com.sun.jmx.snmp.Timestamp;

public class GravarFormas extends Thread {

	private String filename = "";
	private File file;
	private GUIGravarFormas guiGF;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Timestamp tsAntes, tsAgora;
	private ArrayList<Object> arrMensagens;
	private EstadoGravador estadoGravador;
	private Semaphore smp;
	private boolean endApp;
	private boolean modoReproducao;
	private RobotDesenhador robotDesenhador;

	public GravarFormas(GUIGravarFormas guiGF) {
		this.guiGF = guiGF;
		this.endApp = false;
		this.modoReproducao = false;
		arrMensagens = new ArrayList<Object>();
		estadoGravador = EstadoGravador.PARADO;
		smp = new Semaphore(0);
	}

	public String getFicheiro() {
		return filename;
	}

	public void setEstado(EstadoGravador estado) {
		estadoGravador = estado;
		smp.release();
	}

	public EstadoGravador getEstado() {
		return estadoGravador;
	}

	public boolean isModoReproducao() {
		return modoReproducao;
	}

	public void setRobot(RobotDesenhador robotDesenhador) {
		this.robotDesenhador = robotDesenhador;
	}

	public void novoFicheiro(String filename) {
		if (filename.equals(""))
			filename = "formas.txt";
		this.filename = filename;
		file = new File(filename);
	}

	public void adicionarMensagem(Mensagem m) {
		if (estadoGravador == EstadoGravador.GRAVAR) {
			tsAgora = new Timestamp(System.currentTimeMillis());
			long espera = tsAgora.getDateTime() - tsAntes.getDateTime();

			tsAntes = tsAgora;
			arrMensagens.add(m);
			guiGF.log("Guardado: " + m.toString());
			
			espera = espera == 0 ? 1 : espera;
			
			guiGF.log("Guardado: Espera = " + espera  + "\n");
			arrMensagens.add(((int) espera));
		}
	}

	public void guardarMensagens() {
		try {
			if (out == null)
				out = new ObjectOutputStream(new FileOutputStream(file));

			while (arrMensagens.size() > 0) {
				out.writeObject(arrMensagens.remove(0));
			}
			out.writeObject(null);
			out.flush();
			out.close();
			guiGF.log("MENSAGENS ESCRITAS NO FICHEIRO!\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void lerMensagens() {
		try {
			if (in == null)
				in = new ObjectInputStream(new FileInputStream(filename));
			Object o;
			int count = 0;
			while ((o = in.readObject()) != null) {

				if (count % 2 == 0) {
					Mensagem m = (Mensagem) o;
					arrMensagens.add(m);
					guiGF.log("Lido: " + m.toString());
				} else {
					int espera = (int) o;
					arrMensagens.add(espera);
					guiGF.log("Lido: Espera: " + espera);		
				}
				count++;
			}
			guiGF.log("ACABOU DE LER DO FICHEIRO!\n");
			in.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void reproduzirMensagens() {
		
		for (int i = 0; i < arrMensagens.size(); i++) {

			if (i % 2 == 0) {
				Mensagem m = (Mensagem) arrMensagens.get(i);
				if (robotDesenhador != null)
					robotDesenhador.desenhar(m);
				guiGF.log("Reproduzido: " + m);
			} else {
				try {
					int espera = (int) arrMensagens.get(i);
					guiGF.log("Reproduzido: Espera: " + espera + "\n");
					Thread.sleep(espera);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		guiGF.log("ACABOU DE REPRODUZIR!\n");
	}

	public File getFile(){
		return file;
	}

	public void setEndApp() {
		this.endApp = true;
		smp.release();
	}

	@Override
	public void run() {
		for (;;) {
			if (this.endApp)
				break;

			switch (estadoGravador) {
			case PARADO:
				try {
					smp.acquire();
					tsAntes = new Timestamp(System.currentTimeMillis());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case GRAVAR:
				break;
			case ESCREVER:
				guardarMensagens();
				estadoGravador = EstadoGravador.PARADO;
				break;
			case LER:
				lerMensagens();
				modoReproducao = true;
				estadoGravador = EstadoGravador.PARADO;
				break;
			case REPRODUZIR:
				reproduzirMensagens();
				guiGF.ligarBtnReproducao();
				estadoGravador = EstadoGravador.PARADO;
				break;
			}
		}
	}
}
