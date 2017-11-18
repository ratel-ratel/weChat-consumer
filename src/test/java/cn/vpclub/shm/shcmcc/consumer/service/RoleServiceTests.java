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

import cn.vpclub.shm.shcmcc.consumer.rpc.RoleRpcService;
import cn.vpclub.shm.shcmcc.consumer.model.request.RolePageParam;;
import cn.vpclub.shm.shcmcc.consumer.entity.Role;

@SpringBootTest
@Slf4j
public class RoleServiceTests extends TestsBase<RoleService> {

    private RoleRpcService roleRpcService;

    Long id = System.currentTimeMillis();
    Role model = null;

    @Override
    public RoleService preSetUp() {
        roleRpcService = mock(RoleRpcService.class);
        RoleService roleService = new RoleService(roleRpcService);
        return roleService;
    }

    @DataProvider(name = "RoleServiceTests.testAddRoleData")
    public Object[][] testRoleAddDataProvider() throws Exception {
        Role bean = new Role();
        bean.setCreatedBy(id);

        return new Object[][]{
                {1000, "成功", bean},
                {1006, "缺少入参", new Role()},
        };
    }

    @DataProvider(name = "RoleServiceTests.testRoleData")
    public Object[][] testRoleDataProvider() throws Exception {
        Role bean = new Role();
        bean.setId(id);
        return new Object[][]{
                {1000, "成功", bean},
        };
    }


    @Test(dataProvider = "RoleServiceTests.testAddRoleData")
    public void testAddRole(Integer code, String message, Role request) {
        BaseResponse baseResponse = BackResponseUtil.getBaseResponse(code);
        when(roleRpcService.add(any())).thenReturn(baseResponse);
        BaseResponse response = testObject.add(request);

        // 6.3 verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }

    @DataProvider(name = "RoleServiceTests.testRoleIdDataProvider")
    public Object[][] testRoleIdDataProvider() {

        Role bean1 = new Role();
        bean1.setId(11l);
        Role bean2 = new Role();

        return new Object[][]{
                {1006, "id: cannot be null", bean2},
                {1000, "Success", bean1},
        };
    }

    @Test(dataProvider = "RoleServiceTests.testRoleIdDataProvider", dependsOnMethods = "testAddRole")
    public void testQueryRole(Integer code, String message, Role req) {
        when(roleRpcService.query(any())).thenReturn(BackResponseUtil.getBaseResponse(code));
        BaseResponse<Role> response = testObject.query(req);
        log.info("expected: {}, actual: {} ", message, response.getMessage());
        // 6.3 verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }

    @DataProvider(name = "RoleServiceTests.testUpdateRoleDataProvider")
    public Object[][] testUpdateRoleDataProvider() {

        Role bean1 = new Role();
        bean1.setId(id);
        bean1.setUpdatedBy(11l);

        Role bean2 = new Role();
        return new Object[][]{
                {1006, "参数不全", bean2},
                {1000, "成功", bean1},
        };
    }

    @Test(dataProvider = "RoleServiceTests.testUpdateRoleDataProvider", dependsOnMethods = "testAddRole")
    public void testUpdateRole(Integer code, String message, Role request) {
        when(roleRpcService.update(any())).thenReturn(BackResponseUtil.getBaseResponse(code));
        BaseResponse<Role> response = testObject.update(request);
        log.info("expected: {}, actual: {} ", message, response.getMessage());
        // verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }

    // Step 6: the final step is the test case
    @Test(dataProvider = "RoleServiceTests.testRoleIdDataProvider")
    public void testDeleteRole(Integer code, String message, Role request) {
        when(roleRpcService.delete(any())).thenReturn(BackResponseUtil.getBaseResponse(code));
        BaseResponse response = testObject.delete(request);
        log.info("expected: {}, actual: {} ", message, response.getMessage());
        // 6.3 verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }

    @DataProvider(name = "RoleServiceTests.testPageDataProvider")
    public Object[][] ServiceTestsListDataProvider() {
        RolePageParam request = new RolePageParam();
        request.setPageNumber(2);
        request.setPageSize(3);

        List<Role> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            id = System.currentTimeMillis();
            model = new Role();
//            RoleList.add(new RoleAssociatedResponse(Role, resourceIds));
            model.setId(id);
            list.add(model);
        }

        return new Object[][]{
                {1000, "成功", request, list},
                {1006, "入参有问题", new RolePageParam(), list},
        };
    }

    @Test(dataProvider = "RoleServiceTests.testPageDataProvider")
    public void testPageRole(Integer code, String message, RolePageParam RoleParam, List<Role> list) {
        PageResponse pageResponse = BackResponseUtil.getPageResponse(code);
        pageResponse.setRecords(list);
        when(roleRpcService.page(any())).thenReturn(pageResponse);
        PageResponse response = testObject.page(RoleParam);
        log.info("expected: {}, actual: {} ", message, response.getMessage());
        // 6.3 verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }
}
