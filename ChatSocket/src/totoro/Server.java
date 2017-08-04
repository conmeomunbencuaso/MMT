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
    private static Socket socket = null;
    private ArrayList listOnline = null;

    public static void main(String[] args) throws IOException {
        Server.start();
    }
    // ok t hì chát vô đây nhé oke
    // lắng nghe các kết nối từ phía Client
    // khi gặp kết nối tạo ra một luồng phục vụ riêng cho mỗi Client.
    public static void start() throws IOException {
        ServerSocket socketServer = new ServerSocket(PORT);
        System.out.println("Listenning...");
        while (running) {
            socket = socketServer.accept(); 
            ThreadService service = new ThreadService((socket));
            service.start();
            System.out.println("Connected!");
        }
    }

    public static void stop() {
        running = false;
    }
}

/**
 *
 * @author Serivce
 */
class ThreadService extends Thread {

    private Socket socket = null;
    private ArrayList<Account> listOfAccount = null;
    DataProvider dataProvider;

    public ThreadService(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try {
            dataProvider = new DataProvider(socket);
            while (true) {
                Package pagClient = (Package) dataProvider.recivePackage();
                if (null != pagClient.getHeader()) {
                    switch (pagClient.getHeader()) {
                        // create account for label REGISTER
                        case REGISTER:
                            Account act = ((Account) pagClient.getData());
                            boolean resultRegister = createAccount(act);

                            Package pagRegister = new Package(Header.REGISTER, resultRegister);
                            dataProvider.sendPackage(pagRegister);
                            break;
                        // check account for label LOGIN
                        case LOGIN:
                            String strLog = (String) pagClient.getData();
                            String[] login = strLog.split(",");

                            String userName = login[0].toString();
                            String password = login[1].toString();

                            boolean resultLogin = checkLogin(userName, password);
                            Package pagLogin = new Package(Header.LOGIN, resultLogin);
                            dataProvider.sendPackage(pagLogin);
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
