public class RobotDesenhador {
    private RobotLegoEV3 robot;
    private String nome;
    private boolean conectado;
    GUIRobotDesenhador gui;

    public RobotDesenhador(String nome, GUIRobotDesenhador gui) {
        this.nome = nome;
        this.gui = gui;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void desenhar(Mensagem msg) {
        switch (msg.getTipo()) {
            case 0: case 4:
                robot.Reta(msg.getRaio());
                break;
            case 1:
                robot.CurvarEsquerda(msg.getRaio(), msg.getAngulo());
                break;
            case 2:
                robot.Parar(msg.isParar());
                break;
            case 3:
                robot.CurvarDireita(msg.getRaio(), msg.getAngulo());
                break;
        }
    }

    public void conectar() {
        robot = new RobotLegoEV3();
        conectado = robot.OpenEV3(nome);
        if (conectado) {
            System.out.println("Robot " + nome + " conectado!");
        }
    }

    public void desconectar() {
        robot.CloseEV3();
        System.out.println("Robot " + nome + " desconectado!");
    }

    public boolean isConectado() {
        return conectado;
    }

    public void mostrarGUI(boolean value) {
        gui.setVisible(value);
    }

    public void desenharGUI(Mensagem msg) { gui.desenhar(msg);}
}
