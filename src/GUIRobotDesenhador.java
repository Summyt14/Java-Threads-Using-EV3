import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GUIRobotDesenhador extends JFrame {
    private JTextArea textAreaRcvMsg;

    public GUIRobotDesenhador() {
        init();
        myInit();
    }

    private void init() {
        setTitle("Robot Desenhador");
        setResizable(true);
        getContentPane().setLayout(null);

        JScrollPane scrollPaneRcvMsg = new JScrollPane();
        // redimensiona aqui
        scrollPaneRcvMsg.setBounds(47, 32, 409, 435);
        getContentPane().add(scrollPaneRcvMsg);

        textAreaRcvMsg = new JTextArea();
        scrollPaneRcvMsg.setViewportView(textAreaRcvMsg);

        JButton btnClear = new JButton("Limpar");
        btnClear.addActionListener(e -> handleBtnClear());
        btnClear.setBounds(339, 500, 117, 29);
        getContentPane().add(btnClear);

        setSize(508, 600);
        setVisible(false);
        setResizable(false);
    }

    private void myInit() {
        textAreaRcvMsg.setEditable(false);
    }

    private void handleBtnClear() {
        textAreaRcvMsg.setText("");
    }

    public void log(String msg) {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
        String log = "<" + ft.format(dNow) + "> " + msg + "\n";
        textAreaRcvMsg.append(log);
    }
}
