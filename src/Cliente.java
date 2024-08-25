public class Cliente implements Runnable {
    private static int nextId = 1;
    private final int id;
    private int tentativas;
    private final Mesa mesa;

    public Cliente(Mesa mesa) {
        this.mesa = mesa;
        this.id = nextId++;
        this.tentativas = 1;
    }

    @Override
    public void run() {
        while (tentativas < 10) {
            try {
                if (mesa.sentarCliente(id)) {
                    System.out.println("Cliente " + id + " conseguiu sentar na mesa " + mesa.getId() + " após " + tentativas + " tentativas.");
                    Thread.sleep((long) (Math.random() * 5000 + 3000));
                    mesa.sairCliente(id);
                    break;
                } else {
                    tentativas++;
                    System.out.println("Cliente " + id + " (Tentativa " + tentativas + ") falhou ao tentar sentar na mesa " + mesa.getId());
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (tentativas >= 10) {
            System.out.println("Cliente " + id + " desistiu após " + tentativas + " tentativas na mesa " + mesa.getId());
        }
    }
}
