public class RobotDesenhador {
    private RobotLegoEV3 robot;
    private GUIServidor gui;
    private String nome;
    private boolean conectado;

    public RobotDesenhador(String nome) {
        this.nome = nome;
        this.gui = new GUIServidor("Robot Desenhador");
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public GUIServidor getGUI() {
    	return gui;
    }

    public void desenhar(Mensagem msg) {
        if (conectado) msg.executarComando(robot);
        gui.log(msg.toString());
    }

    public void conectar() {
        robot = new RobotLegoEV3();
        conectado = robot.OpenEV3(nome);
        if (conectado) {
            String msg = "Robot " + nome + " conectado!";
            gui.log(msg);
            System.out.println(msg);
        }
    }

    public void desconectar() {
        robot.CloseEV3();
        String msg = "Robot " + nome + " desconectado!";
        gui.log(msg);
        System.out.println(msg);
        gui.dispose();
    }

    public boolean isConectado() {
        return conectado;
    }
}
