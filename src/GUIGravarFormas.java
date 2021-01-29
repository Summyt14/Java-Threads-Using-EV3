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
    private JButton btnLer;
    private JButton btnEscrever;

    private GravarFormas gravador;
    private String filename = "";

    public GUIGravarFormas() {
        init();
        myInit();
    }

    private void init() {
        setTitle("Gravar Formas");
        setResizable(false);
        getContentPane().setLayout(null);


        tglBtnGravacao = new JToggleButton("Iniciar/Parar Grava\u00E7\u00E3o");
        tglBtnGravacao.setBounds(40, 30, 178, 34);
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
        
        btnReproducao = new JButton("Iniciar Reprodu\u00E7\u00E3o");
        btnReproducao.addActionListener(e -> handleReproducao());
        btnReproducao.setEnabled(false);
        btnReproducao.setBounds(40, 87, 178, 34);
        getContentPane().add(btnReproducao);
        
        btnLer = new JButton("Ler do ficheiro");
        btnLer.addActionListener(e -> handleLeitura());
        btnLer.setEnabled(false);
        btnLer.setBounds(256, 87, 178, 34);
        getContentPane().add(btnLer);
        
        btnEscrever = new JButton("Escrever no ficheiro");
        btnEscrever.addActionListener(e -> handleEscrever());
        btnEscrever.setEnabled(false);
        btnEscrever.setBounds(256, 30, 178, 34);
        getContentPane().add(btnEscrever);

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
            btnReproducao.setEnabled(false);
            btnEscrever.setEnabled(false);
            btnLer.setEnabled(true);
        }
    }

    private void handleNovo() {
        filename = JOptionPane.showInputDialog("Introduza o nome e a extens\u00E3o do ficheiro.");
        if (filename == null) return;
        gravador.novoFicheiro(filename);
        tglBtnGravacao.setEnabled(true);
    }

    private void handleGravacao() {
        if (tglBtnGravacao.isSelected()) {
        	gravador.setEstado(EstadoGravador.GRAVAR);
            btnLer.setEnabled(false);
        	btnEscrever.setEnabled(false);
        	btnReproducao.setEnabled(false);
        } else {
        	gravador.setEstado(EstadoGravador.PARADO);
        	btnEscrever.setEnabled(true);
        }
    }
    
    private void handleEscrever() {
    	gravador.setEstado(EstadoGravador.ESCREVER);
    	tglBtnGravacao.setEnabled(false);
    	btnEscrever.setEnabled(false);
		btnLer.setEnabled(true);
		btnReproducao.setEnabled(false);
	}
    
    private void handleLeitura() {
    	gravador.setEstado(EstadoGravador.LER);
    	btnLer.setEnabled(false);     
    	btnReproducao.setEnabled(true);
    	btnEscrever.setEnabled(false);
    	tglBtnGravacao.setEnabled(false);
    }

    private void handleReproducao() {
    	gravador.setEstado(EstadoGravador.REPRODUZIR);
        btnReproducao.setEnabled(false);     
    }
    
    public void ligarBtnReproducao() {
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
    
    public void handleBotaoReproduzir(boolean ativo) {
    	btnReproducao.setEnabled(ativo);
    }

    public void mostrarGUI(boolean value) {
        setVisible(value);
    }
}
