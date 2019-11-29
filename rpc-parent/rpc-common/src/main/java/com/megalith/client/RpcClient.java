package com.megalith.client;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.megalith.config.RegisterConfig;
import com.megalith.repository.ServiceConnectionFactory;
import com.megalith.support.RpcRequest;
import com.megalith.repository.ClientRepository;
import com.megalith.support.RpcFuture;
import com.megalith.util.Assert;
import io.netty.channel.Channel;


/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 11:41
 */
public class RpcClient {


    public static Object transfer(RpcRequest rpcRequest) {
        try {
            //配置中心已实现负载均衡
            RegisterConfig.init(false);
            Instance ins = RegisterConfig.getNamingService().selectOneHealthyInstance(rpcRequest.getServiceName());
            Assert.notNull(ins,"service not be find :"+rpcRequest.getServiceName());
            String key = ins.getIp() + ":" + ins.getPort();
            Channel c = ClientRepository.getChannel(key);
            if ( c == null ) {
                String[] split = key.split(":");
                c = ServiceConnectionFactory.createConnection(split[ 0 ] , Integer.valueOf(split[ 1 ]));
            }
            Long id = rpcRequest.getId();
            RpcFuture rpcFuture = new RpcFuture(id , rpcRequest);
            RpcFuture.putFuture(id , rpcFuture);
            c.writeAndFlush(rpcRequest);
            return rpcFuture.getResponse();
        } catch (NacosException e) {
            throw new RuntimeException("register connection error" , e);
        } catch (InterruptedException e) {
            throw new RuntimeException("register connection error" , e);
        }

    }
}
