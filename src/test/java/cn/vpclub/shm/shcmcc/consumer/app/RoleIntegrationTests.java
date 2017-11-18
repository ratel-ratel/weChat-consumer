package cn.vpclub.shm.shcmcc.consumer.app;

import cn.vpclub.shm.shcmcc.consumer.ConsumerApplication;
import cn.vpclub.shm.shcmcc.consumer.api.RoleProto.*;
import cn.vpclub.shm.shcmcc.consumer.api.RoleServiceGrpc;
import cn.vpclub.shm.shcmcc.consumer.entity.Role;
import cn.vpclub.shm.shcmcc.consumer.model.request.RolePageParam;;
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
public class RoleIntegrationTests extends AbstractTestNGSpringContextTests {
    @Autowired
    private WebApplicationContext context;

    @BeforeMethod
    public void setUp() {
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    private Long id = System.currentTimeMillis();

    @DataProvider(name = "RoleIntegrationTests.testAddRoleData")
    public Object[][] testRoleAddDataProvider() throws Exception {
        Role bean = new Role();
        bean.setName("角色名称");
        bean.setUpdateName("更新操作员");
        bean.setRemark("添加了备注");
        bean.setCreatedBy(id);

        Role bean1 = new Role();

        return new Object[][]{
                {1000, "成功", bean},
                {1006, "入参信息不全", bean1},
        };
    }

    @DataProvider(name = "RoleIntegrationTests.testRoleData")
    public Object[][] testRoleDataProvider() throws Exception {
        Role bean = new Role();
        bean.setId(id);
        return new Object[][]{
                {1000, "成功", bean},
        };
    }

    @Test(dataProvider = "RoleIntegrationTests.testAddRoleData")
    public void testAddRole(Integer code, String message, Role request) {
        String uri = "/role/add";
        PostUtilTests.post(code, request, uri);
    }

    @DataProvider(name = "RoleIntegrationTests.testUpdateRoleData")
    public Object[][] testUpdateRoleData() throws Exception {
        Role bean = new Role();
        bean.setId(910088377140502528l);
        bean.setName("角色名称");
        bean.setUpdatedBy(id);

        Role bean1 = new Role();

        return new Object[][]{
                {1000, "成功", bean},
                {1006, "缺少参数输入", bean1},
        };
    }

    @Test(dataProvider = "RoleIntegrationTests.testUpdateRoleData")
    public void testUpdateRole(Integer code, String message, Role request) {
        String uri = "/role/update";
        PostUtilTests.post(code, request, uri);
    }
    @DataProvider(name = "RoleIntegrationTests.testQueryRoleData")
    public Object[][] testQueryRoleData() throws Exception {

        //正常入参 910101766919151616
        Role bean = new Role();
        bean.setId(910101766919151616l);

        //状态为下线的角色 910088377140502528
        Role bean1 = new Role();
        bean1.setId(910088377140502528l);

        //无参
        Role bean2 = new Role();

        return new Object[][]{
                {1000, "成功", bean},
                {1002, "没有查到信息", bean1},
                {1006, "缺少参数输入", bean2},
        };
    }
    @Test(dataProvider = "RoleIntegrationTests.testQueryRoleData")
    public void testQueryRole(Integer code, String message, Role request) {
        String uri = "/role/query";
        PostUtilTests.post(code, request, uri);
    }

    @DataProvider(name = "RoleIntegrationTests.testDeleteRoleData")
    public Object[][] testDeleteRoleData() throws Exception {

        //正常入参
        Role bean = new Role();
        bean.setId(910088377140502528l);

        //无参
        Role bean1 = new Role();

        return new Object[][]{
                {1000, "成功", bean},
                {1006, "缺少参数输入", bean1},
        };
    }

    @Test(dataProvider = "RoleIntegrationTests.testDeleteRoleData", dependsOnMethods = "testAddRole")
    public void testDeleteRole(Integer code, String message, Role request) {
        String uri = "/role/delete";
        PostUtilTests.post(code, request, uri);
    }

    @DataProvider(name = "RoleIntegrationTests.testPageRoleData")
    public Object[][] testPageRoleData() {

        RolePageParam request = new RolePageParam();
        request.setPageNumber(2);
        request.setPageSize(3);

        return new Object[][]{
                {1000, "成功", request},
                {1006, "缺少参数输入", new RolePageParam()},
        };
    }

    @Test(dataProvider = "RoleIntegrationTests.testPageRoleData")
    public void testPageRole(Integer code, String message, RolePageParam request) {
        String uri = "/role/page";
        PostUtilTests.postList(code, request, uri);
    }

}
