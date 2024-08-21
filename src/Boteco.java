import java.util.LinkedList;
import java.util.Queue;

public class Boteco {
    public static void main(String[] args) {
        int numMesas = 4;
        int numClientes = 20;
        Mesa[] mesas = new Mesa[numMesas];

        for (int i = 0; i < numMesas; i++) {
            mesas[i] = new Mesa(i, 4);
        }

        Garcom garcom = new Garcom(mesas);
        garcom.start();

        Queue<Cliente> filaCliente = new LinkedList<>();

        for (int i = 0; i < numClientes; i++) {
            filaCliente.add(new Cliente(mesas[i % numMesas]));
        }

        while(!filaCliente.isEmpty()) {
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


