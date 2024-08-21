public class Garcom extends Thread {
    private final Mesa[] mesas;

    public Garcom(Mesa[] mesas) {
        this.mesas = mesas;
    }

    @Override
    public void run() {
        while (true) {
            try {
                for (Mesa mesa : mesas) {
                    if (mesa.isVazia()) {
                        System.out.println("Garçom inspecionou a mesa " + mesa.getId());
                        Thread.sleep(500);
                    }
                }
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

