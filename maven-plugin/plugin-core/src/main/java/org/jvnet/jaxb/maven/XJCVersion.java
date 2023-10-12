package org.jvnet.jaxb.maven;

public class XJCVersion {
    public static final XJCVersion UNDEFINED = new XJCVersion(null);
    private String raw = "UNDEFINED";
    private int major;
    private int minor;
    private int bugfix;

    public XJCVersion(String version) {
        if (version != null) {
            this.raw = version;
            int indexOfSnapshot = version.indexOf("-SNAPSHOT");
            if (indexOfSnapshot >= 0) {
                version = version.substring(0, indexOfSnapshot);
            }
            String[] split = version.split("\\.");
            if (split.length >= 3) {
                major = Integer.valueOf(split[0]);
                minor = Integer.valueOf(split[1]);
                bugfix = Integer.valueOf(split[2]);
            }
        }
    }

    public String getRaw() {
        return raw;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getBugfix() {
        return bugfix;
    }

    public boolean isKnown() {
        return !(this.major == 0 && this.minor == 0 && this.bugfix == 0);
    }

    public boolean gte(int major, int minor, int bugfix) {
        return this.major > major || (this.major == major && this.minor > minor) || (this.major == major && this.minor == minor && this.bugfix >= bugfix);
    }

    public boolean gt(int major, int minor, int bugfix) {
        return this.major > major || (this.major == major && this.minor > minor) || (this.major == major && this.minor == minor && this.bugfix > bugfix);
    }

    public boolean lte(int major, int minor, int bugfix) {
        return this.major < major || (this.major == major && this.minor < minor) || (this.major == major && this.minor == minor && this.bugfix <= bugfix);
    }

    public boolean lt(int major, int minor, int bugfix) {
        return this.major < major || (this.major == major && this.minor < minor) || (this.major == major && this.minor == minor && this.bugfix < bugfix);
    }
}
