package cn.vpclub.shm.shcmcc.consumer.config;

import cn.vpclub.shm.shcmcc.consumer.api.UserServiceGrpc;
import cn.vpclub.shm.shcmcc.consumer.api.UserServiceGrpc.UserServiceBlockingStub;

import cn.vpclub.spring.boot.grpc.annotations.GRpcClient;
import io.grpc.ManagedChannel;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * <p>
 *  rpc server连接配置
 * </p>
 *
 * @author yinxicheng
 * @since 2017-09-18
 */

@Configuration
@EnableAutoConfiguration
public class UserGrpcConfiguration {
    @GRpcClient("shcmcc-provider")
    private  ManagedChannel channel;

    @Bean
    public UserServiceBlockingStub userServiceBlockingStub() {
        return UserServiceGrpc.newBlockingStub(channel);
    }

}
