public class Mesa  {
    private int id;
    private int maxClientes;
    private int clientesAtuais;

    public Mesa(int id, int maxClientes) {
        this.id = id;
        this.maxClientes = maxClientes;
        this.clientesAtuais = 0;
    }

    public synchronized boolean sentarCliente() {
        if (clientesAtuais < maxClientes) {
            clientesAtuais++;
            return true;
        }
        return false;
    }

    public synchronized void sairCliente() {
        if (clientesAtuais > 0) {
            clientesAtuais--;
        }
    }

    public synchronized boolean isVazia() {
        return clientesAtuais == 0;
    }

    public synchronized int getClientesAtuais() {
        return clientesAtuais;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxClientes() {
        return maxClientes;
    }

    public void setMaxClientes(int maxClientes) {
        this.maxClientes = maxClientes;
    }

    public void setClientesAtuais(int clientesAtuais) {
        this.clientesAtuais = clientesAtuais;
    }
    
}

