package org.nero.tabby.conf;

/**
 * author： nero
 * email: nerosoft@outlook.com
 * data: 16-9-29
 * time: 下午7:45.
 */
public final class Confkey {
    private  String confkey;

    public Confkey(String confkey) {
        this.confkey =confkey;
    }

    public Confkey() {
    }

    public String getConfkey() {
        return confkey;
    }

    public void setConfkey(String confkey) {
        this.confkey = confkey;
    }

    @Override
    public String toString() {
        return "Confkey{" +
                "confkey='" + confkey + '\'' +
                '}';
    }

    public boolean equals(Confkey obj) {
        return obj.getConfkey().equals(this.getConfkey());
    }
}
