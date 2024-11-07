package io.jenkins.plugins.okhttp.api;

import io.jenkins.plugins.okhttp.api.internals.JenkinsProxyAuthenticator;
import io.jenkins.plugins.okhttp.api.internals.JenkinsProxySelector;
import jenkins.model.Jenkins;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;

import java.util.List;

/**
 * This class allows to take into consideration if Jenkins is running behind a proxy or not and return a proper {@link OkHttpClient}
 * client.
 */
public class JenkinsOkHttpClient {

    private JenkinsOkHttpClient() {
    }

    /**
     * Generates a new builder for the given base client.
     * It applies the current {@link Jenkins#proxy} configuration.
     *
     * @return a builder to configure the client
     */
    public static OkHttpClient.Builder newClientBuilder(OkHttpClient httpClient) {
        OkHttpClient.Builder reBuild = httpClient.newBuilder();
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).allEnabledTlsVersions().allEnabledCipherSuites().build();
        reBuild.connectionSpecs(List.of(spec));
        reBuild.proxyAuthenticator(new JenkinsProxyAuthenticator());
        reBuild.proxySelector(new JenkinsProxySelector());
        return reBuild;
    }
}
