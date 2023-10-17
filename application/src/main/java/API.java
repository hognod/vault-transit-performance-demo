import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class API {
    private static String vault_host;
    private static String vault_token;
    
    public API(String vault_host, String vault_token) {
        this.vault_host = vault_host;
        this.vault_token = vault_token;
    }
    
    public JSONObject post(String action, JSONObject data) {
        
        try {
            URL url = new URL(vault_host+action);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
    
    
            connection.setRequestProperty("X-Vault-Token", vault_token);
    
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(String.valueOf(data));
            wr.flush();
            wr.close();
    
    
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                
                JSONObject responseData = new JSONObject(sb.toString());
                return responseData;
            } else {
                System.out.println(connection.getResponseMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        return null;
    }
    
}
