package de.bahr.config;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

/**
 * Created by michaelbahr on 22/10/16.
 */
@Component
@CrossOrigin
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final boolean DEV_MODE = true;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (shouldAuthenticateAgainstThirdPartySystem(name, password)) {
            // use the credentials and authenticate against the third-party system
            return new UsernamePasswordAuthenticationToken(name, password, Collections.emptyList());
        } else {
            return null;
        }
    }

    private boolean shouldAuthenticateAgainstThirdPartySystem(String name, String sessionId) {

        if (DEV_MODE) {
            allowUntrustedCertificates();
        }

        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get("https://auth:8443/session/authenticated")
                    .queryString("sessionId", sessionId)
                    .asJson();
            String s = jsonResponse.getBody().toString();
            return s.contains("true");
        } catch (UnirestException e) {
            e.printStackTrace();
            return false;
        }

    }

    private void allowUntrustedCertificates() {
        // dev code
        try {
            SSLContext sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                    .build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            Unirest.setHttpClient(httpclient);
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
            System.exit(1);
        }
        // dev code end
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}