package cn.vpclub.shm.shcmcc.consumer.app;

import cn.vpclub.shm.shcmcc.consumer.ConsumerApplication;
import cn.vpclub.shm.shcmcc.consumer.api.UserProto.*;
import cn.vpclub.shm.shcmcc.consumer.api.UserServiceGrpc;
import cn.vpclub.shm.shcmcc.consumer.entity.User;
import cn.vpclub.shm.shcmcc.consumer.model.request.UserPageParam;;
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
public class UserIntegrationTests extends AbstractTestNGSpringContextTests {
    @Autowired
    private WebApplicationContext context;

    @BeforeMethod
    public void setUp() {
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    private Long id = System.currentTimeMillis();

    @DataProvider(name = "UserIntegrationTests.testAddUserData")
    public Object[][] testUserAddDataProvider() throws Exception {
        User bean = new User();
        bean.setId(id);
        return new Object[][]{
                {1000, "成功", bean},
        };
    }

    @DataProvider(name = "UserIntegrationTests.testUserData")
    public Object[][] testUserDataProvider() throws Exception {
        User bean = new User();
        bean.setId(id);
        return new Object[][]{
                {1000, "成功", bean},
        };
    }

    @Test(dataProvider = "UserIntegrationTests.testAddUserData")
    public void testAddUser(Integer code, String message, User request) {
        String uri = "/user/add";
        PostUtilTests.post(code, request, uri);
    }

    @Test(dataProvider = "UserIntegrationTests.testUserData")
    public void testUpdateUser(Integer code, String message, User request) {
        String uri = "/user/update";
        PostUtilTests.post(code, request, uri);
    }

    @Test(dataProvider = "UserIntegrationTests.testUserData", dependsOnMethods = "testAddUser")
    public void testQueryUser(Integer code, String message, User request) {
        String uri = "/user/query";
        PostUtilTests.post(code, request, uri);
    }

    @Test(dataProvider = "UserIntegrationTests.testUserData", dependsOnMethods = "testQueryUser")
    public void testDeleteUser(Integer code, String message, User request) {
        String uri = "/user/delete";
        PostUtilTests.post(code, request, uri);
    }

    @DataProvider(name = "UserIntegrationTests.testPageUserData")
    public Object[][] testUserListDataProvider() {
        return new Object[][]{
                {1000, "Success", new UserPageParam()},
        };
    }

    @Test(dataProvider = "UserIntegrationTests.testPageUserData", dependsOnMethods = "testAddUser")
    public void testPageUser(Integer code, String message, UserPageParam request) {
        String uri = "/user/page";
        PostUtilTests.postList(code, request, uri);
    }

}
