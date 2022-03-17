package digital_signature;

import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.util.Store;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;

import static org.junit.Assert.assertTrue;

public class BouncyCastleService {

    public static boolean verifSignData(String textToSign, String digitalSignature, String base64Certificate) {

        System.out.println("Verifying Sign Data");

        CMSSignedData s = appendTextToSign(textToSign, digitalSignature);

        s = appendCertificate(base64Certificate, s);

        SignerInformation signer = getSigner(s);
        X509CertificateHolder certHolder = getCertHolder(signer, s);

        String certName = IETFUtils.valueToString(certHolder.getIssuer().getRDNs(BCStyle.CN)[0].getFirst().getValue());

        boolean verifResult = false;
        try {
            verifResult = signer.verify(new JcaSimpleSignerInfoVerifierBuilder().build(certHolder));
        }
        catch (Exception e){
            System.out.println("Verifying Sign Data Exception: " + e.getMessage());
        }
        return verifResult;
    }

    private static X509CertificateHolder getCertHolder(SignerInformation signer, CMSSignedData s){
        try {
            Store certs = s.getCertificates();
            Collection<X509CertificateHolder> certCollection = certs.getMatches(signer.getSID());
            Iterator<X509CertificateHolder> certIt = certCollection.iterator();
            return certIt.next();
        } catch (Exception e) {
            System.out.println("getCertHolder Exception: " + e.getMessage());
        }
        return null;
    }

    private static SignerInformation getSigner(CMSSignedData s){
        try {
            SignerInformationStore signers = s.getSignerInfos();
            Collection<SignerInformation> c = signers.getSigners();
            return c.iterator().next();
        } catch (Exception e) {
            System.out.println("getSigner Exception: " + e.getMessage());
        }
        return null;
    }

    private static CMSSignedData appendTextToSign(String textToSign, String digitalSignature) {
        try{
            byte[] signedData = Base64.getDecoder().decode(digitalSignature.getBytes("UTF-8"));
            CMSProcessable cmsProcesableContent = new CMSProcessableByteArray(textToSign.getBytes(StandardCharsets.UTF_8));
            return new CMSSignedData(cmsProcesableContent, signedData);
        } catch (Exception e){
            System.out.println("appendTextToSign Exception: " + e.getMessage());
        }
        return null;
    }

    private static CMSSignedData appendCertificate(String base64Certificate, CMSSignedData cmsSignedData) {
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(Base64.getDecoder().decode(base64Certificate)));
            Collection certificateValues = Collections.singletonList(certificate);
            Store certStore = cmsSignedData.getCertificates();
            Store crlStore = cmsSignedData.getCRLs();

            if (certificateValues != null && !certificateValues.isEmpty()) {
                Set<Certificate> newCerts =
                        new HashSet<Certificate>(certificateValues); // 'Set' to avoid duplicates
                certStore = new JcaCertStore(newCerts);
            }
            return CMSSignedData.replaceCertificatesAndCRLs(
                    cmsSignedData, certStore, cmsSignedData.getAttributeCertificates(), crlStore);
        } catch (Exception e) {
            System.out.println("appendTextToSign Exception: " + e.getMessage());
        }
        return null;
    }

    @Test
    public void verifSignData() {
        String CERT_PUBLIC_KEY = "3082010A0282010100E378FACE9F2230D86C06328E3002855B7C7FF6245FD4A89DF2CEF5EDA5DCC8720A044FA80D4179205AFB019B0E660BB265A09F1B15E7ADF14E5E559F7CC84C9802019B8D266E87F44BDF142F5F4FA6BB1A2B5A20D98B0184CD296C657349E9BADDD96C485455680C4880627BC07430A4F705E36A2A192AB21F0D123A8147B81149BAA34C27CB83A57E06174E4D6B8C897AC2DC8CC7E11D06B3931CE3D0E963DC794CDC8D10E9D94ADFBFB722BFF0BB3F478B11A35414327E81FE4393EF47D3B2F3785978FF4ED985A738853EFF98CBD62D6614D53E7E736A0A179A64BCA8B76499442075D71BA8314E39B5493D72A32ED129DF8CE9F3BDA4F4AFE37C8F8C69E50203010001";
        String DIGITAL_SIGNATURE = "MIIErwYJKoZIhvcNAQcCoIIEoDCCBJwCAQExCzAJBgUrDgMCGgUAMBcGCSqGSIb3DQEHAaAKBAhwcm9iYW5kb6CCAxwwggMYMIICAKADAgECAhBff4mPTV67u0cpx6cKDASkMA0GCSqGSIb3DQEBCwUAMBQxEjAQBgNVBAMMCWFlbGl6YWxkZTAeFw0yMTA4MDQxNDAwMTlaFw0yMjA4MDQxNDIwMTlaMBQxEjAQBgNVBAMMCWFlbGl6YWxkZTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJ6tFGeDskGZuLUeLKF+PssOg1p5GiYwoPNjNx+mYwIx3HcR70LI7yVltvEPtbqLx4m+VKvwqeklacP8pDzItzHkpyTU8iquzupHfCic4UCLutTLub/qoticmfcm/OcTDkTngYi653+uyF0J/cNEgacbr/7A/UXPLtAKTxeUNoBNZrx4/szX00f0WGZYd1/Nb3FTOLKlSkaJvyo4TfBOXFTsfYDzrp+2ni7dlNmjIxFtSRKmn/jT5FlRqLpz/dCbPS12LF8ZjnDSV8xbSN47kRPIr/Cha7fXWBtkyeubu+kYqJ475wgIctrzSfY9zGdjhcEcmYX6GrB8OPZ66OewFQcCAwEAAaNmMGQwDgYDVR0PAQH/BAQDAgWgMB0GA1UdJQQWMBQGCCsGAQUFBwMCBggrBgEFBQcDATAUBgNVHREEDTALgglhZWxpemFsZGUwHQYDVR0OBBYEFNkAj9ljPe/rcnEz2GjmsbYgSWQPMA0GCSqGSIb3DQEBCwUAA4IBAQBAiRtmMHbgqHTLkGB32QCy/aKYDo9EatSJ9vwhVKyf5ph3YDw3YQ2ZA7tm+yOnIxrzl20F9t3kt33mxbZfG5iSRhzFMQ0U8MGhtyl2A7wH5BkUYWdm5XKMllbtt957a9Ho4HospcrxWFzb52orcfcq02VnvWwXJA5zslcsjoG3rVJx56JHDiYp+1qVyzc8suvTvOcju5N16G+X2BbEGeNHEZnue6jfisBJdvvXqfUB6eXSybOaYUAG8s5057Kg0n2SWnv+Ugg5gJ4xBRk+Bnm8A+sjJ7TDgDyOd9z4N3g9XF0f2EJgt6qgzj12ZpbuyfzTRc2uJn0ZJcKWokPDCgGKMYIBTzCCAUsCAQEwKDAUMRIwEAYDVQQDDAlhZWxpemFsZGUCEF9/iY9NXru7RynHpwoMBKQwCQYFKw4DAhoFADANBgkqhkiG9w0BAQEFAASCAQAwy4TInEPtygVymh28ksu+rgcGR1K8uTMJrdFimPf+J/jSIq3rHbV/ciIXk32iP8VsjxpJWNIPsjNp/Ui8+mn7FRkSq2MnIIc/FRBqlkvAzOMYlwd+WAtpUaCaTTBsx7Dx36mM8lGY/w19VUZ5mE/p36GOEGFm4ymFs1UQlyf/d3XPOVbE+OYRBxejyb7DQcd9wH6L3ykGivpO9wHFp7q7lKEW8n9M/wk4GvnEL+ZW3hI64yceFiP2SQs5cOPW7lnYtViOAWty+2cZ/sTGgc1GFtaoD1bFO9NWJbMP7Yw+5WOkc5khfEZf3iIkaJJbcwzlVN8t1tJGGZYCJeOTRdJa";
        String TEXT_TO_SIGN = "probando";
        String BASE64_CERTIFICATE = "MIIDGDCCAgCgAwIBAgIQX3+Jj01eu7tHKcenCgwEpDANBgkqhkiG9w0BAQsFADAUMRIwEAYDVQQDDAlhZWxpemFsZGUwHhcNMjEwODA0MTQwMDE5WhcNMjIwODA0MTQyMDE5WjAUMRIwEAYDVQQDDAlhZWxpemFsZGUwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCerRRng7JBmbi1Hiyhfj7LDoNaeRomMKDzYzcfpmMCMdx3Ee9CyO8lZbbxD7W6i8eJvlSr8KnpJWnD/KQ8yLcx5Kck1PIqrs7qR3wonOFAi7rUy7m/6qLYnJn3JvznEw5E54GIuud/rshdCf3DRIGnG6/+wP1Fzy7QCk8XlDaATWa8eP7M19NH9FhmWHdfzW9xUziypUpGib8qOE3wTlxU7H2A866ftp4u3ZTZoyMRbUkSpp/40+RZUai6c/3Qmz0tdixfGY5w0lfMW0jeO5ETyK/woWu311gbZMnrm7vpGKieO+cICHLa80n2PcxnY4XBHJmF+hqwfDj2eujnsBUHAgMBAAGjZjBkMA4GA1UdDwEB/wQEAwIFoDAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwEwFAYDVR0RBA0wC4IJYWVsaXphbGRlMB0GA1UdDgQWBBTZAI/ZYz3v63JxM9ho5rG2IElkDzANBgkqhkiG9w0BAQsFAAOCAQEAQIkbZjB24Kh0y5Bgd9kAsv2imA6PRGrUifb8IVSsn+aYd2A8N2ENmQO7ZvsjpyMa85dtBfbd5Ld95sW2XxuYkkYcxTENFPDBobcpdgO8B+QZFGFnZuVyjJZW7bfee2vR6OB6LKXK8Vhc2+dqK3H3KtNlZ71sFyQOc7JXLI6Bt61SceeiRw4mKftalcs3PLLr07znI7uTdehvl9gWxBnjRxGZ7nuo34rASXb716n1Aenl0smzmmFABvLOdOeyoNJ9klp7/lIIOYCeMQUZPgZ5vAPrIye0w4A8jnfc+Dd4PVxdH9hCYLeqoM49dmaW7sn800XNriZ9GSXClqJDwwoBig==";

        assertTrue(verifSignData(TEXT_TO_SIGN, DIGITAL_SIGNATURE, BASE64_CERTIFICATE));
    }
}
