package ru.lanit.at.crypto;


import org.jasypt.util.text.BasicTextEncryptor;

public class Crypto {
    private static String privateKey = "privateKey";

    public static String decrypt(String input) {
        BasicTextEncryptor dataEncryptor = new BasicTextEncryptor();
        dataEncryptor.setPasswordCharArray(privateKey.toCharArray());
        return dataEncryptor.decrypt(input);
    }

    public static String encrypt(String input) {
        BasicTextEncryptor dataEncryptor = new BasicTextEncryptor();
        dataEncryptor.setPasswordCharArray(privateKey.toCharArray());
        return dataEncryptor.encrypt(input);

    }

}
