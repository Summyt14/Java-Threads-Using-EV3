import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import com.sun.jmx.snmp.Timestamp;

public class GravarFormas extends Thread {

	private String filename = "";
	private File file;
	private GUIGravarFormas guiGF;
	private boolean btnGravar;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Timestamp tsAntes, tsAgora;
	private ArrayList<Mensagem> arrMensagens;
	private ArrayList<Integer> esperas;
	private EstadoGravador estadoGravador;
	private Semaphore smp;
	private boolean endApp;
	private RobotDesenhador robotDesenhador;

	public GravarFormas(GUIGravarFormas guiGF) {
		this.guiGF = guiGF;
		this.endApp = false;
		arrMensagens = new ArrayList<Mensagem>();
		esperas = new ArrayList<Integer>();
		estadoGravador = EstadoGravador.PARADO;
		smp = new Semaphore(0);
	}

	public void gravar(boolean value) {
		btnGravar = value;
	}

	public String getFicheiro() {
		return filename;
	}

	public void setEstado(EstadoGravador estado) {
		estadoGravador = estado;
		smp.release();
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

		if (btnGravar) {

			if (arrMensagens.size() < 1) {
				tsAntes = new Timestamp(System.currentTimeMillis());
			}

			tsAgora = new Timestamp(System.currentTimeMillis());
			long espera = tsAgora.getDateTime() - tsAntes.getDateTime();

			if (espera >= 50) {
				guiGF.log("Espera: " + espera);
				esperas.add(((int) espera));
			} else {
				esperas.add(1);
			}

			tsAntes = tsAgora;

			arrMensagens.add(m);

			guiGF.log("Guardado: " + m.toString());
		}
	}

	public void guardarMensagens() {
		try {
			if (out == null)
				out = new ObjectOutputStream(new FileOutputStream(file));

			while (arrMensagens.size() > 0) {
				Mensagem m = arrMensagens.remove(0);
				int espera = esperas.remove(0);
				out.writeObject(m);
				out.writeObject(espera);
			}
			out.writeObject(null);
			out.flush();
			out.close();
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
					if (espera > 1) {
						esperas.add(espera);
						guiGF.log("Lido: Espera: " + espera);
					}
				}
				count++;

			}
			guiGF.log("ACABOU!");
			in.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void reproduzirMensagens() {
		int i = 0;
		while (arrMensagens.size() > 0) {
			
			if (i%2==0) {
				Mensagem m = arrMensagens.get(i);
				if (robotDesenhador != null)
					robotDesenhador.desenhar(m);
				guiGF.log("Reproduzido: " + m);

			} else {	
					try {
						int espera = esperas.get(i);
						if (robotDesenhador != null) {

						Thread.sleep(espera);
						}
						guiGF.log("Reproduzido: Espera: " + espera);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			i++;

			}
			
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
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case GRAVAR:
				guardarMensagens();
				estadoGravador = EstadoGravador.PARADO;
				break;
			case LER:
				lerMensagens();
				estadoGravador = EstadoGravador.PARADO;
				break;
			case REPRODUZIR:
				reproduzirMensagens();
				estadoGravador = EstadoGravador.PARADO;
				break;
			}
		}
	}

	public void setEndApp() {
		this.endApp = true;
	}

}
