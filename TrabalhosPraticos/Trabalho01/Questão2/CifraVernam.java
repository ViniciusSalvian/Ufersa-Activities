package Trabalho01.Questão2;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class CifraVernam {
    
    public static void main(String[] args) {
        String message = "Mensagem a ser cifrada";
        String key = generateKey(message.length());
        String ciphertext = encrypt(message, key);
        String plaintext = decrypt(ciphertext, key);

        System.out.println("Mensagem original: " + message);
        System.out.println("Chave: " + key);
        System.out.println("Mensagem cifrada: " + ciphertext);
        System.out.println("Mensagem decifrada: " + plaintext);
    }

    public static String generateKey(int length) {
        byte[] key = new byte[length];
        new Random().nextBytes(key);
        return new String(key, StandardCharsets.ISO_8859_1);
    }

    public static String encrypt(String message, String key) {
        byte[] messageBytes = message.getBytes(StandardCharsets.ISO_8859_1);
        byte[] keyBytes = key.getBytes(StandardCharsets.ISO_8859_1);
        byte[] ciphertextBytes = new byte[messageBytes.length];
        for (int i = 0; i < messageBytes.length; i++) {
            ciphertextBytes[i] = (byte) (messageBytes[i] ^ keyBytes[i]);
        }
        return new String(ciphertextBytes, StandardCharsets.ISO_8859_1);
    }

    public static String decrypt(String ciphertext, String key) {
        byte[] ciphertextBytes = ciphertext.getBytes(StandardCharsets.ISO_8859_1);
        byte[] keyBytes = key.getBytes(StandardCharsets.ISO_8859_1);
        byte[] plaintextBytes = new byte[ciphertextBytes.length];
        for (int i = 0; i < ciphertextBytes.length; i++) {
            plaintextBytes[i] = (byte) (ciphertextBytes[i] ^ keyBytes[i]);
        }
        return new String(plaintextBytes, StandardCharsets.ISO_8859_1);
    }

}
