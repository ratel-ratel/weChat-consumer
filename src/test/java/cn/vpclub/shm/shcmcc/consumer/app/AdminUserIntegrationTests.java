package cn.vpclub.shm.shcmcc.consumer.app;

import cn.vpclub.shm.shcmcc.consumer.ConsumerApplication;
import cn.vpclub.shm.shcmcc.consumer.api.AdminUserProto.*;
import cn.vpclub.shm.shcmcc.consumer.api.AdminUserServiceGrpc;
import cn.vpclub.shm.shcmcc.consumer.entity.AdminUser;
import cn.vpclub.shm.shcmcc.consumer.model.request.AdminUserPageParam;;
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
public class AdminUserIntegrationTests extends AbstractTestNGSpringContextTests {
    @Autowired
    private WebApplicationContext context;

    @BeforeMethod
    public void setUp() {
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    private Long id = System.currentTimeMillis();

    @DataProvider(name = "AdminUserIntegrationTests.testAddAdminUserData")
    public Object[][] testAdminUserAddDataProvider() throws Exception {
        AdminUser bean = new AdminUser();
        bean.setId(id);
        return new Object[][]{
                {1000, "成功", bean},
        };
    }

    @DataProvider(name = "AdminUserIntegrationTests.testAdminUserData")
    public Object[][] testAdminUserDataProvider() throws Exception {
        AdminUser bean = new AdminUser();
        bean.setId(id);
        return new Object[][]{
                {1000, "成功", bean},
        };
    }

    @Test(dataProvider = "AdminUserIntegrationTests.testAddAdminUserData")
    public void testAddAdminUser(Integer code, String message, AdminUser request) {
        String uri = "/adminUser/add";
        PostUtilTests.post(code, request, uri);
    }

    @Test(dataProvider = "AdminUserIntegrationTests.testAdminUserData")
    public void testUpdateAdminUser(Integer code, String message, AdminUser request) {
        String uri = "/adminUser/update";
        PostUtilTests.post(code, request, uri);
    }

    @Test(dataProvider = "AdminUserIntegrationTests.testAdminUserData", dependsOnMethods = "testAddAdminUser")
    public void testQueryAdminUser(Integer code, String message, AdminUser request) {
        String uri = "/adminUser/query";
        PostUtilTests.post(code, request, uri);
    }

    @Test(dataProvider = "AdminUserIntegrationTests.testAdminUserData", dependsOnMethods = "testQueryAdminUser")
    public void testDeleteAdminUser(Integer code, String message, AdminUser request) {
        String uri = "/adminUser/delete";
        PostUtilTests.post(code, request, uri);
    }

    @DataProvider(name = "AdminUserIntegrationTests.testPageAdminUserData")
    public Object[][] testAdminUserListDataProvider() {
        return new Object[][]{
                {1000, "Success", new AdminUserPageParam()},
        };
    }

    @Test(dataProvider = "AdminUserIntegrationTests.testPageAdminUserData", dependsOnMethods = "testAddAdminUser")
    public void testPageAdminUser(Integer code, String message, AdminUserPageParam request) {
        String uri = "/adminUser/page";
        PostUtilTests.postList(code, request, uri);
    }

}
