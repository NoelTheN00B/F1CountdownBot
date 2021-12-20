package de.leon.f1twitterbot;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.JsonKeysetReader;
import com.google.crypto.tink.JsonKeysetWriter;
import com.google.crypto.tink.KeyTemplates;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.aead.AeadWrapper;
import com.google.crypto.tink.proto.KeyTemplate;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

public class Security {

    private final static String FILE_NAME = "startArguments.json";
    KeysetHandle currentKeysetHanlde;

    public Security() throws GeneralSecurityException, IOException {
        AeadConfig.register();

        if (!new File(FILE_NAME).exists()) {
            saveKeys(generateNewKeys());
        }
        currentKeysetHanlde = loadKeys();
    }

    private KeysetHandle generateNewKeys() throws GeneralSecurityException {
        return KeysetHandle.generateNew(KeyTemplates.get("AES128_GCM"));
    }

    private void saveKeys(KeysetHandle keysetHandle) throws IOException {
        CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withFile(new File(FILE_NAME)));
    }

    private KeysetHandle loadKeys() throws IOException, GeneralSecurityException {
        return CleartextKeysetHandle.read(JsonKeysetReader.withFile(new File(FILE_NAME)));
    }

    public void getNewKeys() throws GeneralSecurityException, IOException {
        if (!new File(FILE_NAME).exists()) {
            new File(FILE_NAME).delete();
            saveKeys(generateNewKeys());
            currentKeysetHanlde = loadKeys();
        }
    }

    public byte[] encrypt(String textToEncrypt) throws GeneralSecurityException {
        Aead aead = currentKeysetHanlde.getPrimitive(Aead.class);
        return aead.encrypt(textToEncrypt.getBytes(StandardCharsets.UTF_8), MasterKey.getAAD().getBytes(
            StandardCharsets.UTF_8));
    }

    public String encryptToString(String textToEncrypt) throws GeneralSecurityException {
        return new String(encrypt(textToEncrypt));
    }

    public byte[] decrypt(byte[] textToDecrypt) throws GeneralSecurityException {
        Aead aead = currentKeysetHanlde.getPrimitive(Aead.class);
        return aead.decrypt(textToDecrypt, MasterKey.getAAD().getBytes(
            StandardCharsets.UTF_8));
    }

    public byte[] decrypt(String textToDecrypt) throws GeneralSecurityException {
        return decrypt(textToDecrypt.getBytes(StandardCharsets.UTF_8));
    }

    public String decryptToString(byte[] textToDecrypt) throws GeneralSecurityException {
        return new String(decrypt(textToDecrypt));
    }

    public String decryptToString(String textToDecrypt) throws GeneralSecurityException {
        return new String(decrypt(textToDecrypt.getBytes(StandardCharsets.UTF_8)));
    }
}
