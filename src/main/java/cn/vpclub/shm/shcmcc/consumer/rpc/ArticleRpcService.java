package cn.vpclub.shm.shcmcc.consumer.rpc;

import cn.vpclub.shm.shcmcc.consumer.api.ArticleProto;
import cn.vpclub.shm.shcmcc.consumer.api.ArticleProto.ArticleDTO;
import cn.vpclub.shm.shcmcc.consumer.api.ArticleProto.ArticleResponse;
import cn.vpclub.shm.shcmcc.consumer.api.ArticleProto.ArticleListResponse;
import cn.vpclub.shm.shcmcc.consumer.api.ArticleProto.ArticlePageResponse;
import cn.vpclub.shm.shcmcc.consumer.api.ArticleServiceGrpc;
import cn.vpclub.shm.shcmcc.consumer.api.ArticleServiceGrpc.ArticleServiceBlockingStub;
import cn.vpclub.shm.shcmcc.consumer.entity.Article;
import cn.vpclub.shm.shcmcc.consumer.model.request.ArticlePageParam;

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
public class ArticleRpcService {

    private ArticleServiceBlockingStub blockingStub;

    public BaseResponse add(Article request) {
        ArticleDTO dto = (ArticleDTO) toGRpcMessage(request, ArticleDTO.newBuilder());
        ArticleResponse response = blockingStub.add(dto);
        return (BaseResponse) fromGRpcMessage(response, BaseResponse.class,Article.class);
    }

    public BaseResponse update(Article request) {
        ArticleDTO dto = (ArticleDTO) toGRpcMessage(request, ArticleDTO.newBuilder());
        ArticleResponse response = blockingStub.update(dto);
        return (BaseResponse) fromGRpcMessage(response, BaseResponse.class,Article.class);
    }

    public BaseResponse query(Article request) {
        ArticleDTO dto = (ArticleDTO) toGRpcMessage(request, ArticleDTO.newBuilder());
        ArticleResponse response = blockingStub.query(dto);
        return (BaseResponse) fromGRpcMessage(response, BaseResponse.class,Article.class);
    }

    public BaseResponse delete(Article request) {
        ArticleDTO dto = (ArticleDTO) toGRpcMessage(request, ArticleDTO.newBuilder());
        ArticleResponse response = blockingStub.delete(dto);
        return (BaseResponse) fromGRpcMessage(response, BaseResponse.class,Article.class);
    }

    public PageDataResponse list(ArticlePageParam request) {
        ArticleProto.ArticleRequest dto = (ArticleProto.ArticleRequest) toGRpcMessage(request, ArticleProto.ArticleRequest.newBuilder());
        ArticleListResponse listResponse = blockingStub.list(dto);
        return (PageDataResponse) fromGRpcMessage(listResponse, PageDataResponse.class,Article.class);
    }
    public PageResponse page(ArticlePageParam request) {
        ArticleProto.ArticleRequest dto = (ArticleProto.ArticleRequest) toGRpcMessage(request, ArticleProto.ArticleRequest.newBuilder());
        ArticlePageResponse listResponse = blockingStub.page(dto);
        return (PageResponse) fromGRpcMessage(listResponse, PageResponse.class,Article.class);
    }
}
