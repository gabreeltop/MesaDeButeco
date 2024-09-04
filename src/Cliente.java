public class Cliente implements Runnable {
    private static int idCounter = 0;
    private final int id;
    private final Mesa mesa;
    private static final int MAX_TENTATIVAS = 10;

    public Cliente(Mesa mesa) {
        this.id = idCounter++;
        this.mesa = mesa;
    }

    @Override
    public void run() {
        int tentativas = 0;
        while (tentativas < MAX_TENTATIVAS) {
            if (mesa.tentarSentar()) {
                System.out.println("Cliente " + id + " sentou na mesa " + mesa.getId() + " atualmente na mesa: " + mesa.getOcupacaoAtual());
                try {
                    Thread.sleep(1250); // Simula o tempo que o cliente fica na mesa
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                mesa.sair();
                System.out.println("Cliente " + id + " saiu da mesa " + mesa.getId() + ", atualmente na mesa: " + mesa.getOcupacaoAtual());
                break;
            } else {
                tentativas++;
                System.out.println("Cliente " + id + " tentou sentar na mesa " + mesa.getId() + " (" + tentativas + "/" + MAX_TENTATIVAS + ")");
                try {
                    Thread.sleep(1850); // Espera antes de tentar novamente
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        if (tentativas == MAX_TENTATIVAS) {
            System.out.println("Cliente " + id + " desistiu após " + MAX_TENTATIVAS + " tentativas.");
        }
    }
}