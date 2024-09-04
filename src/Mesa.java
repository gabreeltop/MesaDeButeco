import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mesa {
    private final int id;
    private final int capacidade;
    private int ocupacaoAtual;
    private final Semaphore semaphore;
    private final Lock lock;
    private boolean precisaInspecao;

    public Mesa(int id, int capacidade) {
        this.id = id;
        this.capacidade = capacidade;
        this.ocupacaoAtual = 0;
        this.semaphore = new Semaphore(capacidade, true);
        this.lock = new ReentrantLock();
        this.precisaInspecao = false;
    }

    public boolean tentarSentar() {
        try {
            semaphore.acquire();
            lock.lock();
            if (precisaInspecao) {
                semaphore.release();
                return false;
            }
            ocupacaoAtual++;
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            lock.unlock();
        }
    }

    public void sair() {
        lock.lock();
        try {
            if (ocupacaoAtual > 0) {
                ocupacaoAtual--;
                if (ocupacaoAtual == 0) {
                    precisaInspecao = true;
                }
            }
        } finally {
            lock.unlock();
            semaphore.release();
        }
    }

    public void inspecionar() {
        lock.lock();
        try {
            if (ocupacaoAtual == 0 && precisaInspecao) {
                System.out.println("Garçom inspecionou a mesa " + id);
                precisaInspecao = false;
            }
        } finally {
            lock.unlock();
        }
    }

    public int getId() {
        return id;
    }

    public int getOcupacaoAtual() {
        return ocupacaoAtual;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public boolean precisaInspecao() {
        return precisaInspecao;
    }
}