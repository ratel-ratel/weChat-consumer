package cn.vpclub.shm.shcmcc.consumer.rpc;

import cn.vpclub.shm.shcmcc.consumer.api.AdminUserProto;
import cn.vpclub.shm.shcmcc.consumer.api.AdminUserProto.AdminUserDTO;
import cn.vpclub.shm.shcmcc.consumer.api.AdminUserProto.AdminUserResponse;
import cn.vpclub.shm.shcmcc.consumer.api.AdminUserProto.AdminUserListResponse;
import cn.vpclub.shm.shcmcc.consumer.api.AdminUserProto.AdminUserPageResponse;
import cn.vpclub.shm.shcmcc.consumer.api.AdminUserServiceGrpc;
import cn.vpclub.shm.shcmcc.consumer.api.AdminUserServiceGrpc.AdminUserServiceBlockingStub;
import cn.vpclub.shm.shcmcc.consumer.entity.AdminUser;
import cn.vpclub.shm.shcmcc.consumer.model.request.AdminUserPageParam;

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
public class AdminUserRpcService {

    private AdminUserServiceBlockingStub blockingStub;

    public BaseResponse add(AdminUser request) {
        AdminUserDTO dto = (AdminUserDTO) toGRpcMessage(request, AdminUserDTO.newBuilder());
        AdminUserResponse response = blockingStub.add(dto);
        return (BaseResponse) fromGRpcMessage(response, BaseResponse.class,AdminUser.class);
    }

    public BaseResponse update(AdminUser request) {
        AdminUserDTO dto = (AdminUserDTO) toGRpcMessage(request, AdminUserDTO.newBuilder());
        AdminUserResponse response = blockingStub.update(dto);
        return (BaseResponse) fromGRpcMessage(response, BaseResponse.class,AdminUser.class);
    }

    public BaseResponse query(AdminUser request) {
        AdminUserDTO dto = (AdminUserDTO) toGRpcMessage(request, AdminUserDTO.newBuilder());
        AdminUserResponse response = blockingStub.query(dto);
        return (BaseResponse) fromGRpcMessage(response, BaseResponse.class,AdminUser.class);
    }

    public BaseResponse delete(AdminUser request) {
        AdminUserDTO dto = (AdminUserDTO) toGRpcMessage(request, AdminUserDTO.newBuilder());
        AdminUserResponse response = blockingStub.delete(dto);
        return (BaseResponse) fromGRpcMessage(response, BaseResponse.class,AdminUser.class);
    }

    public PageDataResponse list(AdminUserPageParam request) {
        AdminUserProto.AdminUserRequest dto = (AdminUserProto.AdminUserRequest) toGRpcMessage(request, AdminUserProto.AdminUserRequest.newBuilder());
        AdminUserListResponse listResponse = blockingStub.list(dto);
        return (PageDataResponse) fromGRpcMessage(listResponse, PageDataResponse.class,AdminUser.class);
    }
    public PageResponse page(AdminUserPageParam request) {
        AdminUserProto.AdminUserRequest dto = (AdminUserProto.AdminUserRequest) toGRpcMessage(request, AdminUserProto.AdminUserRequest.newBuilder());
        AdminUserPageResponse listResponse = blockingStub.page(dto);
        return (PageResponse) fromGRpcMessage(listResponse, PageResponse.class,AdminUser.class);
    }
}
