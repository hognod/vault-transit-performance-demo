import org.json.JSONObject;

import java.util.Base64;

public class Transform {
    public String base64Encode(String data) {
        byte[] dataToByte = data.getBytes();
        Base64.Encoder encoder = Base64.getEncoder();
    
        byte[] encodeByte = encoder.encode(dataToByte);
        String encodeData = new String(encodeByte);
        
        return encodeData;
    }
    
    public String base64Decode(String data) {
        byte[] dataToByte = data.getBytes();
        Base64.Decoder decoder = Base64.getDecoder();
        
        byte[] decodeByte = decoder.decode(dataToByte);
        String decodeData = new String(decodeByte);
        
        return decodeData;
    }
    
    public JSONObject encryptionForm(String data) {
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put("plaintext", data);
        
        return jsonObject;
    }
    
    public JSONObject decryptionForm(String data) {
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put("ciphertext", data);
        
        return jsonObject;
    }
}
