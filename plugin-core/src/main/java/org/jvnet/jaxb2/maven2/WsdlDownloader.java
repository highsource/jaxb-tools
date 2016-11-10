
package org.jvnet.jaxb2.maven2;

import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.codehaus.plexus.util.Base64.encodeBase64;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Created by Lucas Le on 11/10/16.
 */
public class WsdlDownloader {
    private BasicAuthentication basicAuthentication;

    public BasicAuthentication getBasicAuthentication() {
        return basicAuthentication;
    }

    public void setBasicAuthentication(BasicAuthentication basicAuthentication) {
        this.basicAuthentication = basicAuthentication;
    }

    public WsdlDownloader(BasicAuthentication basicAuthentication) {
        this.basicAuthentication = basicAuthentication;
    }

    public void execute() throws MojoExecutionException {
        for (int i = 0; i < basicAuthentication.getUrls().length; i++) {
            String wsdlUrl = basicAuthentication.getUrls()[i];
            System.out.println("Processing wsdlUrl: " + wsdlUrl);
            download(wsdlUrl, basicAuthentication.getUsername(), basicAuthentication.getPassword(), basicAuthentication.getDownloadDirectory(), i);
        }
    }

    private void download(String wsdlUrl, String username, String password, String downloadDirectory, int index) throws MojoExecutionException {
        if (null != wsdlUrl) {
            try {
                String encoded = new String(encodeBase64((username + ":" + password).getBytes()));

                HttpURLConnection connection = initConnection(wsdlUrl, encoded);

                writeStringToFile(
                        createWsdlFile(downloadDirectory, basicAuthentication.getWsdlPrefixName(), index),
                        IOUtils.toString(connection.getInputStream(), Charset.defaultCharset()),
                        Charset.defaultCharset()
                );

            } catch (IOException e) {
                throw new MojoExecutionException("Download WSDL URL" + wsdlUrl, e);
            }
        }
    }

    private File createWsdlFile(String downloadDirectory, String wsdlPrefixName, int index) {
        wsdlPrefixName = StringUtils.isEmpty(wsdlPrefixName) ? "webservice_" : wsdlPrefixName + "_";
        return new File(downloadDirectory + File.separator + wsdlPrefixName + (index + 1) + ".wsdl");
    }

    private HttpURLConnection initConnection(String wsdlUrl, String encoded) throws IOException {
        URL wsdl = new URL(wsdlUrl);

        HttpURLConnection connection = (HttpURLConnection) wsdl.openConnection();

        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Basic " + encoded);

        return connection;
    }

}