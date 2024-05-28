package org.example.greenuae.security;

import jakarta.persistence.AttributeConverter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SerializationUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Base64;

@Configuration
public class AES implements AttributeConverter<Object, String> {

    @Value("${aes.encryption.key}")
    private String encryptionKey;

    private final String encryptionCipher = "AES";

    private Key key;
    private Cipher cipher;

    private Key getKey() throws GeneralSecurityException, UnsupportedEncodingException {
        if (key == null) {
            if (encryptionKey.length() != 16 && encryptionKey.length() != 24 && encryptionKey.length() != 32) {
                throw new GeneralSecurityException("Invalid key length: must be 16, 24, or 32 bytes");
            }
            key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), encryptionCipher);
        }
        return key;
    }

    private Cipher getCipher() throws GeneralSecurityException {
        if (cipher == null)
            cipher = Cipher.getInstance(encryptionCipher);
        return cipher;
    }

    private void initCipher(int encryptMode) throws GeneralSecurityException, UnsupportedEncodingException {
        getCipher().init(encryptMode, getKey());
    }

    @SneakyThrows
    @Override
    // Encryption
    public String convertToDatabaseColumn(Object attribute) {
        if (attribute == null)
            return null;
        initCipher(Cipher.ENCRYPT_MODE);
        byte[] bytes = SerializationUtils.serialize(attribute);
        return Base64.getEncoder().encodeToString(getCipher().doFinal(bytes));
    }

    @SneakyThrows
    @Override
    // Decryption
    public Object convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;
        initCipher(Cipher.DECRYPT_MODE);
        byte[] bytes = getCipher().doFinal(Base64.getDecoder().decode(dbData));
        return SerializationUtils.deserialize(bytes);
    }
}
