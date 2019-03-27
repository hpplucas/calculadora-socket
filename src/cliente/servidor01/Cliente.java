package cliente.servidor01;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
   
    
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
       
        System.out.println("Informe o 1° número: ");
        int n1 = sc.nextInt();
        System.out.println("Informe o 2° número: ");
        int n2 = sc.nextInt();
        System.out.println("Informe a operação: ");
        char operacao = sc.next().charAt(0);
        
        
        try {
            //cria conexão entre cliente e servidor
            System.out.println("Estabelecendo conexão...");
            Socket socket = new Socket("localhost", 5557);
            System.out.println("Conexão estabelecida! ");
            
            ObjectOutput output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            
            
            output.writeInt(n1);
            output.flush();//server saber fim da mensagem
            output.writeInt(n2);
            output.flush();
            output.writeChar(operacao);
            output.flush();
            
            System.out.println("Aguardando a resposta do servidor...");
            int resp;
            
            resp = input.readInt();
            System.out.println("O resultado foi: "+resp);
            
           
            output.close();
            input.close();
            socket.close();
            
        } catch (IOException ex) {
            System.out.println("Erro: "+ ex.getMessage());
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
}
