import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GUIGravarFormas extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3798051427778409493L;
	private JMenuItem menuAbrir;
    private JMenuItem menuNovo;
    private JMenuItem menuVerConteudo;
    private JTextArea txtAreaMsgGravadas;
    private JToggleButton tglBtnGravacao;
    private JButton btnReproducao;

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
        tglBtnGravacao.setBounds(150, 26, 178, 34);
        tglBtnGravacao.addActionListener(e -> handleGravacao());
        getContentPane().add(tglBtnGravacao);

        btnReproducao = new JButton("Iniciar Reprodu\u00E7\u00E3o");
        btnReproducao.setBounds(150, 89, 178, 34);
        btnReproducao.addActionListener(e -> handleReproducao());
        getContentPane().add(btnReproducao);

        JScrollPane scrollPaneRcvMsg = new JScrollPane();
        scrollPaneRcvMsg.setBounds(40, 171, 394, 189);
        getContentPane().add(scrollPaneRcvMsg);

        txtAreaMsgGravadas = new JTextArea();
        scrollPaneRcvMsg.setViewportView(txtAreaMsgGravadas);
        txtAreaMsgGravadas.setEditable(false);

        JLabel mensagensGravadas = new JLabel("Logs");
        mensagensGravadas.setBounds(40, 148, 164, 13);
        getContentPane().add(mensagensGravadas);

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

        menuVerConteudo = new JMenuItem("Ver conteudo");
        menuVerConteudo.addActionListener(e -> handleVerConteudos());
        menuFicheiro.add(menuVerConteudo);
        menuVerConteudo.setEnabled(false);
        setVisible(false);
        setResizable(false);

    }

    private void myInit() {
        gravador = new GravarFormas(this);
        gravador.start();
    }

    private void handleAbrir() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            filename = chooser.getSelectedFile().getAbsolutePath();
            gravador.novoFicheiro(filename);
            menuVerConteudo.setEnabled(true);
        }
    }

    private void handleNovo() {
        filename = JOptionPane.showInputDialog("Intruduza o nome e a extensão do ficheiro.");
        gravador.novoFicheiro(filename);
    }

    private void handleVerConteudos() {

    }

    private void handleGravacao() {
        if (tglBtnGravacao.isSelected()) {
            btnReproducao.setEnabled(false);
            gravador.gravar(true);
            gravador.setEstadoGravador(EstadoGravador.GRAVAR);
        } else {           
            btnReproducao.setEnabled(true);
            gravador.gravar(false);
            gravador.setEstadoGravador(EstadoGravador.PARADO);
        }
    }

    private void handleReproducao() {
        gravador.setEstadoGravador(EstadoGravador.REPRODUZIR);
        btnReproducao.setEnabled(false);
        tglBtnGravacao.setEnabled(false);
    }

    public void log(String msg) {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
        String log = "<" + ft.format(dNow) + "> " + msg + "\n";
        txtAreaMsgGravadas.append(log);
    }

    public GravarFormas getGravador() {
        return gravador;
    }

    public void mostrarGUI(boolean value) {
        setVisible(value);
    }
    
    public void acabouReproducao() {
    	btnReproducao.setEnabled(true);
        tglBtnGravacao.setEnabled(true);
    }

}
