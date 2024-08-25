import java.util.concurrent.Semaphore;

public class Mesa {
    private final int id;
    private final int maxClientes;
    private int clientesAtuais;
    private final Semaphore mutex;
    private final Semaphore garcomMutex;
    private boolean precisaInspecao;

    public Mesa(int id, int maxClientes) {
        this.id = id;
        this.maxClientes = maxClientes;
        this.clientesAtuais = 0;
        this.mutex = new Semaphore(1);
        this.garcomMutex = new Semaphore(0);
        this.precisaInspecao = false;
    }

    public boolean sentarCliente(int clienteId) {
        try {
            mutex.acquire();
            if (clientesAtuais < maxClientes && !precisaInspecao) {
                clientesAtuais++;
                System.out.println("Cliente " + clienteId + " sentou na mesa " + id + ". Total na mesa: " + clientesAtuais);
                mutex.release();
                return true;
            }
            mutex.release();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sairCliente(int clienteId) {
        try {
            mutex.acquire();
            clientesAtuais--;
            System.out.println("Cliente " + clienteId + " saiu da mesa " + id + ". Total na mesa: " + clientesAtuais);
            if (clientesAtuais == 0) {
                precisaInspecao = true; // Marca que a mesa precisa ser inspecionada
                garcomMutex.release();  // Libera o garçom para inspecionar a mesa
            }
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void garcomInspecionar() {
        try {
            garcomMutex.acquire(); // Espera até que a mesa esteja vazia
            mutex.acquire();
            if (precisaInspecao) {
                System.out.println("Garçom está inspecionando a mesa " + id);
                Thread.sleep(500); // Tempo de inspeção
                System.out.println("Garçom terminou de inspecionar a mesa " + id);
                precisaInspecao = false;
            }
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }
}