/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package totoro;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Totoro
 */
public class Client {

    Socket socketClient;
    private String userName = null;
    private String password = null;

    public Socket getSocketClient() {
        return socketClient;
    }

    public void setSocketClient(Socket socketClient) {
        this.socketClient = socketClient;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void connect(String address, int port) throws IOException {
        socketClient = new Socket(address, port);
    }

    public void disConnect() throws IOException {
        socketClient.close();
    }

    // Sent to server of register
    public boolean registerAccount(Account act) throws IOException, ClassNotFoundException {
        boolean result = false;
        Package pagRegister = new Package(Header.REGISTER, act);
        DataProvider dataProvider = new DataProvider(socketClient);

        dataProvider.sendPackage(pagRegister);

        Package pagServer = dataProvider.recivePackage();
        if (pagServer.getHeader() == Header.REGISTER) {
            result = (boolean) pagServer.getData();
        }
        return result;
    }

    // Login with username, password
    public boolean login(String userName, String password) throws IOException, ClassNotFoundException {
        // gửi 2 chuỗi "đăng nhập" sang cho server kiểm tra
        boolean result = false;
        String pagData = userName + "," + password;
        Package pagLogin = new Package(Header.LOGIN, pagData);

        DataProvider dataProvider = new DataProvider(socketClient);
        dataProvider.sendPackage(pagLogin);

        // nhận thông tin đăng nhập hợp lệ: không hợp lệ ?
        Package pagServer = dataProvider.recivePackage();
        if (pagServer.getHeader() == Header.LOGIN) {
            result = (boolean) pagServer.getData();
        }
        return result;
    }

    // - 1 = logout
    public boolean logout() throws IOException, ClassNotFoundException {
        int out = -1;
        Package pagLogout = new Package(Header.LOGOUT, out);

        DataProvider dataProvider = new DataProvider(socketClient);
        dataProvider.sendPackage(pagLogout);

        dataProvider.recivePackage();
        return false;
    }

    public void chat(Account act) {

    }

    public void sendFile(String fileName) {

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client();
        client.connect("localhost", 7777);

        // Test tạo tài khoản
//        Account act = new Account("mèo mun bên cửa sổ", "totoro", "123456");
//        if (client.registerAccount(act) == true) {
//            System.out.println("Đăng ký thành công !");
//        } else {
//            System.out.println("Đăng ký thất bại~");
//        }
        // Test đăng nhập
        boolean test = client.login("totoro", "123456");
        if (test == true) {
            System.out.println("Đăng nhập thành công!");
        } else {
            System.out.println("Đăng nhập thất bại!");
        }
    }
}
