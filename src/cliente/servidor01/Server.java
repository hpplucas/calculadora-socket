package cliente.servidor01;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket server;

    private void criarServidor(int porta) throws IOException {
        server = new ServerSocket(porta);
    }

    private Socket esperarConexao() throws IOException {
        Socket socket = server.accept();
        return socket;
    }

    private void fecharSocket(Socket socket) throws IOException {
        socket.close();
    }

    private void tratarConexao(Socket socket) throws IOException {
        //cliente---------> SOCKET <----------- servidor

        try {
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            /*protocolo 
            cliente 
            
            */
            int n1 = input.readInt();
            int n2 = input.readInt();
            char operacao = input.readChar();
            
            int resp = 0;
            
            switch (operacao) {
                case '+':
                    resp = n1 + n2;
                    break;
                case '-':
                    resp = n1 - n2;
                    break;
                case '/':
                    resp = n1 / n2;
                    break;
                case '*':
                    resp = n1 * n2;
                    break;
                    
            }
            
            System.out.println("Calculando "+n1+operacao+n2);
            output.writeInt(resp);
            output.flush();  //cliente saber quando terminou a mensagem

            output.close();
            input.close();
        } catch (IOException ex) {
            //tratamento de falhas   
            System.out.println("Falha no tratamento de conexão com o cliente");
            System.out.println("Erro: " + ex.getMessage());
        } finally {
            fecharSocket(socket);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.criarServidor(5557);

            while (true) {
                System.out.println("Aguardando conexão...");
                Socket socket = server.esperarConexao();
                System.out.println("Cliente conectado");
                server.tratarConexao(socket);
                System.out.println("Cliente desconectado");
            }

        } catch (IOException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }

    }
}
