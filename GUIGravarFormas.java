import javax.swing.*;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GUIGravarFormas extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3798051427778409493L;
	private JMenuItem menuAbrir;
    private JMenuItem menuNovo;
    private JTextArea txtAreaMsgGravadas;
    private JToggleButton tglBtnGravacao;
    private JButton btnReproducao;

    private GravarFormas gravador;
    private String filename = "";
    private JButton btnLer;

    public GUIGravarFormas() {
        init();
        myInit();
    }

    private void init() {
        setTitle("Gravar Formas");
        setResizable(false);
        getContentPane().setLayout(null);


        tglBtnGravacao = new JToggleButton("Iniciar/Parar Grava\u00E7\u00E3o");
        tglBtnGravacao.setBounds(150, 29, 178, 34);
        tglBtnGravacao.addActionListener(e -> handleGravacao());
        getContentPane().add(tglBtnGravacao);

        JScrollPane scrollPaneRcvMsg = new JScrollPane();
        scrollPaneRcvMsg.setBounds(40, 171, 394, 189);
        getContentPane().add(scrollPaneRcvMsg);

        txtAreaMsgGravadas = new JTextArea();
        scrollPaneRcvMsg.setViewportView(txtAreaMsgGravadas);
        txtAreaMsgGravadas.setEditable(false);

        JLabel mensagensGravadas = new JLabel("Logs");
        mensagensGravadas.setBounds(40, 148, 164, 13);
        getContentPane().add(mensagensGravadas);
        
        btnReproducao = new JButton("Iniciar Reprodu��o");
        btnReproducao.addActionListener(e -> handleReproducao());
        btnReproducao.setEnabled(false);
        btnReproducao.setBounds(150, 110, 178, 34);
        getContentPane().add(btnReproducao);
        
        btnLer = new JButton("Ler ficheiro");
        btnLer.addActionListener(e -> handleLeitura());
        btnLer.setEnabled(false);
        btnLer.setBounds(150, 69, 178, 34);
        getContentPane().add(btnLer);

        setSize(493, 450);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuFicheiro = new JMenu("Ficheiro");
        menuBar.add(menuFicheiro);

        menuAbrir = new JMenuItem("Abrir");
        menuAbrir.addActionListener(e -> handleAbrir());
        menuFicheiro.add(menuAbrir);

        menuNovo = new JMenuItem("Novo");
        menuNovo.addActionListener(e -> handleNovo());
        menuFicheiro.add(menuNovo);
        setVisible(false);
        setResizable(false);

    }

    private void myInit() {
        gravador = new GravarFormas(this);
        gravador.start();
        tglBtnGravacao.setEnabled(false);
    }

    private void handleAbrir() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            filename = chooser.getSelectedFile().getAbsolutePath();
            gravador.novoFicheiro(filename);
            tglBtnGravacao.setEnabled(true);
            btnReproducao.setEnabled(true);
        }
    }

    private void handleNovo() {
        filename = JOptionPane.showInputDialog("Intruduza o nome e a extens�o do ficheiro.");
        gravador.novoFicheiro(filename);
        tglBtnGravacao.setEnabled(true);
    }

    private void handleGravacao() {
        if (tglBtnGravacao.isSelected()) {
            gravador.gravar(true);
        } else {
        	btnLer.setEnabled(true);
            gravador.gravar(false);
            gravador.setEstado(EstadoGravador.GRAVAR);
        }
    }
    
    private void handleLeitura() {
    	btnLer.setEnabled(false);     
        gravador.setEstado(EstadoGravador.LER);
    	btnReproducao.setEnabled(true);
    }

    private void handleReproducao() {
        btnReproducao.setEnabled(false);     
        gravador.setEstado(EstadoGravador.REPRODUZIR);
    	btnReproducao.setEnabled(true);

    }

    public void log(String msg) {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
        String log = "<" + ft.format(dNow) + "> " + msg + "\n";
        showRcvMsg(log);
    }

    public void showRcvMsg(String rcvd) {
		Runnable r = new Runnable() {
			@Override
			public void run() {
                txtAreaMsgGravadas.append(rcvd);
            }
		};

		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(r);
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

    public GravarFormas getGravador() {
        return gravador;
    }

    public void mostrarGUI(boolean value) {
        setVisible(value);
    }
}
