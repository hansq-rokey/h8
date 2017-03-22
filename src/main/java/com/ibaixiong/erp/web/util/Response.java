package com.ibaixiong.erp.web.util;


/**
 * created by 重剑 on 2015/8/3
 * 凡是标注了 @ResponseBody 的方法都是返回这个类的实例
 *
 */
public class Response {

    private Boolean success = Boolean.TRUE;
    private String message;

    private Object result;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
