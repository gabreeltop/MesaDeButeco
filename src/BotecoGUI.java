import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BotecoGUI extends JFrame {
    private final Mesa[] mesas;
    private final MesaPanel[] mesaPanels;

    public BotecoGUI(Mesa[] mesas) {
        this.mesas = mesas;
        this.mesaPanels = new MesaPanel[mesas.length];

        setTitle("Boteco");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2));

        for (int i = 0; i < mesas.length; i++) {
            mesaPanels[i] = new MesaPanel(mesas[i]);
            add(mesaPanels[i]);
        }

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateMesaStatus();
            }
        }, 0, 1000);

        setVisible(true);
    }

    private void updateMesaStatus() {
        for (MesaPanel mesaPanel : mesaPanels) {
            mesaPanel.atualizar();
        }
    }

    // Método para utilizar a variável mesas
    public int getNumeroDeMesas() {
        return mesas.length;
    }

    private class MesaPanel extends JPanel {
        private final Mesa mesa;

        public MesaPanel(Mesa mesa) {
            this.mesa = mesa;
            setPreferredSize(new Dimension(100, 100));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Fundo verde claro
            g.setColor(new Color(144, 238, 144));
            g.fillRect(0, 0, getWidth(), getHeight());

            // Desenhar mesa redonda marrom
            g.setColor(new Color(139, 69, 19));
            g.fillOval(10, 10, getWidth() - 20, getHeight() - 40); // Mais espaço para o balcão

            // Desenhar borda da mesa
            g.setColor(Color.BLACK);
            g.drawOval(10, 10, getWidth() - 20, getHeight() - 40); // Mais espaço para o balcão

            // Informações da mesa dentro da mesa
            g.setColor(Color.BLACK);
            g.drawString("Mesa " + mesa.getId(), getWidth() / 2 - 20, getHeight() / 2 - 10);
            g.drawString("Ocupação: " + mesa.getOcupacaoAtual() + "/" + mesa.getCapacidade(), getWidth() / 2 - 30, getHeight() / 2 + 10);

            if (mesa.precisaInspecao()) {
                g.setColor(Color.RED);
                g.drawString("Precisa de inspeção", getWidth() / 2 - 30, getHeight() / 2 + 30);
            }

            // Desenhar clientes como pontos
            g.setColor(Color.BLUE);
            int numClientes = mesa.getOcupacaoAtual();
            for (int i = 0; i < numClientes; i++) {
                int angle = 360 / numClientes * i;
                int x = (int) (getWidth() / 2 + (getWidth() / 4) * Math.cos(Math.toRadians(angle)));
                int y = (int) (getHeight() / 2 + (getHeight() / 4) * Math.sin(Math.toRadians(angle)));
                g.fillOval(x - 5, y - 5, 10, 10);
            }

            // Desenhar balcão na parte inferior
            g.setColor(new Color(139, 69, 19));
            g.fillRect(0, getHeight() - 20, getWidth(), 20);
            g.setColor(Color.BLACK);
            g.drawRect(0, getHeight() - 20, getWidth(), 20);
        }

        public void atualizar() {
            repaint();
        }
    }

    public static void main(String[] args) {
        int numMesas = 4;
        Mesa[] mesas = new Mesa[numMesas];

        for (int i = 0; i < numMesas; i++) {
            mesas[i] = new Mesa(i, 4);
        }

        BotecoGUI botecoGUI = new BotecoGUI(mesas);
        System.out.println("Número de mesas: " + botecoGUI.getNumeroDeMesas());

        Garcom garcom = new Garcom(mesas);
        garcom.start();

        Queue<Cliente> filaCliente = new LinkedList<>();
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            filaCliente.add(new Cliente(mesas[random.nextInt(numMesas)]));
        }

        while (!filaCliente.isEmpty()) {
            Cliente cliente = filaCliente.poll();
            new Thread(cliente).start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            filaCliente.add(cliente);
        }
    }
}