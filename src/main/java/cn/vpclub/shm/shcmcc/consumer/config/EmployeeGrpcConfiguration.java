package cn.vpclub.shm.shcmcc.consumer.config;

import cn.vpclub.shm.shcmcc.consumer.api.EmployeeServiceGrpc;
import cn.vpclub.shm.shcmcc.consumer.api.EmployeeServiceGrpc.EmployeeServiceBlockingStub;
import cn.vpclub.spring.boot.grpc.annotations.GRpcClient;
import io.grpc.ManagedChannel;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rpc server连接配置
 * Created by dell on 2017/9/22.
 */
@Configuration
@EnableAutoConfiguration
public class EmployeeGrpcConfiguration {

    @GRpcClient("shcmcc-provider")
    private ManagedChannel channel;

    @Bean
    public EmployeeServiceBlockingStub employeeServiceBlockingStub() {
        return EmployeeServiceGrpc.newBlockingStub(channel);
    }

}
