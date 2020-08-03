package com.simon.invoker;

import cn.hutool.http.HttpUtil;

import java.util.Map;

/**
 * @data: 2020/8/3 14:17
 * @author: limeng17
 * @version:
 * @description: 远程HTTP方法调用
 */
public class HttpInvoker implements Invoker{

    private String uri;
    private String methodType;
    private Map<String,Object> data;

    public HttpInvoker() {
    }

    public HttpInvoker(String uri, String methodType, Map<String, Object> data) {
        this.uri = uri;
        this.methodType = methodType;
        this.data = data;
    }

    @Override
    public Object invoke() throws Exception {
        if (methodType == null) {
            return null;
        }
        if ("GET".equals(this.methodType.toUpperCase())) {
            return HttpUtil.get(uri, data);
        } else {
            return HttpUtil.post(uri, data);
        }
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
