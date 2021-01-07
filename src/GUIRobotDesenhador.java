import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GUIRobotDesenhador extends JFrame {
    private JTextArea textAreaRcvMsg;

    public GUIRobotDesenhador(){
        init();
        myInit();
    }

    private void init() {
        setTitle("Robot Desenhador");
        setResizable(false);
        getContentPane().setLayout(null);

        JScrollPane scrollPaneRcvMsg = new JScrollPane();
        // redimensiona aqui
        scrollPaneRcvMsg.setBounds(47, 32, 409, 850);
        getContentPane().add(scrollPaneRcvMsg);

        textAreaRcvMsg = new JTextArea();
        scrollPaneRcvMsg.setViewportView(textAreaRcvMsg);

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(e->handleBtnClear());
        btnClear.setBounds(339, 900, 117, 29);
        getContentPane().add(btnClear);

        setSize(508, 1000);
        setVisible(true);
    }

    private void myInit() {
        textAreaRcvMsg.setEditable(false);
    }

    private void handleBtnClear() {
        textAreaRcvMsg.setText("");
    }

    public void desenhar(Mensagem msg) {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");

        String msgToLog = "";
        switch (msg.getTipo()) {
            case 0: // Reta
                msgToLog = "Reta: Lado = " + msg.getRaio();
                break;
            case 1: // Curva Esquerda
                msgToLog = "Curva Esquerda: Raio = " + msg.getRaio() + ", Angulo = " + msg.getAngulo();
                break;
            case 2: // Parar
                msgToLog = "Parar";
                break;
            case 3: // Curva Direita
                msgToLog = "Curva Direita: Raio = " + msg.getRaio() + ", Angulo = " + msg.getAngulo();
                break;
            case 4: // Espaçar
                msgToLog = "Espaçamento: Lado = " + msg.getRaio();
                break;
        }

        String log = "<" + ft.format(dNow) + "> " + msgToLog + "\n";
        textAreaRcvMsg.append(log);
    }

    static class MyRunnable implements Runnable {
        private GUIRobotDesenhador window;

        public GUIRobotDesenhador getWindow() {
            return this.window;
        }

        public void run() {
            this.window = new GUIRobotDesenhador();
        }
    }

    static void launch() {
        MyRunnable r = new MyRunnable();

        try {
            EventQueue.invokeAndWait(r);
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }

        r.getWindow().run();
    }

    private void run(){

    }
}
