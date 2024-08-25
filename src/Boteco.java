import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Boteco {
    public static void main(String[] args) {
        int numMesas = 4;
        int numClientes = 50;
        Mesa[] mesas = new Mesa[numMesas];

        for (int i = 0; i < numMesas; i++) {
            mesas[i] = new Mesa(i, 4);
        }

        Garcom garcom = new Garcom(mesas);
        garcom.start();

        Queue<Cliente> filaCliente = new LinkedList<>();
        Random random = new Random();

        for (int i = 0; i < numClientes; i++) {
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
