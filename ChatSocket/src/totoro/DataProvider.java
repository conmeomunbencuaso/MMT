/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package totoro;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Totoro
 */
public class DataProvider {

    private Socket socket;
    private ObjectOutputStream send;
    private ObjectInputStream recive;

    public DataProvider(Socket socket) {
        this.socket = socket;
    }
    // send a package through network
    public void sendPackage(Package pag) throws IOException {
        ObjectOutputStream sendToServer = new ObjectOutputStream(socket.getOutputStream());
        sendToServer.writeObject(pag);
        sendToServer.flush();
    }

    // recive a package through network
    public Package recivePackage() throws IOException, ClassNotFoundException {
        Package pag = new Package();
        ObjectInputStream reciveFromServer = new ObjectInputStream(socket.getInputStream());
        pag = (Package) reciveFromServer.readObject();
        return pag;
    }
}
