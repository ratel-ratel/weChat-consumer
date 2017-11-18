package cn.vpclub.shm.shcmcc.consumer.rpc;

import cn.vpclub.shm.shcmcc.consumer.api.UserProto;
import cn.vpclub.shm.shcmcc.consumer.api.UserProto.UserDTO;
import cn.vpclub.shm.shcmcc.consumer.api.UserProto.UserResponse;
import cn.vpclub.shm.shcmcc.consumer.api.UserProto.UserListResponse;
import cn.vpclub.shm.shcmcc.consumer.api.UserProto.UserPageResponse;
import cn.vpclub.shm.shcmcc.consumer.api.UserServiceGrpc;
import cn.vpclub.shm.shcmcc.consumer.api.UserServiceGrpc.UserServiceBlockingStub;
import cn.vpclub.shm.shcmcc.consumer.entity.User;
import cn.vpclub.shm.shcmcc.consumer.model.request.UserPageParam;

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
public class UserRpcService {

    private UserServiceBlockingStub blockingStub;

    public BaseResponse add(User request) {
        UserDTO dto = (UserDTO) toGRpcMessage(request, UserDTO.newBuilder());
        UserResponse response = blockingStub.add(dto);
        return (BaseResponse) fromGRpcMessage(response, BaseResponse.class,User.class);
    }

    public BaseResponse update(User request) {
        UserDTO dto = (UserDTO) toGRpcMessage(request, UserDTO.newBuilder());
        UserResponse response = blockingStub.update(dto);
        return (BaseResponse) fromGRpcMessage(response, BaseResponse.class,User.class);
    }

    public BaseResponse query(User request) {
        UserDTO dto = (UserDTO) toGRpcMessage(request, UserDTO.newBuilder());
        UserResponse response = blockingStub.query(dto);
        return (BaseResponse) fromGRpcMessage(response, BaseResponse.class,User.class);
    }

    public BaseResponse delete(User request) {
        UserDTO dto = (UserDTO) toGRpcMessage(request, UserDTO.newBuilder());
        UserResponse response = blockingStub.delete(dto);
        return (BaseResponse) fromGRpcMessage(response, BaseResponse.class,User.class);
    }

    public PageDataResponse list(UserPageParam request) {
        UserProto.UserRequest dto = (UserProto.UserRequest) toGRpcMessage(request, UserProto.UserRequest.newBuilder());
        UserListResponse listResponse = blockingStub.list(dto);
        return (PageDataResponse) fromGRpcMessage(listResponse, PageDataResponse.class,User.class);
    }
    public PageResponse page(UserPageParam request) {
        UserProto.UserRequest dto = (UserProto.UserRequest) toGRpcMessage(request, UserProto.UserRequest.newBuilder());
        UserPageResponse listResponse = blockingStub.page(dto);
        return (PageResponse) fromGRpcMessage(listResponse, PageResponse.class,User.class);
    }
}
