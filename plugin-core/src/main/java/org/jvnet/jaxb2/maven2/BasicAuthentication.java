
package org.jvnet.jaxb2.maven2;

/**
 * Created by Lucas Le on 11/9/16.
 */
public class BasicAuthentication {
    private String username;
    private String password;
    private String downloadDirectory;
    private String wsdlPrefixName;
    private String[] urls = new String[0];

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDownloadDirectory() {
        return downloadDirectory;
    }

    public void setDownloadDirectory(String downloadDirectory) {
        this.downloadDirectory = downloadDirectory;
    }

    public String getWsdlPrefixName() {
        return wsdlPrefixName;
    }

    public void setWsdlPrefixName(String wsdlPrefixName) {
        this.wsdlPrefixName = wsdlPrefixName;
    }
}