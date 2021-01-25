import Buffer.BufferCircularGUI;
import Buffer.BufferCircularMonitores;
import Buffer.IBufferCircular;
import Buffer.IBufferCircularGUI;

import java.awt.Color;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Semaphore;
import javax.swing.border.EtchedBorder;


public class GUI extends JFrame implements IGUI{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8502496328362365351L;
	private JTextField textFieldNomeRobot;
    private JCheckBox chckbxIniciaRobot;
    private JButton btnCriaBufferEServidor;
    private JButton btnGUIDesenhador;
    private JButton btnQuadrado;
    private JButton btnCirculo;
    private ButtonGroup group;
    private JRadioButton rdbtnCurvarEsq;
    private JRadioButton rdbtnCurvarDireita;
    private JSpinner spinnerLado;
    private JSpinner spinnerRaio;
    private JSpinner spinnerDeltaAng;

    private ServidorDoRobot servidorDoRobot;
    private RobotDesenhador robotDesenhador;
    private ClienteDoRobot clienteDoRobot;
    private GUIServidor guiRD;
    private GUIGravarFormas guiSpy;
    private DesenharQuadrado quadrado;
    private DesenharCirculo circulo;
    private EspacarFormasGeometricas espacamento;

    private boolean endApp = false;
    private boolean toggleGUISpy = false;
    private boolean ladoEsq;
    private boolean acabouDesenho;
    private IBufferCircularGUI guiBuffer;
    private IBufferCircular<Mensagem> buffer;
    private final static int MAX_BUFFER_SIZE = 16;
    private int dist = 0;
    private Semaphore mutex;

    public GUI() {
        initialize();
        myInit();
    }

    private void initialize() {
        setTitle("Trabalho 2");
        setResizable(false);
        getContentPane().setLayout(null);

        btnCriaBufferEServidor = new JButton("Criar buffer e servidor");
        btnCriaBufferEServidor.addActionListener(e -> {
            handleCriarBuffer();
            handleCreateServer();
        });
        btnCriaBufferEServidor.setBounds(282, 203, 290, 29);
        getContentPane().add(btnCriaBufferEServidor);

        btnGUIDesenhador = new JButton("Mostrar GUI Robot Desenhador");
        btnGUIDesenhador.addActionListener(e -> {
            handleGUIDesenhador();
        });
        btnGUIDesenhador.setBounds(282, 239, 290, 29);
        getContentPane().add(btnGUIDesenhador);

        JLabel lblNomeRobot = new JLabel("Nome do Robot");
        lblNomeRobot.setBounds(282, 9, 125, 16);
        getContentPane().add(lblNomeRobot);

        textFieldNomeRobot = new JTextField("EV1");
        textFieldNomeRobot.setBounds(282, 35, 290, 26);
        getContentPane().add(textFieldNomeRobot);
        textFieldNomeRobot.setColumns(10);

        JPanel panelComportamentos = new JPanel();
        panelComportamentos.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
                "Comportamentos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelComportamentos.setBounds(16, 11, 249, 260);
        getContentPane().add(panelComportamentos);
        panelComportamentos.setLayout(null);

        btnQuadrado = new JButton("Desenhar Quadrado");
        btnQuadrado.setBounds(6, 16, 237, 53);
        panelComportamentos.add(btnQuadrado);
        btnQuadrado.addActionListener(e -> handleDesenharQuadrado());

        btnCirculo = new JButton("Desenhar Circulo");
        btnCirculo.setBounds(6, 69, 237, 53);
        btnCirculo.addActionListener(e -> handleDesenharCirculo());
        panelComportamentos.add(btnCirculo);

        rdbtnCurvarEsq = new JRadioButton("Curvar Esquerda");
        rdbtnCurvarEsq.setBounds(6, 122, 131, 43);
        panelComportamentos.add(rdbtnCurvarEsq);

        rdbtnCurvarDireita = new JRadioButton("Curvar Direita");
        rdbtnCurvarDireita.setBounds(139, 122, 104, 43);
        panelComportamentos.add(rdbtnCurvarDireita);

        spinnerLado = new JSpinner();
        spinnerLado.setModel(new SpinnerNumberModel(30, 20, 60, 1));
        spinnerLado.setBounds(55, 180, 51, 20);
        panelComportamentos.add(spinnerLado);

        spinnerRaio = new JSpinner();
        spinnerRaio.setModel(new SpinnerNumberModel(35, 0, 360, 1));
        spinnerRaio.setBounds(142, 180, 51, 20);
        panelComportamentos.add(spinnerRaio);

        JLabel lblLado = new JLabel("Lado");
        lblLado.setHorizontalAlignment(SwingConstants.CENTER);
        lblLado.setBounds(55, 161, 51, 17);
        panelComportamentos.add(lblLado);

        JLabel lblRaio = new JLabel("Raio");
        lblRaio.setHorizontalAlignment(SwingConstants.CENTER);
        lblRaio.setBounds(142, 161, 51, 17);
        panelComportamentos.add(lblRaio);

        JLabel ajusteAnguloTxt = new JLabel("Ajustar �ngulo");
        ajusteAnguloTxt.setHorizontalAlignment(SwingConstants.CENTER);
        ajusteAnguloTxt.setBounds(6, 224, 104, 17);
        panelComportamentos.add(ajusteAnguloTxt);

        spinnerDeltaAng = new JSpinner();
        spinnerDeltaAng.setModel(new SpinnerNumberModel(0, -180, 180, 1));
        spinnerDeltaAng.setBounds(109, 219, 40, 26);
        panelComportamentos.add(spinnerDeltaAng);

        chckbxIniciaRobot = new JCheckBox("Conectar robot");
        chckbxIniciaRobot.addActionListener(e -> handleIniciarRobot());
        chckbxIniciaRobot.setBounds(282, 67, 128, 23);
        getContentPane().add(chckbxIniciaRobot);

        JButton btnSpyrobot = new JButton("Gravar Figuras");
        btnSpyrobot.addActionListener(e -> handleAtivarEspiao());
        btnSpyrobot.setBounds(282, 166, 290, 29);
        getContentPane().add(btnSpyrobot);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        setSize(600, 332);
        setVisible(true);
    }

    private void myInit() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Ask for confirmation before terminating the program.
                int option = JOptionPane.showConfirmDialog(null, "De certeza que quer fechar a aplica��o?",
                        "Confirma��o de fecho", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    handleClosing();
                }
            }
        });

        // Group the radio buttons.
        group = new ButtonGroup();
        rdbtnCurvarEsq.setSelected(true);
        rdbtnCurvarEsq.setActionCommand("0");
        rdbtnCurvarDireita.setActionCommand("1");
        group.add(rdbtnCurvarEsq);
        group.add(rdbtnCurvarDireita);
        btnGUIDesenhador.setEnabled(false);

        guiSpy = new GUIGravarFormas();
        clienteDoRobot = new ClienteDoRobot(buffer, guiSpy.getGravador());
        criarComportamentos();
    }

    private void criarComportamentos() {
        quadrado = new DesenharQuadrado(true, clienteDoRobot, this);
        circulo = new DesenharCirculo(true, clienteDoRobot, this);
        espacamento = new EspacarFormasGeometricas(dist, clienteDoRobot, this);
        mutex = new Semaphore(1);

        quadrado.start();
        circulo.start();
        espacamento.start();
    }

    public void adicionarQuadrado() {
        adicionarEspacamento();
        try {
            quadrado.setLadoEsq(ladoEsq);
            quadrado.setAngulo(90 + (int) spinnerDeltaAng.getValue());
            quadrado.ativar();
            clienteDoRobot.setUltimoComportamento(quadrado);
            acabouDesenho = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adicionarCirculo() {
        adicionarEspacamento();
        try {
            circulo.setLadoEsq(ladoEsq);
            circulo.setAngulo(360 + (int) spinnerDeltaAng.getValue());
            circulo.ativar();
            clienteDoRobot.setUltimoComportamento(circulo);
            acabouDesenho = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adicionarEspacamento() {
        if (clienteDoRobot.getUltimoComportamento() != null) {
            try {
                espacamento.setDistancia(clienteDoRobot.getUltimoComportamento().getDistancia());
                espacamento.ativar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleCriarBuffer() {
        if (textFieldNomeRobot.getText().trim().equals("")) {
            String msg = "Tem de indicar o nome do robot antes de criar o servidor.";
            JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            guiBuffer = new BufferCircularGUI(MAX_BUFFER_SIZE);
            buffer = new BufferCircularMonitores<>(MAX_BUFFER_SIZE, guiBuffer);
            clienteDoRobot.setBuffer(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            if (this.guiBuffer != null) {
                this.guiBuffer.dispose();
            }
        }
    }

    private void handleCreateServer() {
        if (textFieldNomeRobot.getText().trim().equals("")) return;
        servidorDoRobot = new ServidorDoRobot(buffer);
        servidorDoRobot.start();
        btnCriaBufferEServidor.setEnabled(false);
        btnGUIDesenhador.setEnabled(true);
    }

    private void handleIniciarRobot() {
        if (buffer == null || servidorDoRobot == null) {
            String msg = "Tem de criar o buffer e o servidor antes de iniciar o robot.";
            JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
            chckbxIniciaRobot.setSelected(false);
            return;
        }

        if (textFieldNomeRobot.getText().trim().equals("")) {
            String msg = "Tem de indicar o nome do robot antes de o conectar.";
            JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
            chckbxIniciaRobot.setSelected(false);
            return;
        }
        
        if (robotDesenhador == null) {
            robotDesenhador = new RobotDesenhador(textFieldNomeRobot.getText().trim());
            servidorDoRobot.setRobotDesenhador(robotDesenhador);
            guiSpy.getGravador().setRobot(robotDesenhador);
        }
        
        robotDesenhador.setNome(textFieldNomeRobot.getText().trim());

        if (chckbxIniciaRobot.isSelected()) {
            robotDesenhador.conectar();
            guiSpy.getGravador().setRobot(robotDesenhador);

        }
        else {
            robotDesenhador.desconectar();
        }
    }

    private void handleClosing() {
        if (robotDesenhador != null && robotDesenhador.isConectado())
            robotDesenhador.desconectar();
        if (guiRD != null)
            guiRD.dispose();
        if (guiSpy != null){
            guiSpy.dispose();
            guiSpy.getGravador().setEndApp();
        }
        if (guiBuffer != null)
            guiBuffer.dispose();
        if (servidorDoRobot != null)
            servidorDoRobot.setEnd();
        endApp = true;
    }

    private void handleDesenharCirculo() {
        if (clienteDoRobot == null) return;
        btnCirculo.setEnabled(false);
        btnQuadrado.setEnabled(false);
        setLadoEsq(rdbtnCurvarEsq.isSelected());
        setDist((int) spinnerRaio.getValue());
        adicionarCirculo();
    }

    private void handleDesenharQuadrado() {
        if (clienteDoRobot == null) return;
        btnCirculo.setEnabled(false);
        btnQuadrado.setEnabled(false);
        setLadoEsq(rdbtnCurvarEsq.isSelected());
        setDist((int) spinnerLado.getValue());
        adicionarQuadrado();
    }

    private void handleGUIDesenhador() {
    	if (robotDesenhador == null) {
            robotDesenhador = new RobotDesenhador(textFieldNomeRobot.getText().trim());
            servidorDoRobot.setRobotDesenhador(robotDesenhador);
        }
    	
        robotDesenhador.getGUI().setVisible(!toggleGUISpy);
    }

    private void handleAtivarEspiao() {
        guiSpy.mostrarGUI(!toggleGUISpy);
    }

    public void acabeiDesenho() {
        acabouDesenho = true;
    }

    public boolean acabouDesenho() {
        return acabouDesenho;
    }

    public void setLadoEsq(boolean ladoEsq) {
        this.ladoEsq = ladoEsq;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public int getDist() {
        return dist;
    }

    public Semaphore getMutex() {
        return mutex;
    }

    public void run() {	
        for (; ; ) {
            try {
                if (this.endApp) {
                    break;
                }

                Thread.sleep(100);
                if (clienteDoRobot != null && acabouDesenho()) {
                    btnQuadrado.setEnabled(true);
                    btnCirculo.setEnabled(true);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
