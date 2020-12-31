import javax.swing.*;
import java.awt.*;

public class GUIRobotDesenhador extends JFrame {
    private Canvas c;
    private int lastX = 50, lastY = 300;

    private enum Direction {
        RIGHT, UP, LEFT, DOWN;

        public Direction getNextDir(boolean reverse) {
            int nextOrdinal = Direction.values().length - 1;
            if (reverse)
                return (this.ordinal() == 0) ? Direction.DOWN : Direction.values()[this.ordinal() - 1];
            return (this.ordinal() < nextOrdinal) ? Direction.values()[this.ordinal() + 1] : Direction.RIGHT;
        }

        public int[] toValues() {
            switch (Direction.values()[this.ordinal()]) {
                case UP:
                    return new int[]{0, -1};
                case DOWN:
                    return new int[]{0, 1};
                case LEFT:
                    return new int[]{-1, 0};
                case RIGHT:
                    return new int[]{1, 0};
            }
            return new int[]{};
        }
    }

    private Direction dir = Direction.RIGHT;

    public GUIRobotDesenhador() {
        initialize();
    }

    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("GUI Robot Desenhador");

        c = new Canvas() {
            public void paint(Graphics g) {
            }
        };

        c.setBackground(Color.black);

        add(c);
        setSize(800, 600);
        //  setResizable(false);
        setVisible(true);
    }

    public void desenhar(Mensagem msg) {
        Graphics g = c.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 200, 100);
        g.setColor(Color.red);
        g.setFont(new Font("Bold", 1, 20));

        switch (msg.getTipo()) {
            case 0:
                g.drawString("Reta", 10, 30);
                int[] rot = dir.toValues();
                g.setColor(Color.red);
                g.drawLine(lastX, lastY, lastX + (msg.getRaio() * 10 * rot[0]), lastY + (msg.getRaio() * 10 * rot[1]));
                lastX += (msg.getRaio() * 10 * rot[0]);
                lastY += (msg.getRaio() * 10 * rot[1]);
                break;
            case 1:
                g.drawString("Curva Esquerda", 10, 30);
                if (msg.getRaio() == 0)
                    dir = dir.getNextDir(false);
                else {
                    int initX = lastX;
                    int initY = lastY;
                    lastY -= msg.getRaio();
                    g.setColor(Color.red);
                    g.drawOval(lastX, lastY, msg.getRaio(), msg.getRaio());
                    lastX = initX;
                    lastY = initY;
                }
                break;
            case 2:
                g.drawString("Parar", 10, 30);
                break;
            case 3:
                g.drawString("Curva Direita", 10, 30);
                if (msg.getRaio() == 0)
                    dir = dir.getNextDir(true);
                else {
                    int initX = lastX;
                    int initY = lastY;
                    g.setColor(Color.red);
                    g.drawOval(lastX, 300, msg.getRaio(), msg.getRaio());
                    lastX = initX;
                    lastY = initY;
                }
                break;
            case 4:
                g.drawString("Espaçamento", 10, 30);
                lastX += 75;
                break;
        }
    }
}
