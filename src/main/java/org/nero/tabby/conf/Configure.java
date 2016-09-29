package org.nero.tabby.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * author： nero
 * email: nerosoft@outlook.com
 * data: 16-9-29
 * time: 下午7:07.
 */
public class Configure {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public final String confFilePath = System.getProperty("user.dir") + File.separator + "../conf/tabby.conf";

    private Map<Confkey,Confvalue> confs;

    public void init(){
        getConf();
    }

    public Map<Confkey, Confvalue> getConfs() {
        return confs;
    }

    public void setConfs(Map<Confkey, Confvalue> confs) {
        this.confs = confs;
    }

    public String getConfItem(String confName){
        String conf="";
        for(Confkey confkey:confs.keySet()){
            if (confkey.equals(new Confkey(confName))){
                conf =  confs.get(confkey).getConfvalue();
            }
        }
        return conf;
    }

    private Map<Confkey,Confvalue> getConf() {
        confs = new HashMap<Confkey,Confvalue>();

        String confContent = readFile(confFilePath);

        String[] confStrings = confContent.trim().split("\n");
        for(String confString:confStrings){
            if(!"".equals(confString)) {
                String[] confItems = confString.trim().split(":");
                if (!"#".equals(confItems[0].substring(0, 1))) {
                    confs.put(new Confkey(confItems[0].trim()),new Confvalue(confItems[1].trim()));
                }
            }
        }
        return confs;
    }

    public String readFile(String filename) {
        File src = null;
        int totalReaded = 0;
        int readed = 0;
        FileInputStream reader = null;
        byte data[];
        try {
            src = new File(filename);
            if (!src.exists()) {
                throw new IllegalArgumentException("读取文件失败：指定文件" + filename
                        + "不存在。");
            }
            if (src.isDirectory()) {
                throw new IllegalArgumentException("读取文件失败：" + filename
                        + "是目录而不是文件。");
            }

            Long len = src.length();

            data = new byte[len.intValue()];
            for (reader = new FileInputStream(filename); reader.available() > 0; ) {
                readed = reader.read(data, totalReaded,
                        len.intValue() - totalReaded);
                totalReaded += readed;
            }
            return new String(data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(),e.getCause());
            return "";
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(),e.getCause());
            return "";
        } catch (IOException e) {
            logger.error(e.getMessage(),e.getCause());
            return "";
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(),e.getCause());
                }
            }
        }
    }
}
