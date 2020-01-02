package com.rpc.client;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.rpc.config.RegisterConfig;
import com.rpc.repository.ServiceConnectionFactory;
import com.rpc.support.RpcRequest;
import com.rpc.repository.ClientRepository;
import com.rpc.support.RpcFuture;
import com.rpc.util.Assert;
import io.netty.channel.Channel;

import java.util.List;


/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 11:41
 */
public class RpcClient {


    public static Object transfer(RpcRequest rpcRequest) {
        try {

            RegisterConfig.init(false);
            //服务注册中心拿服务 服务中心已实现负载均衡  也可以自己实现负载均衡(轮询/hash方式)
            //List<Instance> instances = RegisterConfig.getNamingService().selectInstances(rpcRequest.getServiceName() , true);
            //int one = rpcRequest.hashCode()%instances.size();
            //Instance ins = instances.get(one);
            Instance ins = RegisterConfig.getNamingService().selectOneHealthyInstance(rpcRequest.getServiceName());
            Assert.notNull(ins,"service not be find :"+rpcRequest.getServiceName());
            String key = ins.getIp() + ":" + ins.getPort();
            Channel c = ClientRepository.getChannel(key);
            if ( c == null ) {
                //如果系统没有缓存这个服务的连接则创建
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
