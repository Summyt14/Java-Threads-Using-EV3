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

@SuppressWarnings("serial")
public class GUI extends JFrame {
    private JTextField textFieldNomeRobot;
    private JCheckBox chckbxIniciaRobot;
    private JButton btnCriaBufferEServidor;
    private JButton btnGUIDesenhador;
    private JButton btnQuadrado;
    private JButton btnCirculo;
    private JCheckBox chckbxRepeat;

    private String robotName;
    private ServidorDoRobot servidorDoRobot;
    private RobotDesenhador robotDesenhador;
    private ClienteDoRobot clienteDoRobot;
    private GUIRobotDesenhador guiRD;
    private boolean endApp = false;
    private boolean toggleGUIRD = false;
    private IBufferCircularGUI guiBuffer;
    private IBufferCircular<Mensagem> buffer;
    private final static int MAX_BUFFER_SIZE = 16;
    private JRadioButton rdbtnCurvarEsq;
    private ButtonGroup group;
    private JRadioButton rdbtnCurvarDireita;
    private JSpinner spinnerLado;
    private JSpinner spinnerRaio;

    private DesenharQuadrado quadrado;
    private DesenharCirculo circulo;
    private EspacarFormasGeometricas espacamento;

    private boolean ladoEsq;
    private boolean acabouDesenho;
    private int dist = 0;
    private Semaphore mutex;
    private Semaphore esperaDesenho;


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

        chckbxRepeat = new JCheckBox("Enviar automaticamente");
        chckbxRepeat.setBounds(43, 218, 169, 36);
        chckbxRepeat.addActionListener(e -> handleRepetir());

        rdbtnCurvarEsq = new JRadioButton("Curvar Esquerda");
        rdbtnCurvarEsq.setBounds(6, 122, 131, 43);
        panelComportamentos.add(rdbtnCurvarEsq);
        panelComportamentos.add(chckbxRepeat);

        rdbtnCurvarDireita = new JRadioButton("Curvar Direita");
        rdbtnCurvarDireita.setBounds(139, 122, 104, 43);
        panelComportamentos.add(rdbtnCurvarDireita);

        spinnerLado = new JSpinner();
        spinnerLado.setModel(new SpinnerNumberModel(10, 2, 50, 1));
        spinnerLado.setBounds(55, 192, 51, 20);
        panelComportamentos.add(spinnerLado);

        spinnerRaio = new JSpinner();
        spinnerRaio.setModel(new SpinnerNumberModel(35, 0, 360, 1));
        spinnerRaio.setBounds(142, 192, 51, 20);
        panelComportamentos.add(spinnerRaio);

        JLabel lblNewLabel = new JLabel("Lado");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(55, 171, 51, 17);
        panelComportamentos.add(lblNewLabel);

        JLabel lblRaio = new JLabel("Raio");
        lblRaio.setHorizontalAlignment(SwingConstants.CENTER);
        lblRaio.setBounds(142, 171, 51, 17);
        panelComportamentos.add(lblRaio);

        chckbxIniciaRobot = new JCheckBox("Conectar robot");
        chckbxIniciaRobot.addActionListener(e -> handleIniciarRobot());
        chckbxIniciaRobot.setBounds(282, 67, 128, 23);
        getContentPane().add(chckbxIniciaRobot);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        setSize(600, 332);
        setVisible(true);
    }

    private void myInit() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Ask for confirmation before terminating the program.
                int option = JOptionPane.showConfirmDialog(null, "De certeza que quer fechar a aplicação?",
                        "Confirmação de fecho", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
    }

    private void criarComportamentos() {
        quadrado = new DesenharQuadrado(true, clienteDoRobot, this);
        circulo = new DesenharCirculo(true, clienteDoRobot, this);
        espacamento = new EspacarFormasGeometricas(dist, clienteDoRobot, this);
        mutex = new Semaphore(1);

        quadrado.start();
        circulo.start();
        espacamento.start();
        esperaDesenho = new Semaphore(1);
    }

    public void adicionarQuadrado() {
        adicionarEspacamento();
        try {
            esperaDesenho.acquire();
            quadrado.setLadoEsq(ladoEsq);
            quadrado.ativar();
            clienteDoRobot.setUltimoComportamento(quadrado);
            acabouDesenho = false;
            esperaDesenho.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adicionarCirculo() {
        adicionarEspacamento();
        try {
            esperaDesenho.acquire();
            circulo.setLadoEsq(ladoEsq);
            circulo.ativar();
            clienteDoRobot.setUltimoComportamento(circulo);
            acabouDesenho = false;
            esperaDesenho.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adicionarEspacamento() {
        if (clienteDoRobot.getUltimoComportamento() != null) {
            try {
                esperaDesenho.acquire();
                espacamento.setDistancia(clienteDoRobot.getUltimoComportamento().getDistancia());
                espacamento.ativar();
                esperaDesenho.release();
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
            clienteDoRobot = new ClienteDoRobot(buffer);
            criarComportamentos();
        } catch (Exception e) {
            e.printStackTrace();
            if (this.guiBuffer != null) {
                this.guiBuffer.dispose();
            }
        }
    }

    private void handleCreateServer() {
        if (textFieldNomeRobot.getText().trim().equals("")) {
            return;
        }

        guiRD = new GUIRobotDesenhador();
        robotDesenhador = new RobotDesenhador(textFieldNomeRobot.getText().trim(), guiRD);
        servidorDoRobot = new ServidorDoRobot(buffer, robotDesenhador);
        servidorDoRobot.start();
        btnCriaBufferEServidor.setEnabled(false);
    }

    private void handleIniciarRobot() {
        if (buffer == null || servidorDoRobot == null) {
            String msg = "Tem de criar o buffer e o servidor antes de iniciar o robot.";
            JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
            chckbxIniciaRobot.setSelected(false);
            return;
        }

        if (textFieldNomeRobot.getText().trim().equals("")) {
            String msg = "Tem de indicar o nome do robot antes de o abrir.";
            JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
            chckbxIniciaRobot.setSelected(false);
            return;
        }

        robotDesenhador.setNome(textFieldNomeRobot.getText().trim());

        if (chckbxIniciaRobot.isSelected())
            robotDesenhador.conectar();
        else
            robotDesenhador.desconectar();
    }

    private void handleClosing() {
        if (robotDesenhador != null && robotDesenhador.isConectado())
            robotDesenhador.desconectar();
        if (guiRD != null)
            guiRD.dispose();
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

    private void handleRepetir() {
        if (clienteDoRobot == null) return;
        if (chckbxRepeat.isSelected()) {
            if (btnQuadrado.isEnabled()) {
//                btnQuadrado.setEnabled(false);
//                btnCirculo.setEnabled(false);
            }
        }
    }

    private void handleGUIDesenhador() {
        if (robotDesenhador == null) {
            String msg = "Tem de criar o buffer e o servidor antes de visualizar a GUI do robot desenhador.";
            JOptionPane.showMessageDialog(null, msg, "Erro", JOptionPane.ERROR_MESSAGE);
            chckbxIniciaRobot.setSelected(false);
            return;
        }

        robotDesenhador.mostrarGUI(!toggleGUIRD);
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
                    chckbxRepeat.setEnabled(true);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.run();
    }
}
