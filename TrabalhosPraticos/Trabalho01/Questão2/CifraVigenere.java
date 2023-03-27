package Trabalho01.Questão2;

public class CifraVigenere {
    private String key;

    public CifraVigenere(String key) {
        this.key = key;
    }    

    public String encrypt(String plaintext) {
        String ciphertext = "";
        int keyIndex = 0;        
            
        for (int i = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);

            if (Character.isLetter(c)) {
                
                int shift = key.charAt(keyIndex) - 'a';
                c = (char) (((c - 'a' + shift) % 26) + 'a');
                keyIndex = (keyIndex + 1) % key.length();
            }
            ciphertext += c;
        }

        return ciphertext;
    }

    public String decrypt(String ciphertext) {
        String plaintext = "";
        int keyIndex = 0;

        for (int i = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            if (Character.isLetter(c)) {
                int shift = key.charAt(keyIndex) - 'a';
                c = (char) (((c - 'a' - shift + 26) % 26) + 'a');
                keyIndex = (keyIndex + 1) % key.length();
            }
            plaintext += c;
        }

        return plaintext;
    }

    public static void main(String[] args) {
       CifraVigenere cipher = new CifraVigenere("secret");

        String plaintext = "Hello, world!";
        String ciphertext = cipher.encrypt(plaintext);
        String decryptedText = cipher.decrypt(ciphertext);

        System.out.println("Plaintext: " + plaintext);
        System.out.println("Ciphertext: " + ciphertext);
        System.out.println("Decrypted text: " + decryptedText);
    }
}

