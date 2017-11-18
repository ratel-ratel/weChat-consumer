package cn.vpclub.shm.shcmcc.consumer.service;

import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.PageResponse;
import cn.vpclub.moses.tests.TestsBase;
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

import cn.vpclub.shm.shcmcc.consumer.rpc.AdminUserRpcService;
import cn.vpclub.shm.shcmcc.consumer.model.request.AdminUserPageParam;;
import cn.vpclub.shm.shcmcc.consumer.entity.AdminUser;

@SpringBootTest
@Slf4j
public class AdminUserServiceTests extends TestsBase<AdminUserService> {

    private AdminUserRpcService adminUserRpcService;

    Long id = System.currentTimeMillis();
    AdminUser model = null;

    @Override
    public AdminUserService preSetUp() {
        adminUserRpcService = mock(AdminUserRpcService.class);
        AdminUserService adminUserService = new AdminUserService(adminUserRpcService);
        return adminUserService;
    }

    @DataProvider(name = "AdminUserServiceTests.testAddAdminUserData")
    public Object[][] testAdminUserAddDataProvider() throws Exception {
        AdminUser bean = new AdminUser();
        bean.setId(id);
        return new Object[][]{
                {1000, "成功", bean},
        };
    }

    @DataProvider(name = "AdminUserServiceTests.testAdminUserData")
    public Object[][] testAdminUserDataProvider() throws Exception {
        AdminUser bean = new AdminUser();
        bean.setId(id);
        return new Object[][]{
                {1000, "成功", bean},
        };
    }


    @Test(dataProvider = "AdminUserServiceTests.testAddAdminUserData")
    public void testAddAdminUser(Integer code, String message, AdminUser request) {
        BaseResponse baseResponse = BackResponseUtil.getBaseResponse(code);
        when(adminUserRpcService.add(any())).thenReturn(baseResponse);
        BaseResponse response = testObject.add(request);

        // 6.3 verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }

    @DataProvider(name = "AdminUserServiceTests.testAdminUserIdDataProvider")
    public Object[][] testAdminUserIdDataProvider() {

        AdminUser bean1 = new AdminUser();
        bean1.setId(11l);
        AdminUser bean2 = new AdminUser();

        return new Object[][]{
                {1006, "id: cannot be null", bean2},
                {1000, "Success", bean1},
        };
    }

    @Test(dataProvider = "AdminUserServiceTests.testAdminUserIdDataProvider", dependsOnMethods = "testAddAdminUser")
    public void testQueryAdminUser(Integer code, String message, AdminUser req) {
        when(adminUserRpcService.query(any())).thenReturn(BackResponseUtil.getBaseResponse(code));
        BaseResponse<AdminUser> response = testObject.query(req);
        log.info("expected: {}, actual: {} ", message, response.getMessage());
        // 6.3 verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }

    @DataProvider(name = "AdminUserServiceTests.testUpdateAdminUserDataProvider")
    public Object[][] testUpdateAdminUserDataProvider() {

        AdminUser bean1 = new AdminUser();
        bean1.setId(id);
        AdminUser bean2 = new AdminUser();
        return new Object[][]{
                {1006, "参数不全", bean2},
                {1000, "成功", bean1},
        };
    }

    @Test(dataProvider = "AdminUserServiceTests.testUpdateAdminUserDataProvider", dependsOnMethods = "testAddAdminUser")
    public void testUpdateAdminUser(Integer code, String message, AdminUser request) {
        when(adminUserRpcService.update(any())).thenReturn(BackResponseUtil.getBaseResponse(code));
        BaseResponse<AdminUser> response = testObject.update(request);
        log.info("expected: {}, actual: {} ", message, response.getMessage());
        // verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }

    // Step 6: the final step is the test case
    @Test(dataProvider = "AdminUserServiceTests.testAdminUserIdDataProvider")
    public void testDeleteAdminUser(Integer code, String message, AdminUser request) {
        when(adminUserRpcService.delete(any())).thenReturn(BackResponseUtil.getBaseResponse(code));
        BaseResponse response = testObject.delete(request);
        log.info("expected: {}, actual: {} ", message, response.getMessage());
        // 6.3 verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }

    @DataProvider(name = "AdminUserServiceTests.ServiceTestsListDataProvider")
    public Object[][] ServiceTestsListDataProvider() {
        AdminUserPageParam request = new AdminUserPageParam();
        request.setStartRow(0);
        request.setPageSize(5);

        List<AdminUser> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            id = System.currentTimeMillis();
            model = new AdminUser();
//            AdminUserList.add(new AdminUserAssociatedResponse(AdminUser, resourceIds));
            model.setId(id);
            list.add(model);
        }

        return new Object[][]{
                {1000, "Success", request, list},
                {1000, "Success", new AdminUserPageParam(), list},
        };
    }

    @Test(dataProvider = "AdminUserServiceTests.ServiceTestsListDataProvider")
    public void testListAdminUser(Integer code, String message, AdminUserPageParam AdminUserParam, List<AdminUser> list) {
        PageResponse pageResponse = BackResponseUtil.getPageResponse(code);
        pageResponse.setRecords(list);
        when(adminUserRpcService.page(any())).thenReturn(pageResponse);
        PageResponse response = testObject.page(AdminUserParam);
        log.info("expected: {}, actual: {} ", message, response.getMessage());
        // 6.3 verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }
}
