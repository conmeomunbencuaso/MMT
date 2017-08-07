/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package totoro;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

enum Header {
    REGISTER, LOGIN, LOGOUT
};

/**
 *
 * @author Totoro
 */
public class Server {

    private static final int PORT = 7777;
    private static boolean running = true;
    private static ServerSocket socketServer = null;
    private static Socket socket = null;

    public static void main(String[] args) throws IOException {
        DataProvider dataProvider = new DataProvider();
        dataProvider.connect();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.start();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }).start();
    }

    // lắng nghe các kết nối từ phía Client
    // khi gặp kết nối tạo ra một luồng phục vụ riêng cho mỗi Client.
    public static void start() throws IOException {
        socketServer = new ServerSocket(PORT);
        System.out.println("Listenning...");
        while (running) {
            socket = socketServer.accept();
            ThreadService service = new ThreadService((socket));
            service.start();
            System.out.println("Connected!");
        }
    }

    public static void stop() {
        try {
            socketServer.close();
            running = false;
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/**
 *
 * @author Serivce
 */
class ThreadService extends Thread {

    private Socket socket = null;
    private ArrayList<Account> listOfAccount = null;
    Transport transport;

    public ThreadService(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            transport = new Transport(socket);
            while (true) {
                Package pagClient = (Package) transport.recivePackage();
                if (null != pagClient.getHeader()) {
                    switch (pagClient.getHeader()) {
                        // create account for label REGISTER
                        case REGISTER:
                            Account act = ((Account) pagClient.getData());
                            boolean resultRegister = createAccount(act);

                            Package pagRegister = new Package(Header.REGISTER, resultRegister);
                            transport.sendPackage(pagRegister);
                            break;
                        // check account for label LOGIN
                        case LOGIN:
                            String strLog = (String) pagClient.getData();
                            String[] login = strLog.split(",");

                            String userName = login[0];
                            String password = login[1];

                            boolean resultLogin = checkLogin(userName, password);
                            Package pagLogin = new Package(Header.LOGIN, resultLogin);
                            transport.sendPackage(pagLogin);
                            break;
                        case LOGOUT:
                            // input: Header.OUTPUT
                            // output: void
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ex.toString());
        }
    }

    public boolean createAccount(Account act) throws IOException {
        // nhận vào thông tin tài khoản
        // kiểm tra tài khoản hợp lệ
        // tạo một tài khoản trong database
        // nếu thành công thì ~
        return false;
    }

    public void disConnect() {

    }

    private boolean checkLogin(String userName, String password) {
        if (userName.equals("totoro") && password.equals("123456")) {
            return true;
        }
        return false;
    }

    public void updateAccount() {

    }

    public void requestSendFile() {

    }

}
