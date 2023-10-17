import org.json.JSONObject;

public class Transit {
    private static String url;
    private static String vault_token;
    private static String vault_transit_key_name;
    
    public Transit(String url, String vault_token, String vault_transit_key_name) {
        this.url = url;
        this.vault_token = vault_token;
        this.vault_transit_key_name = vault_transit_key_name;
    }
    public String getCipherText(String plainText) {
        API api = new API(url, vault_token);
        Transform transform = new Transform();
        String action = String.format("/v1/transit/encrypt/%s", vault_transit_key_name);
        
        String base64Data = transform.base64Encode(plainText);
        JSONObject encryptionFormData = transform.encryptionForm(base64Data);
        String cipherText = api.post(action, encryptionFormData).getJSONObject("data").getString("ciphertext");
        
        return cipherText;
    }
    
    public String getPlainText(String cipherText) {
        API api = new API(url, vault_token);
        Transform transform = new Transform();
        String action = String.format("/v1/transit/decrypt/%s", vault_transit_key_name);
        
        JSONObject decryptionFormData = transform.decryptionForm(cipherText);
        String base64Data = api.post(action, decryptionFormData).getJSONObject("data").getString("plaintext");
        String plainText = transform.base64Decode(base64Data);
        
        return plainText;
    }
}
