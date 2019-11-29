package com.megalith.support;

import java.io.Serializable;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 10:52
 */
public class RpcResponse implements Serializable {


    private Throwable error;

    private Object response;

    private long id;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }


}
