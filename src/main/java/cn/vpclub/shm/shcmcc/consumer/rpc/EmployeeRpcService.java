package cn.vpclub.shm.shcmcc.consumer.rpc;


import cn.vpclub.moses.core.model.response.PageResponse;
import cn.vpclub.shm.shcmcc.consumer.api.EmployeeProto;
import cn.vpclub.shm.shcmcc.consumer.api.EmployeeProto.EmployeePageResponse;
import cn.vpclub.shm.shcmcc.consumer.api.EmployeeServiceGrpc.EmployeeServiceBlockingStub;
import cn.vpclub.shm.shcmcc.consumer.entity.Employee;
import cn.vpclub.shm.shcmcc.consumer.model.request.EmployeePageParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static cn.vpclub.moses.utils.grpc.GRpcMessageConverter.fromGRpcMessage;
import static cn.vpclub.moses.utils.grpc.GRpcMessageConverter.toGRpcMessage;

/**
 * rpc层数据传输
 * Created by dell on 2017/9/22.
 */
@Service
@Slf4j
@AllArgsConstructor
public class EmployeeRpcService {
    private EmployeeServiceBlockingStub blockingStub;

    public PageResponse page(EmployeePageParam request) {
        EmployeeProto.EmployeeRequest dto = (EmployeeProto.EmployeeRequest) toGRpcMessage(request, EmployeeProto.EmployeeRequest.newBuilder());
        EmployeePageResponse listResponse = blockingStub.page(dto);
        return (PageResponse) fromGRpcMessage(listResponse, PageResponse.class, Employee.class);
    }


}
