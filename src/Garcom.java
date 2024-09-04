import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Garcom extends Thread {
    private final Mesa[] mesas;

    public Garcom(Mesa[] mesas) {
        this.mesas = mesas;
    }

    @Override
    public void run() {
        while (true) {
            try {
                List<Mesa> listaMesas = Arrays.asList(mesas);
                Collections.shuffle(listaMesas);
                for (Mesa mesa : listaMesas) {
                    mesa.inspecionar();
                    Thread.sleep(500); // O garçom espera antes de verificar a próxima mesa
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}