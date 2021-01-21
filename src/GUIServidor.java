import javax.swing.*;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GUIServidor extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7852897540266445539L;
	private JTextArea textAreaRcvMsg;
    private String nome;

    public GUIServidor(String nome) {
    	this.nome = nome;
        init();
        myInit();
    }

    private void init() {
        setTitle(nome);
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

	public void showRcvMsg(String rcvd) {
		Runnable r = new Runnable() {
			@Override
			public void run() {
                textAreaRcvMsg.append(rcvd);
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
    public void log(String msg) {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
        String log = "<" + ft.format(dNow) + "> " + msg + "\n";
        showRcvMsg(log);
    }
}
