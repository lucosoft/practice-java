package digital.signature;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.*;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DigiSig {

    private static final String SPEC = "secp256k1";
//    private static final String ALGO = "SHA256withECDSA";
    private static final String ALGO = "SHA256withRSA";

    public static void main(String[] args) {

        byte[] x = new byte[100];
        ByteBuffer buf = ByteBuffer.allocate(100);
        buf.putInt(0x01020304);
        buf.putChar((char) 0x0506);
        buf.putLong(0x0102030405060708L);
        buf.putDouble(3.3e15);
        buf.position(0);
        buf.get(x);
        System.out.println(x[0] + "," + x[1] + "," + x[2] + "," + x[3]);
        buf.position(0);
        System.out.println(Integer.toHexString(buf.getInt()));
        System.out.println(Integer.toHexString(buf.getChar()));
        System.out.println(Long.toHexString(buf.getLong()));
        System.out.println(buf.getDouble());
        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.position(0);
        buf.get(x);
        System.out.println(x[0] + "," + x[1] + "," + x[2] + "," + x[3]);
        buf.position(0);
        System.out.println(Integer.toHexString(buf.getInt()));
        System.out.println(Integer.toHexString(buf.getChar()));
        System.out.println(Long.toHexString(buf.getLong()));
        System.out.println(buf.getDouble());

        try {
            DigiSig digiSig = new DigiSig();
            JSONObject obj = digiSig.sender();
            boolean result = digiSig.receiver(obj);
            System.out.println(result);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private JSONObject sender() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, SignatureException {

//        ECGenParameterSpec ecSpec = new ECGenParameterSpec(SPEC);
        RSAKeyGenParameterSpec ecSpec = new RSAKeyGenParameterSpec(1024, RSAKeyGenParameterSpec.F4);
        KeyPairGenerator g = KeyPairGenerator.getInstance("RSA");
        g.initialize(ecSpec, new SecureRandom());
        KeyPair keypair = g.generateKeyPair();
        PublicKey publicKey = keypair.getPublic();
        PrivateKey privateKey = keypair.getPrivate();

        String plaintext = "Hello";

        //...... sign
        Signature ecdsaSign = Signature.getInstance(ALGO);
        ecdsaSign.initSign(privateKey);
        ecdsaSign.update(plaintext.getBytes("UTF-8"));
        byte[] signature = ecdsaSign.sign();
        String pub = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String sig = Base64.getEncoder().encodeToString(signature);
        System.out.println(sig);
        System.out.println(pub);

        JSONObject obj = new JSONObject();
//        obj.put("publicKey", pub);
//        obj.put("signature", sig);
//        obj.put("message", plaintext);

        obj.put("publicKey", "3082010a02820101009ead146783b24199b8b51e2ca17e3ecb0e835a791a2630a0f363371fa6630231dc7711ef42c8ef2565b6f10fb5ba8bc789be54abf0a9e92569c3fca43cc8b731e4a724d4f22aaeceea477c289ce1408bbad4cbb9bfeaa2d89c99f726fce7130e44e78188bae77faec85d09fdc34481a71baffec0fd45cf2ed00a4f179436804d66bc78feccd7d347f4586658775fcd6f715338b2a54a4689bf2a384df04e5c54ec7d80f3ae9fb69e2edd94d9a323116d4912a69ff8d3e45951a8ba73fdd09b3d2d762c5f198e70d257cc5b48de3b9113c8aff0a16bb7d7581b64c9eb9bbbe918a89e3be7080872daf349f63dcc676385c11c9985fa1ab07c38f67ae8e7b015070203010001");
        obj.put("signature", "MIIBgwYJKoZIhvcNAQcCoIIBdDCCAXACAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHATGCAU8wggFLAgEBMCgwFDESMBAGA1UEAwwJYWVsaXphbGRlAhBff4mPTV67u0cpx6cKDASkMAkGBSsOAwIaBQAwDQYJKoZIhvcNAQEBBQAEggEAQ1eY2xJgDeig65HZD1Oshd/XhVclbO3s7cTby2AtiY1+0ZrCxiunSKuzHiwDvnHHTI9BLkQcw/JNjuHvtyycw/GBw3nBRFyXsrxqnEG/6pxq6duu/VuC5RSsSeVEJ1+18Sk31kDFt+OFdzaOIpnmmk2xOKd1PYZxoU4KSLZAqgDpEIC1iqnA02n8tkOlq5+/+LjqWxv4/o+d6SsJ8mzTFrP1JN1ygzHOmnFFd8FOIHk7b4T6Vm16S3pXyc66Te4FHyb//Epdr7MUWj5HeZKbndB9miUI+wCn+EWJ/SSK28BxQSSCDHVYGWJdbcICFnhwjvLDghGVkh+sAXDoMriLhw==");
        obj.put("message", "my message");

        obj.put("algorithm", ALGO);

        return obj;
    }

    private boolean receiver(JSONObject obj) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException, SignatureException {

        Signature ecdsaVerify = Signature.getInstance(obj.getString("algorithm"));
//        KeyFactory kf = KeyFactory.getInstance("EC");
//        KeyFactory kf = KeyFactory.getInstance("RSA");


//        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(obj.getString("publicKey")));
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(hexStringToByteArray(obj.getString("publicKey")));

//        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        ecdsaVerify.initVerify(publicKey);
        ecdsaVerify.update(obj.getString("message").getBytes("UTF-8"));
        boolean result = ecdsaVerify.verify(Base64.getDecoder().decode(obj.getString("signature")));

        return result;
    }

    private static byte[] hexStringToByteArray(String hexString) {
        byte[] bytes = new byte[hexString.length() / 2];

        for(int i = 0; i < hexString.length(); i += 2){
            String sub = hexString.substring(i, i + 2);
            Integer intVal = Integer.parseInt(sub, 16);
            bytes[i / 2] = intVal.byteValue();
            String hex = "".format("0x%x", bytes[i / 2]);
        }
        return bytes;
    }

}
