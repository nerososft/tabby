package org.nero.tabby.conf;

/**
 * author： nero
 * email: nerosoft@outlook.com
 * data: 16-9-29
 * time: 下午7:46.
 */
public final class Confvalue {
    private String confvalue;

    public Confvalue(String confvalue) {
        this.confvalue = confvalue;
    }

    public Confvalue() {
    }

    public String getConfvalue() {
        return confvalue;
    }

    public void setConfvalue(String confvalue) {
        this.confvalue = confvalue;
    }

    @Override
    public String toString() {
        return "Confvalue{" +
                "confvalue='" + confvalue + '\'' +
                '}';
    }
}
