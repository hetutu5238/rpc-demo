package com.megalith.config;

import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;

import com.megalith.repository.ServiceRepository;
import com.megalith.util.Assert;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-28
 * @Time: 16:24
 */
public class RegisterConfig {

    private static Properties register;

    private static NamingService namingService;

    private static volatile boolean init = false;

    public static boolean getInit() {
        return init;
    }

    public static void init(boolean serverFlag) {
        if ( getInit() ) {
            return;
        }
        System.out.println("服务端初始化开始" + new Date());
        Properties properties = new Properties();
        try (InputStream resourceAsStream = RegisterConfig.class.getClassLoader().getResourceAsStream("register.properties");) {
            Assert.notNull(resourceAsStream,"register.properties is required");
            properties.load(resourceAsStream);
            register = properties;
            namingService = NamingFactory.createNamingService(String.valueOf(register.get("register.host")));
            if ( serverFlag ){
                regis();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("服务端初始化结束" + new Date());
        init = true;
    }

    public static Integer getPort() {
        Assert.notNull(register,"RegisterConfig must be initialized");
        return Integer.valueOf(Objects.toString(register.get("server.port")));
    }

    public static NamingService getNamingService() {
        Assert.notNull(namingService,"RegisterConfig must be initialized");
        return namingService;
    }

    /**
     * 注册到注册中心
     */
    private static void regis() {
        try {
            String scanpkgs = String.valueOf(register.get("scanpkgs"));
            Assert.on(StringUtils.isBlank(scanpkgs),"scan service package can't be null");
            initService(scanpkgs.split(","));
            //将服务注册到注册中心
            Set<String> serviceNames = ServiceRepository.getAllServiceName();
            if ( !serviceNames.isEmpty() ) {
                for (String serviceName : serviceNames) {
                    namingService.registerInstance(serviceName , String.valueOf(register.get("server.host")) , Integer.valueOf(Objects.toString(register.get("server.port"))));
                    System.out.println(String.format("已注册服务:%s,服务地址为:%s" , register.get("server.host") , register.get("server.port")));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static void initService(String[] pkgs) {
        List<Class<?>> classes = new ArrayList<>();
        //获取所有包名下的符合条件的服务
        try {
            for (String pkg : pkgs) {
                classes.addAll(getRpcClass(pkg));
            }
            for (Class<?> c : classes) {
                Object o = c.newInstance();
                ServiceRepository.put(c.getAnnotation(com.megalith.anno.RpcService.class).value().getName() , o);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static List<Class<?>> getRpcClass(String pkg) throws ClassNotFoundException {
        List<Class<?>> result = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Assert.notNull(classLoader,"Can't get ClassLoader,Thread: " + Thread.currentThread().getName());
        //获取目录
        String packageName = pkg.replace('.' , '/');
        URL resource = classLoader.getResource(packageName);
        Assert.notNull(resource,"Can't get package,package doesn't exist: " + packageName);
        String fileUrl = resource.getFile();
        File f = new File(fileUrl);
        if ( f.exists() ) {
            File[] files = f.listFiles();
            if ( files != null ) {
                for (File file : files) {
                    String fileName;
                    //如果是文件
                    if ( !file.isDirectory() && (fileName = file.getName()).endsWith(".class") ) {
                        Class<?> c = Class.forName(pkg + "." + fileName.substring(0 , fileName.length() - 6));
                        if ( c.getAnnotation(com.megalith.anno.RpcService.class) != null ) {
                            result.add(c);
                        }
                    } else if ( file.isDirectory() ) {
                        List<Class<?>> rpcClass = getRpcClass(pkg + "." + file.getName());
                        if ( rpcClass != null && !rpcClass.isEmpty() ) {
                            result.addAll(rpcClass);
                        }
                    }
                }
            }
        } else {
            throw new RuntimeException("Can't get package,package doesn't exist: " + pkg);
        }
        return result;

    }


}
