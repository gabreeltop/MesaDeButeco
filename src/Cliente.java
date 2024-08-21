class Cliente implements Runnable {
    private static int nextId = 1;
    private final int id;
    private int tentativas;
    private final Mesa mesa;

    public Cliente(Mesa mesa) {
        this.mesa = mesa;
        this.id = nextId++;
        this.tentativas = 0;
    }

    @Override
    public void run() {
        while (tentativas < 10 && !mesa.sentarCliente()) {
            tentativas++;
            System.out.println("Cliente "+ id + " (Tentativa " + tentativas + ")" + " falhou para cliente na mesa " + mesa.getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (tentativas < 10) {
            System.out.println("Cliente " + id + " conseguiu sentar na mesa " + mesa.getId() + " após " + tentativas + " tentativas.");
            try {
                Thread.sleep((long) (Math.random() * 5000 + 3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mesa.sairCliente();
                System.out.println("Cliente " + id + " saiu da mesa " + mesa.getId());
            }
        } else {
            System.out.println("Cliente " + id + " desistiu após " + tentativas + " tentativas na mesa " + mesa.getId());
        }
    }

}