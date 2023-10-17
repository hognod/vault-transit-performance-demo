import java.util.ArrayList;
import java.util.Scanner;

public class Main {
//    private static String vault_host = "http://127.0.0.1:8200";
//    private static String mysql_host = "127.0.0.1";
//    private static String mysql_port = "3306";
//    private static String mysql_db = "VaultData";
//    private static String mysql_user = "app";
//    private static String mysql_password = "password";
    
    private static String mysql_host = System.getenv("MYSQL_HOST");
    private static String mysql_port = System.getenv("MYSQL_PORT");
    private static String mysql_db_name = System.getenv("MYSQL_DB_NAME");
    private static String mysql_username = System.getenv("MYSQL_USERNAME");
    private static String mysql_userpw = System.getenv("MYSQL_USERPW");
    
    private static String vault_host = System.getenv("VAULT_HOST");
    private static String vault_port = System.getenv("VAULT_PORT");
    private static String vault_token = System.getenv("VAULT_TOKEN");
    private static String vault_transit_key_name = System.getenv("VAULT_TRANSIT_KEY_NAME");
    
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String url = String.format("http://%s:%s", vault_host, vault_port);
        
        Transit transit = new Transit(url, vault_token, vault_transit_key_name);
        MySQL mysql = new MySQL(mysql_host, mysql_port, mysql_db_name, mysql_username, mysql_userpw);
        Dummy dummy = new Dummy();
        
        ArrayList<String> mysql_data;
        
        System.out.println("1. App -> Vault -> DB\n2. DB -> Vault -> App");
        Integer select = scanner.nextInt();
        
        if (select == 1) {
            for (int i=0; i<100000; i++) {
                String randomString = dummy.dummyString(400);
                String cipherText = transit.getCipherText(randomString);
                mysql.insertData(cipherText);
            }
            System.out.println("Complete.");
        }else if (select == 2) {
            mysql_data = mysql.getData();
            for (int i=0; i<mysql_data.size(); i++) {
                String plainText = transit.getPlainText(mysql_data.get(i));
                System.out.println(plainText);
            }
        }else {
            System.out.println("Please choose the correct number.");
        }

    }
    
}
