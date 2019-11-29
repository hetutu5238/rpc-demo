package com.megalith.netty.serilaze;

import com.alibaba.fastjson.JSONObject;

import java.nio.charset.Charset;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-28
 * @Time: 11:22
 */
public class JsonSerilizer implements Serilazier {

    @Override
    public byte[] serialize(Object msg) {
        return JSONObject.toJSONString(msg).getBytes(Charset.defaultCharset());
    }

    @Override
    public <T> T deserialize(byte[] bytes , Class<T> clz) {
        return JSONObject.parseObject(new String(bytes , Charset.defaultCharset()) , clz);
    }
}
