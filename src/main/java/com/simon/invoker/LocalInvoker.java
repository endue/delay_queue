package com.simon.invoker;

import java.lang.reflect.Method;

/**
 * @data: 2020/7/31 17:51
 * @author: limeng17
 * @version:
 * @description: 本地方法调用
 */
public class LocalInvoker implements Invoker{

    private Object instance;
    private String method;
    private Object[] value;

    public LocalInvoker() {
    }

    public LocalInvoker(Object instance, String method, Object... value) {
        this.setInstance(instance);
        this.setMethod(method);
        this.setValue(value);
    }

    @Override
    public Object invoke() throws Exception {
        if (this.instance == null || this.method == null) {
            return null;
        }
        return this.getReflectMethod().invoke(this.instance, this.value);
    }

    private Method getReflectMethod() throws Exception {
        String methodName = this.instance.getClass().getName() + "." + this.method;

        for (Method method : this.instance.getClass().getMethods()) {
            if (method.getName().equals(this.method)) {
                return method;
            }
        }
        throw new Exception("no such method: " + methodName);
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setValue(Object[] value) {
        this.value = value;
    }
}
