package com.megalith.support;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 10:52
 */
public class RpcRequest implements Serializable {

    private static final AtomicLong REQUEST_ID = new AtomicLong(0);

    private long id;

    private String serviceName;

    private String methodName;

    private Class<?>[] paramsTypes;

    private Object[] params;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void newId() {
        this.id = REQUEST_ID.getAndIncrement();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParamsTypes() {
        return paramsTypes;
    }

    public void setParamsTypes(Class<?>[] paramsTypes) {
        this.paramsTypes = paramsTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "id=" + id +
                ", serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                '}';
    }
}
