package cn.sunline.security.entity;

/**
 * @Package cn.sunline.security.entity
 * @Author laijp
 * @Date 2019-07-29
 */
public class AntiResult {
    int code;
    String result;

    public int getCode() {
        return code;
    }

    public AntiResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getResult() {
        return result;
    }

    public AntiResult setResult(String result) {
        this.result = result;
        return this;
    }
}
