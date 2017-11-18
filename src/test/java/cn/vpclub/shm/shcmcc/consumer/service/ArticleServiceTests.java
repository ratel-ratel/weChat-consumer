package cn.vpclub.shm.shcmcc.consumer.service;

import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.PageResponse;
import cn.vpclub.moses.tests.TestsBase;
import cn.vpclub.shm.shcmcc.consumer.rpc.EmployeeRpcService;
import cn.vpclub.shm.shcmcc.consumer.rpc.UserRpcService;
import cn.vpclub.shm.shcmcc.consumer.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import cn.vpclub.shm.shcmcc.consumer.rpc.ArticleRpcService;
import cn.vpclub.shm.shcmcc.consumer.model.request.ArticlePageParam;;
import cn.vpclub.shm.shcmcc.consumer.entity.Article;

@SpringBootTest
@Slf4j
public class ArticleServiceTests extends TestsBase<ArticleService> {

    private ArticleRpcService articleRpcService;

    private UserRpcService userRpcService;

    private EmployeeRpcService employeeRpcService;

    private UserUtil userUtil;

    Long id = System.currentTimeMillis();
    Article model = null;

    @Override
    public ArticleService preSetUp() {
        articleRpcService = mock(ArticleRpcService.class);
        userRpcService = mock(UserRpcService.class);
        employeeRpcService = mock(EmployeeRpcService.class);
        userUtil = mock(UserUtil.class);
        ArticleService articleService = new ArticleService(articleRpcService,userRpcService,employeeRpcService,userUtil);
        return articleService;
    }

    @DataProvider(name = "ArticleServiceTests.testAddArticleData")
    public Object[][] testArticleAddDataProvider() throws Exception {
        Article bean = new Article();
        bean.setId(id);
        return new Object[][]{
                {1000, "成功", bean},
        };
    }

    @DataProvider(name = "ArticleServiceTests.testArticleData")
    public Object[][] testArticleDataProvider() throws Exception {
        Article bean = new Article();
        bean.setId(id);
        return new Object[][]{
                {1000, "成功", bean},
        };
    }


    @Test(dataProvider = "ArticleServiceTests.testAddArticleData")
    public void testAddArticle(Integer code, String message, Article request) {
        BaseResponse baseResponse = BackResponseUtil.getBaseResponse(code);
        when(articleRpcService.add(any())).thenReturn(baseResponse);
        BaseResponse response = testObject.add(request);

        // 6.3 verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }

    @DataProvider(name = "ArticleServiceTests.testArticleIdDataProvider")
    public Object[][] testArticleIdDataProvider() {

        Article bean1 = new Article();
        bean1.setId(11l);
        Article bean2 = new Article();

        return new Object[][]{
                {1006, "id: cannot be null", bean2},
                {1000, "Success", bean1},
        };
    }

    @Test(dataProvider = "ArticleServiceTests.testArticleIdDataProvider", dependsOnMethods = "testAddArticle")
    public void testQueryArticle(Integer code, String message, Article req) {
        when(articleRpcService.query(any())).thenReturn(BackResponseUtil.getBaseResponse(code));
        BaseResponse<Article> response = testObject.query(req);
        log.info("expected: {}, actual: {} ", message, response.getMessage());
        // 6.3 verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }

    @DataProvider(name = "ArticleServiceTests.testUpdateArticleDataProvider")
    public Object[][] testUpdateArticleDataProvider() {

        Article bean1 = new Article();
        bean1.setId(id);
        Article bean2 = new Article();
        return new Object[][]{
                {1006, "参数不全", bean2},
                {1000, "成功", bean1},
        };
    }

    @Test(dataProvider = "ArticleServiceTests.testUpdateArticleDataProvider", dependsOnMethods = "testAddArticle")
    public void testUpdateArticle(Integer code, String message, Article request) {
        when(articleRpcService.update(any())).thenReturn(BackResponseUtil.getBaseResponse(code));
        BaseResponse<Article> response = testObject.update(request);
        log.info("expected: {}, actual: {} ", message, response.getMessage());
        // verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }

    // Step 6: the final step is the test case
    @Test(dataProvider = "ArticleServiceTests.testArticleIdDataProvider")
    public void testDeleteArticle(Integer code, String message, Article request) {
        when(articleRpcService.delete(any())).thenReturn(BackResponseUtil.getBaseResponse(code));
        BaseResponse response = testObject.delete(request);
        log.info("expected: {}, actual: {} ", message, response.getMessage());
        // 6.3 verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }

    @DataProvider(name = "ArticleServiceTests.ServiceTestsListDataProvider")
    public Object[][] ServiceTestsListDataProvider() {
        ArticlePageParam request = new ArticlePageParam();
        request.setStartRow(0);
        request.setPageSize(5);

        List<Article> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            id = System.currentTimeMillis();
            model = new Article();
//            ArticleList.add(new ArticleAssociatedResponse(Article, resourceIds));
            model.setId(id);
            list.add(model);
        }

        return new Object[][]{
                {1006, "Success", new ArticlePageParam(), list},
        };
    }

    @Test(dataProvider = "ArticleServiceTests.ServiceTestsListDataProvider")
    public void testListArticle(Integer code, String message, ArticlePageParam ArticleParam, List<Article> list) {
        PageResponse pageResponse = BackResponseUtil.getPageResponse(code);
        pageResponse.setRecords(list);
        when(articleRpcService.page(any())).thenReturn(pageResponse);
        PageResponse response = testObject.page(ArticleParam);
        log.info("expected: {}, actual: {} ", message, response.getMessage());
        // 6.3 verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }
}
