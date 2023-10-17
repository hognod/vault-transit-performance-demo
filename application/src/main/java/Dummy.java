import java.security.SecureRandom;

public class Dummy {
    public String dummyString(int stringLength) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder(stringLength);
        SecureRandom random = new SecureRandom();
        
        for (int i=0; i < stringLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            randomString.append(characters.charAt(randomIndex));
        }
        
        return randomString.toString();
    }
}
