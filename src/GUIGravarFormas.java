import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class GUIGravarFormas extends JFrame {
    private JTextField textFieldSearch;
    private String fileName;
    private ButtonGroup group;
    private JCheckBox rdbtnGravarFormas;
    private JCheckBox rdbtnReproduzirFormas;
    private JRadioButton rdbtnAlterarNomeFile;
    private JButton btnChangeFilename;
    private JTextField textFieldNewFilename;

    public GUIGravarFormas() {
        init();
        myInit();
    }

    private void init() {
        setTitle("Gravar Formas");
        setResizable(false);
        getContentPane().setLayout(null);

        textFieldSearch = new JTextField();
        textFieldSearch.setBounds(6, 6, 352, 26);
        getContentPane().add(textFieldSearch);

        JButton btn_browseFile = new JButton("Browse File");
        btn_browseFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleBtnBrowse();
            }
        });
        btn_browseFile.setBounds(370, 6, 117, 29);
        getContentPane().add(btn_browseFile);

        rdbtnGravarFormas = new JCheckBox("Gravar no ficheiro");
        rdbtnGravarFormas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleGravarFicheiro();
            }

        });
        rdbtnGravarFormas.setBounds(81, 76, 154, 23);
        getContentPane().add(rdbtnGravarFormas);

        rdbtnReproduzirFormas = new JCheckBox("Reproduzir formas");
        rdbtnReproduzirFormas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleReproduzirFormas();
            }

        });
        rdbtnReproduzirFormas.setBounds(81, 123, 165, 23);
        getContentPane().add(rdbtnReproduzirFormas);

        rdbtnAlterarNomeFile = new JRadioButton("Alterar nome do ficheiro");
        rdbtnAlterarNomeFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAlterarNomeFile();
            }

        });
        rdbtnAlterarNomeFile.setBounds(81, 170, 193, 23);
        getContentPane().add(rdbtnAlterarNomeFile);

        textFieldNewFilename = new JTextField();
        textFieldNewFilename.setBounds(109, 205, 206, 26);
        getContentPane().add(textFieldNewFilename);
        textFieldNewFilename.setColumns(10);

        btnChangeFilename = new JButton("Change filename");
        btnChangeFilename.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleChangeFilename();
            }

        });
        btnChangeFilename.setBounds(327, 205, 135, 29);
        getContentPane().add(btnChangeFilename);

        setSize(508, 311);
        setVisible(true);
    }

    private void myInit() {
        if (textFieldSearch.getText().trim().equals(""))
            textFieldSearch.setText("comunicacao.dat");


        textFieldNewFilename.setVisible(rdbtnAlterarNomeFile.isSelected());
        btnChangeFilename.setVisible(rdbtnAlterarNomeFile.isSelected());
    }

    private void handleBtnBrowse() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            textFieldSearch.setText(chooser.getSelectedFile().getAbsolutePath());
            fileName = textFieldSearch.getText();

        }

        System.out.println("Browsing");

    }

    private void handleGravarFicheiro() {
        rdbtnReproduzirFormas.setSelected(false);
        textFieldNewFilename.setVisible(rdbtnAlterarNomeFile.isSelected());
        btnChangeFilename.setVisible(rdbtnAlterarNomeFile.isSelected());
    }

    private void handleReproduzirFormas() {
        rdbtnGravarFormas.setSelected(false);
        textFieldNewFilename.setVisible(rdbtnAlterarNomeFile.isSelected());
        btnChangeFilename.setVisible(rdbtnAlterarNomeFile.isSelected());
    }

    private void handleAlterarNomeFile() {
        textFieldNewFilename.setVisible(rdbtnAlterarNomeFile.isSelected());
        btnChangeFilename.setVisible(rdbtnAlterarNomeFile.isSelected());
    }

    private void handleChangeFilename() {
        // TODO Auto-generated method stub

    }

    public void run() {

    }
}
