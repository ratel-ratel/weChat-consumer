package cn.vpclub.shm.shcmcc.consumer.rpc;

import cn.vpclub.shm.shcmcc.consumer.api.RoleProto;
import cn.vpclub.shm.shcmcc.consumer.api.RoleProto.RoleDTO;
import cn.vpclub.shm.shcmcc.consumer.api.RoleProto.RoleResponse;
import cn.vpclub.shm.shcmcc.consumer.api.RoleProto.RoleListResponse;
import cn.vpclub.shm.shcmcc.consumer.api.RoleProto.RolePageResponse;
import cn.vpclub.shm.shcmcc.consumer.api.RoleServiceGrpc;
import cn.vpclub.shm.shcmcc.consumer.api.RoleServiceGrpc.RoleServiceBlockingStub;
import cn.vpclub.shm.shcmcc.consumer.entity.Role;
import cn.vpclub.shm.shcmcc.consumer.model.request.RolePageParam;

import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.PageDataResponse;
import cn.vpclub.moses.core.model.response.PageResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static cn.vpclub.moses.utils.grpc.GRpcMessageConverter.fromGRpcMessage;
import static cn.vpclub.moses.utils.grpc.GRpcMessageConverter.toGRpcMessage;

/**
 * <p>
 *  rpc层数据传输
 * </p>
 *
 * @author yinxicheng
 * @since 2017-09-18
 */
@Service
@Slf4j
@AllArgsConstructor
public class RoleRpcService {

    private RoleServiceBlockingStub blockingStub;

    public BaseResponse add(Role request) {
        RoleDTO dto = (RoleDTO) toGRpcMessage(request, RoleDTO.newBuilder());
        RoleResponse response = blockingStub.add(dto);
        return (BaseResponse) fromGRpcMessage(response, BaseResponse.class,Role.class);
    }

    public BaseResponse update(Role request) {
        RoleDTO dto = (RoleDTO) toGRpcMessage(request, RoleDTO.newBuilder());
        RoleResponse response = blockingStub.update(dto);
        return (BaseResponse) fromGRpcMessage(response, BaseResponse.class,Role.class);
    }

    public BaseResponse query(Role request) {
        RoleDTO dto = (RoleDTO) toGRpcMessage(request, RoleDTO.newBuilder());
        RoleResponse response = blockingStub.query(dto);
        return (BaseResponse) fromGRpcMessage(response, BaseResponse.class,Role.class);
    }

    public BaseResponse delete(Role request) {
        RoleDTO dto = (RoleDTO) toGRpcMessage(request, RoleDTO.newBuilder());
        RoleResponse response = blockingStub.delete(dto);
        return (BaseResponse) fromGRpcMessage(response, BaseResponse.class,Role.class);
    }

    public PageDataResponse list(RolePageParam request) {
        RoleProto.RoleRequest dto = (RoleProto.RoleRequest) toGRpcMessage(request, RoleProto.RoleRequest.newBuilder());
        RoleListResponse listResponse = blockingStub.list(dto);
        return (PageDataResponse) fromGRpcMessage(listResponse, PageDataResponse.class,Role.class);
    }
    public PageResponse page(RolePageParam request) {
        RoleProto.RoleRequest dto = (RoleProto.RoleRequest) toGRpcMessage(request, RoleProto.RoleRequest.newBuilder());
        RolePageResponse listResponse = blockingStub.page(dto);
        return (PageResponse) fromGRpcMessage(listResponse, PageResponse.class,Role.class);
    }
}
