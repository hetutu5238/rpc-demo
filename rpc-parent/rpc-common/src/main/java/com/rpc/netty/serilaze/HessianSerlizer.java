package com.rpc.netty.serilaze;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2020-01-02
 * @Time: 10:01
 */
public class HessianSerlizer implements Serilazier {
    @Override
    public byte[] serialize(Object msg) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Hessian2Output hessian2Output = new Hessian2Output(out);
        try {
            hessian2Output.writeObject(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                hessian2Output.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return out.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes , Class<T> clz) {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        Hessian2Input hessian2Input = new Hessian2Input(in);
        try {
            T result = (T)hessian2Input.readObject(clz);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                hessian2Input.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
