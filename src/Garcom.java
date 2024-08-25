public class Garcom extends Thread {
    private final Mesa[] mesas;

    public Garcom(Mesa[] mesas) {
        this.mesas = mesas;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (mesas.length == 0) {
                    System.out.println("Nenhuma mesa disponível para inspeção.");
                    Thread.sleep(1500);
                    continue;
                }

                for (Mesa mesa : mesas) {
                    System.out.println("Garçom inspecionou a mesa " + mesa.getId());
                    Thread.sleep(500);
                }
                Thread.sleep(1500);

            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }


}