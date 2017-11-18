package cn.vpclub.shm.shcmcc.consumer.web;

import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.PageDataResponse;
import cn.vpclub.moses.core.model.response.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import cn.vpclub.shm.shcmcc.consumer.entity.Article;
import cn.vpclub.shm.shcmcc.consumer.model.request.ArticlePageParam;
import cn.vpclub.shm.shcmcc.consumer.service.ArticleService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yinxicheng
 * @since 2017-09-18
 */
@RestController
@Slf4j
@Scope("prototype")
@AllArgsConstructor
@RequestMapping("/article")
@Api(value = "",description = "RESTful-API-SERVICE")
public class ArticleController {

    private ArticleService articleService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-add method")
    public BaseResponse add(@RequestBody Article vo) {
        return articleService.add(vo);
        }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-update method")
    public BaseResponse update(@RequestBody Article vo) {
        return articleService.update(vo);
        }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-update method")
    public BaseResponse query(@RequestBody Article vo) {
        return articleService.query(vo);
        }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-update method")
    public BaseResponse delete(@RequestBody Article vo) {
        return articleService.delete(vo);
        }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-list method")
    public PageDataResponse list(@RequestBody ArticlePageParam vo) {
        return articleService.list(vo);
        }
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("-page method")
    public PageResponse page(@RequestBody ArticlePageParam vo) {
        return articleService.page(vo);
    }
    }
