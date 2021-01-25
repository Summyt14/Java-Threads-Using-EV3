package Buffer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BufferCircularGUI extends JFrame implements IBufferCircularGUI {

	private static final long serialVersionUID = -103615988849405009L;

	private JPanel contentPane;

	private JTextField[] texValues;
	private JRadioButton[] indexPut;
	private JRadioButton[] indexGet;
	private JLabel lblIndiceGet;
	private JLabel lblIndicePut;

	public void setText(int pos, String text) {
		Runnable r = () -> texValues[pos].setText(text);
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			SwingUtilities.invokeLater(r);
		}
	}

	public void setIndexPut(int pos) {
		Runnable r = () -> {
			indexPut[pos].setSelected(true);
			lblIndicePut.setText(String.format("Índice put = %d", pos));
		};
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			SwingUtilities.invokeLater(r);
		}
	}

	public void setIndexGet(int pos) {
		Runnable r = () -> {
			indexGet[pos].setSelected(true);

			lblIndiceGet.setText(String.format("Índice get = %d", pos));
		};
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			SwingUtilities.invokeLater(r);
		}
	}

	public void displayNumberOfItems(int numberOfItems) {
		Runnable r = () -> setTitle(String.format("Number of items = %d", numberOfItems));
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			SwingUtilities.invokeLater(r);
		}
	}

	public boolean isVisible() {
		return isShowing();
	}

	public void dispose(boolean state) {
		if (state) {
			dispose();
		} else {
			setVisible(true);
		}
	}

	public BufferCircularGUI(int dim) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 550, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 1, 10, 10));

		lblIndicePut = new JLabel("\u00CDndice de Put");
		lblIndicePut.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblIndicePut);

		JPanel panelElements = new JPanel();
		panelElements.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		contentPane.add(panelElements);
		panelElements.setLayout(new GridLayout(1, dim, 5, 5));

		this.indexPut = new JRadioButton[dim];
		this.indexGet = new JRadioButton[dim];
		this.texValues = new JTextField[dim];

		ButtonGroup bgPut = new ButtonGroup();
		ButtonGroup bgGet = new ButtonGroup();

		for (int idx = 0; idx < dim; ++idx) {
			JPanel panelElement = new JPanel();
			panelElement.setBorder(new LineBorder(new Color(0, 0, 0)));
			panelElement.setLayout(new GridLayout(3, 1, 5, 5));

			this.indexPut[idx] = new JRadioButton("");
			this.indexPut[idx].setHorizontalAlignment(SwingConstants.CENTER);
			this.indexPut[idx].setEnabled(false);
			bgPut.add(this.indexPut[idx]);
			panelElement.add(this.indexPut[idx]);

			this.texValues[idx] = new JTextField();
			this.texValues[idx].setHorizontalAlignment(SwingConstants.CENTER);
			this.texValues[idx].setText("");
			// this.texValues[ idx ].setColumns( 10 );
			this.texValues[idx].setEnabled(false);
			panelElement.add(this.texValues[idx]);

			this.indexGet[idx] = new JRadioButton("");
			this.indexGet[idx].setHorizontalAlignment(SwingConstants.CENTER);
			this.indexGet[idx].setEnabled(false);
			bgGet.add(this.indexGet[idx]);
			panelElement.add(this.indexGet[idx]);

			panelElements.add(panelElement);
		}

		lblIndiceGet = new JLabel("\u00CDndice de Get");
		lblIndiceGet.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblIndiceGet);

		this.setIndexPut(0);
		this.setIndexGet(0);

		this.setVisible(true);
		this.setSize(1800, 380);
	}
}
