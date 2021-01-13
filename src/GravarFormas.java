import java.io.*;
import java.util.ArrayList;

public class GravarFormas extends Thread {

	// ficheiro
	// private File ficheiro;

	private String filename = "";
	private EstadoGravador estadoGravador;
	private ArrayList<Mensagem> arrMensagens;
	private GUIGravarFormas guiGF;
	private boolean btnGravar;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public GravarFormas(GUIGravarFormas guiGF) {
		this.guiGF = guiGF;
		// ficheiro = null;
		estadoGravador = EstadoGravador.PARADO;
		arrMensagens = new ArrayList<Mensagem>();
	}

	public void gravar(boolean value) {
		btnGravar = value;
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

	public void novoFicheiro(String filename) {
		if (filename.equals(""))
			filename = "formas.txt";
		this.filename = filename;

		try {
			out = new ObjectOutputStream(new FileOutputStream(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void adicionarMensagem(Mensagem m) {
		arrMensagens.add(m);
	}

	public void guardarMensagens() {
		try {
			while (arrMensagens.size() > 0) {
				Mensagem m = arrMensagens.remove(0);
				out.writeObject(m);
				guiGF.log("Guardado: " + m.toString());
			}	
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void lerMensagens() {
		try {
			out.writeObject(null);
			System.out.println("TOU A LER");
			if (in == null)
				in = new ObjectInputStream(new FileInputStream(filename));
			arrMensagens.clear();
			Object o;
			while ((o = in.readObject()) != null) {
				Mensagem m = (Mensagem) o;
				arrMensagens.add(m);
				guiGF.log("Lido: " + m.toString());
			}
			in.close();
		} catch (IOException | ClassNotFoundException e) {
		}
	}

	@Override
	public void run() {
		for (;;) {
			switch (estadoGravador) {
			case PARADO:
				break;
			case GRAVAR: // escrever no ficheiro
				if (btnGravar)
					guardarMensagens();
				break;
			case REPRODUZIR: // ler do ficheiro
				lerMensagens();
				guiGF.acabouReproducao();
				estadoGravador = EstadoGravador.PARADO;
				break;
			}
		}
	}
}
