public class RobotDesenhador {
    private RobotLegoEV3 robot;
    private String nome;
    private boolean conectado;
    private GUIRobotDesenhador guiDesenhador;

    public RobotDesenhador(String nome, GUIRobotDesenhador guiDesenhador) {
        this.nome = nome;
        this.guiDesenhador = guiDesenhador;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void desenhar(Mensagem msg) {
        if (conectado) msg.executarComando(robot);
        guiDesenhador.log(msg.toString());
    }

    public void mostrarGUI(boolean value) {
        guiDesenhador.setVisible(value);
    }

    public void conectar() {
        robot = new RobotLegoEV3();
        conectado = robot.OpenEV3(nome);
        if (conectado) {
            String msg = "Robot " + nome + " conectado!";
            guiDesenhador.log(msg);
            System.out.println(msg);
        }
    }

    public void desconectar() {
        robot.CloseEV3();
        String msg = "Robot " + nome + " desconectado!";
        guiDesenhador.log(msg);
        System.out.println(msg);
    }

    public boolean isConectado() {
        return conectado;
    }
}
