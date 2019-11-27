package com.megalith.server;

import com.megalith.task.RpcTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * RPC-SERVER
 *
 * @author: zhoum
 * @Date: 2019-11-26
 * @Time: 11:50
 */
public class RpcServer {
    /**
     *
     */
    public void start(int port,String[] pkgs) {
        Executor executor = Executors.newFixedThreadPool(5);
        System.out.println(String.format("系统系统成功,端口号为：%s",port));
        try (ServerSocket server = new ServerSocket(port)) {
            while ( true ){
                Socket socket = server.accept();
                Map<String, Object> service = getService(pkgs);
                executor.execute(new RpcTask(socket,service));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String,Object> getService(String[] pkgs ){
        Map<String,Object> services = new HashMap<>();
        List<Class<?>> classes = new ArrayList<>();
        //获取所有包名下的符合条件的服务
        try {
            for (String pkg : pkgs) {
                classes.addAll(getRpcClass(pkg));
            }
            for (Class<?> c : classes) {
                Object o = c.newInstance();
                services.putIfAbsent(c.getAnnotation(com.megalith.anno.RpcService.class).value().getName(),o);
            }
            return services;

        }catch (Exception e){
            throw new RuntimeException(e);
        }


    }

    private List<Class<?>> getRpcClass(String pkg) throws ClassNotFoundException {
        List<Class<?>> result = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if ( classLoader == null ){
            throw new RuntimeException("Can't get ClassLoader,Thread: "+Thread.currentThread().getName());
        }
        //获取目录
        String packageName = pkg.replace('.', '/');
        URL resource = classLoader.getResource(packageName);
        if ( resource == null ){
            throw new RuntimeException("Can't get package,package doesn't exist: "+packageName);
        }
        String fileUrl = resource.getFile();
        File f = new File(fileUrl);
        if ( f.exists() ){
            File[] files = f.listFiles();
            if ( files != null ){
                for (File file : files) {
                    String fileName;
                    //如果是文件
                    if ( !file.isDirectory() && (fileName =file.getName()).endsWith(".class") ){
                        Class<?> c = Class.forName(pkg + "." + fileName.substring(0 , fileName.length() - 6));
                        if ( c.getAnnotation(com.megalith.anno.RpcService.class) != null ){
                            result.add(c);
                        }
                    }else if ( file.isDirectory() ){
                        List<Class<?>> rpcClass = getRpcClass(pkg + "." + file.getName());
                        if ( rpcClass != null && ! rpcClass.isEmpty() ){
                            result.addAll(rpcClass);
                        }
                    }
                }
            }
        }else {
            throw new RuntimeException("Can't get package,package doesn't exist: "+pkg);
        }
        return result;

    }
}
