package de.leon.f1twitterbot;

import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.JsonKeysetWriter;
import com.google.crypto.tink.KeyTemplates;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.proto.KeyTemplate;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class Security {

    public Security() throws GeneralSecurityException {
    }

    private KeysetHandle generateNewKeys() throws GeneralSecurityException {
        return KeysetHandle.generateNew(KeyTemplates.get("AES128_GCM"));
    }

    private void saveKeys(KeysetHandle keysetHandle) throws IOException {
        CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withFile(new File("startArguments.json")));
        keysetHandle.write(JsonKeysetWriter.withFile(new File("startArguments.json")));
    }

    private KeysetHandle loadKeys() {

    }
}
