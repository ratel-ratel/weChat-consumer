package cn.vpclub.shm.shcmcc.consumer.app;

import cn.vpclub.shm.shcmcc.consumer.ConsumerApplication;
import cn.vpclub.shm.shcmcc.consumer.api.ArticleProto.*;
import cn.vpclub.shm.shcmcc.consumer.api.ArticleServiceGrpc;
import cn.vpclub.shm.shcmcc.consumer.entity.Article;
import cn.vpclub.shm.shcmcc.consumer.model.request.ArticlePageParam;;
import cn.vpclub.moses.tests.PostUtilTests;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by Administrator on 2017/2/22.
 */
@SpringBootTest(classes = {ConsumerApplication.class})
@Slf4j
public class ArticleIntegrationTests extends AbstractTestNGSpringContextTests {
    @Autowired
    private WebApplicationContext context;

    @BeforeMethod
    public void setUp() {
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    private Long id = System.currentTimeMillis();

    @DataProvider(name = "ArticleIntegrationTests.testAddArticleData")
    public Object[][] testArticleAddDataProvider() throws Exception {
        Article bean = new Article();
        bean.setId(id);
        return new Object[][]{
                {1000, "成功", bean},
        };
    }

    @DataProvider(name = "ArticleIntegrationTests.testArticleData")
    public Object[][] testArticleDataProvider() throws Exception {
        Article bean = new Article();
        bean.setId(id);
        return new Object[][]{
                {1000, "成功", bean},
        };
    }

    @Test(dataProvider = "ArticleIntegrationTests.testAddArticleData")
    public void testAddArticle(Integer code, String message, Article request) {
        String uri = "/article/add";
        PostUtilTests.post(code, request, uri);
    }

    @Test(dataProvider = "ArticleIntegrationTests.testArticleData")
    public void testUpdateArticle(Integer code, String message, Article request) {
        String uri = "/article/update";
        PostUtilTests.post(code, request, uri);
    }

    @Test(dataProvider = "ArticleIntegrationTests.testArticleData", dependsOnMethods = "testAddArticle")
    public void testQueryArticle(Integer code, String message, Article request) {
        String uri = "/article/query";
        PostUtilTests.post(code, request, uri);
    }

    @Test(dataProvider = "ArticleIntegrationTests.testArticleData", dependsOnMethods = "testQueryArticle")
    public void testDeleteArticle(Integer code, String message, Article request) {
        String uri = "/article/delete";
        PostUtilTests.post(code, request, uri);
    }

    @DataProvider(name = "ArticleIntegrationTests.testPageArticleData")
    public Object[][] testArticleListDataProvider() {
        return new Object[][]{
                {1000, "Success", new ArticlePageParam()},
        };
    }

    @Test(dataProvider = "ArticleIntegrationTests.testPageArticleData", dependsOnMethods = "testAddArticle")
    public void testPageArticle(Integer code, String message, ArticlePageParam request) {
        String uri = "/article/page";
        PostUtilTests.postList(code, request, uri);
    }

}
