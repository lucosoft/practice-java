package digital.signature;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class DigitalSignature {
    public static void main(String[] args) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnrecoverableKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

        // firma y validacion con certificados locales
        KeyStore keyStore = KeyStore.getInstance("Windows-MY");
        keyStore.load(null, null);

        X509Certificate cert = (X509Certificate) keyStore.getCertificate("aelizalde");
        PublicKey publicKey = cert.getPublicKey();

        String base64CryptedMessage = "MIIBgwYJKoZIhvcNAQcCoIIBdDCCAXACAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHATGCAU8wggFLAgEBMCgwFDESMBAGA1UEAwwJYWVsaXphbGRlAhBJRdjkjjLJskC+CIudUaoPMAkGBSsOAwIaBQAwDQYJKoZIhvcNAQEBBQAEggEAaCk/sblVxl3TjpXuctGxjdpnCrK4vUpgOYVH7bZ0jryxZDuViaV7qUCYQHYG944qHdKJHuzUMQFJuILwtDf4gke9Oban/IsPnrrRecBc5FrzeIZk9NPcZPAxroaTBFdU6rQrBwPv35e4WXFIXalsCn0ce9LX5668ADeDnND47xhMr4patxP/0nz3QFiHlTneQ7BOUzzj1TgJOnw8Cz+wkzEKhMlujXcMxVXjh5skWxEkBLg8QRZDLBXlRw85qVjMZNxlIrH8S18otkRBF9+4qUy51zM8VR6mUeLOJrYeZECPrkIcZhyf2eN3OyWABg4jlb0vw0bIR4oX7nF4j9ZMcg==";
        String message = "probando";

        System.out.println("digital_signature.DigitalSignature de message: " + message);

        PrivateKey privateKey = (PrivateKey) keyStore.getKey("aelizalde", null);

        Signature instance = Signature.getInstance("SHA256withRSA");
        instance.initSign(privateKey, new SecureRandom());
        instance.update(message.getBytes("UTF-8"));
        byte[] signedBytes = instance.sign();

        instance.initVerify(publicKey);
        instance.update(message.getBytes("UTF-8"));
        boolean verified = instance.verify(signedBytes);
        System.out.println("verified: " + verified);

        byte[] cryptedSignedBytes = Base64.getDecoder().decode(base64CryptedMessage.getBytes("UTF-8"));
        System.out.println("cryptedMessage: " + cryptedSignedBytes);

        // validacion con certificado de request
        String publicKeyB64 = "MIIDGDCCAgCgAwIBAgIQSUXY5I4yybJAvgiLnVGqDzANBgkqhkiG9w0BAQsFADAUMRIwEAYDVQQDDAlhZWxpemFsZGUwHhcNMjEwOTEwMjA1NzAzWhcNMjIwOTEwMjExNzAzWjAUMRIwEAYDVQQDDAlhZWxpemFsZGUwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQD3pNlSkbYaqDITsu1xGUhYHUNiVtWGhAS7z2G0nOmRHGT6805s4JF/ww83eIIB0nRwFJk5VllHJul6Yci5OideAnkX/OyJp/qqAPROL+gdslf411rfJoamjc0JW0njaGGj17Dx5nc+vBHSHCP7wBCJ9QAu82eTEj6WbxFSpPMIgrUBqHDu2Ao+md2bqBHoLTBH97OZXdsen6tmnPjLNLa/B6f6w7sP3VTLqqE3VPei/3Q3bZ4j4GBrGCLydAhEOP72+NdSCLT1JNjnd7p8RqtGrKp7I63XohzLvasqj0NQO8FNkwOh4C9Eyo4bwvjbAC9xqIQXlOPr9Jf9rQR4KyUhAgMBAAGjZjBkMA4GA1UdDwEB/wQEAwIFoDAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwEwFAYDVR0RBA0wC4IJYWVsaXphbGRlMB0GA1UdDgQWBBQabKPFIDkbVJgGQwL5IDdSbLJodzANBgkqhkiG9w0BAQsFAAOCAQEAezsBuKdsb/hHjElQoe8hhzs0s+gLpi+PyiIudCzy+PQZLPdx9h1PSh6W0jT9PclvHSRx5VyarWZceSkINp5HinTjhhLmRxJbKh2jYy0rkhL9CpekF5Dc+dd3su7pbAQF4ZcCci/RdeXZWfrSWXPnOx/IrtLjGuDPKUe3Tjnb3AAIw03/DNoQIIL/n5oqurDI0+HqmpWobDjCXlX7j9WW650IsMC8xb4+0vcfTlK/6DCH3QdzC3EnVXtD+wXms3ldJPFGNRhkkf+Fa13L1/Xg+ZUaXtr/OdEa0+nmmCcHWIEy60qWCn4WYrzDWt4XQoVPRcL/5DXsoYexSUQKH7wfnA==";
        RSAPublicKey publicKeyFromRequest = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate certFromRequest = cf.generateCertificate(new ByteArrayInputStream(Base64.getDecoder().decode(publicKeyB64)));

            publicKeyFromRequest = (RSAPublicKey) certFromRequest.getPublicKey();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Signature instanceFromRequest = Signature.getInstance("SHA256withRSA");

        instanceFromRequest.initVerify(publicKeyFromRequest);

        instanceFromRequest.update(message.getBytes("UTF-8"));
        boolean verified5 = false;
        try {
            verified5 = instanceFromRequest.verify(decryptBytes(cryptedSignedBytes));
        }catch (Exception e){
            System.out.println("verified Exception:");
        }
        System.out.println("verified: " + verified5);

        System.out.println("fin de digital_signature.DigitalSignature");
    }

    static byte[] decryptBytes(byte[] cryptedSignedBytes){
        return null;
    }
}
