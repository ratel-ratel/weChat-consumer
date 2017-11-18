package cn.vpclub.shm.shcmcc.consumer.service;

import cn.vpclub.moses.core.model.response.BackResponseUtil;
import cn.vpclub.moses.core.model.response.BaseResponse;
import cn.vpclub.moses.core.model.response.PageResponse;
import cn.vpclub.moses.tests.TestsBase;
import cn.vpclub.moses.utils.common.JsonUtil;
import cn.vpclub.moses.utils.common.XmlUtil;
import cn.vpclub.shm.shcmcc.consumer.model.request.weChat.*;
import cn.vpclub.shm.shcmcc.consumer.util.UserUtil;
import cn.vpclub.shm.shcmcc.consumer.util.WeChatUtil;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import cn.vpclub.shm.shcmcc.consumer.rpc.UserRpcService;
import cn.vpclub.shm.shcmcc.consumer.model.request.UserPageParam;;
import cn.vpclub.shm.shcmcc.consumer.entity.User;

@SpringBootTest
@Slf4j
public class UserServiceTests extends TestsBase<UserService> {

    private UserRpcService userRpcService;
    private HazelcastInstance hazelcastInstance;
    private UserUtil userUtil;

    Long id = System.currentTimeMillis();
    User model = null;

    @Override
    public UserService preSetUp() {
        userRpcService = mock(UserRpcService.class);
        hazelcastInstance = mock(HazelcastInstance.class);
        userUtil = mock(UserUtil.class);
        UserService userService = new UserService(userRpcService, hazelcastInstance, userUtil);
        return userService;
    }

    @DataProvider(name = "UserServiceTests.testAddUserData")
    public Object[][] testUserAddDataProvider() throws Exception {
        User bean = new User();
        bean.setId(id);
        bean.setOpenId("oVunVjhMmFF7gkvHO-DDzyZBklGY");
        return new Object[][]{
                {1000, "成功", bean},
        };
    }

    @DataProvider(name = "UserServiceTests.testUserData")
    public Object[][] testUserDataProvider() throws Exception {
        User bean = new User();
        bean.setId(id);
        return new Object[][]{
                {1000, "成功", bean},
        };
    }


    @Test(dataProvider = "UserServiceTests.testAddUserData")
    public void testAddUser(Integer code, String message, User request) {
        BaseResponse baseResponse = BackResponseUtil.getBaseResponse(code);
        when(userRpcService.add(any())).thenReturn(baseResponse);
        BaseResponse response = testObject.add(request);

        // 6.3 verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }

    @DataProvider(name = "UserServiceTests.testUserIdDataProvider")
    public Object[][] testUserIdDataProvider() {

        User bean1 = new User();
        bean1.setId(11l);
        User bean2 = new User();

        return new Object[][]{
                {1006, "id: cannot be null", bean2},
                {1000, "Success", bean1},
        };
    }

    @Test(dataProvider = "UserServiceTests.testUserIdDataProvider", dependsOnMethods = "testAddUser")
    public void testQueryUser(Integer code, String message, User req) {
        when(userRpcService.query(any())).thenReturn(BackResponseUtil.getBaseResponse(code));
        BaseResponse<User> response = testObject.query(req);
        log.info("expected: {}, actual: {} ", message, response.getMessage());
        // 6.3 verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }

    @DataProvider(name = "UserServiceTests.testUpdateUserDataProvider")
    public Object[][] testUpdateUserDataProvider() {

        User bean1 = new User();
        bean1.setId(id);
        User bean2 = new User();
        return new Object[][]{
                {1006, "参数不全", bean2},
                {1000, "成功", bean1},
        };
    }

    @Test(dataProvider = "UserServiceTests.testUpdateUserDataProvider", dependsOnMethods = "testAddUser")
    public void testUpdateUser(Integer code, String message, User request) {
        when(userRpcService.update(any())).thenReturn(BackResponseUtil.getBaseResponse(code));
        BaseResponse<User> response = testObject.update(request);
        log.info("expected: {}, actual: {} ", message, response.getMessage());
        // verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }

    // Step 6: the final step is the test case
    @Test(dataProvider = "UserServiceTests.testUserIdDataProvider")
    public void testDeleteUser(Integer code, String message, User request) {
        when(userRpcService.delete(any())).thenReturn(BackResponseUtil.getBaseResponse(code));
        BaseResponse response = testObject.delete(request);
        log.info("expected: {}, actual: {} ", message, response.getMessage());
        // 6.3 verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }

    @DataProvider(name = "UserServiceTests.ServiceTestsListDataProvider")
    public Object[][] ServiceTestsListDataProvider() {
        UserPageParam request = new UserPageParam();
        request.setStartRow(0);
        request.setPageSize(5);

        List<User> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            id = System.currentTimeMillis();
            model = new User();
//            UserList.add(new UserAssociatedResponse(User, resourceIds));
            model.setId(id);
            list.add(model);
        }

        return new Object[][]{
                {1000, "Success", request, list},
                {1000, "Success", new UserPageParam(), list},
        };
    }

    @Test(dataProvider = "UserServiceTests.ServiceTestsListDataProvider")
    public void testListUser(Integer code, String message, UserPageParam UserParam, List<User> list) {
        PageResponse pageResponse = BackResponseUtil.getPageResponse(code);
        pageResponse.setRecords(list);
        when(userRpcService.page(any())).thenReturn(pageResponse);
        PageResponse response = testObject.page(UserParam);
        log.info("expected: {}, actual: {} ", message, response.getMessage());
        // 6.3 verify output
        Assert.assertTrue(code.equals(response.getReturnCode()));
    }

    @Test
    public void testButton() {
        CreateMenuRequest createMenuRequest = new CreateMenuRequest();
        List<Button> buttons = new ArrayList<>();
        Button button = new Button();
        button.setType("click");
        button.setName("今日歌曲");
        button.setKey("V1001_TODAY_MUSIC");
        Button button1 = new Button();
        button1.setName("菜单");
        List<SubButton> subButtons = new ArrayList<>();

        SubButton subButton = new SubButton();
        subButton.setName("搜索");
        subButton.setType("view");
        subButton.setUrl("http://www.soso.com/");

        SubButton subButton1 = new SubButton();
        subButton1.setName("wxa");
        subButton1.setType("miniprogram");
        subButton1.setUrl("http://mp.weixin.qq.com");
        subButton1.setAppid("wx286b93c14bbf93aa");
        subButton1.setPagepath("pages/lunar/index");

        SubButton subButton2 = new SubButton();
        subButton2.setName("搜索");
        subButton2.setType("view");
        subButton2.setKey("V1001_GOOD");

        subButtons.add(subButton);
        subButtons.add(subButton1);
        subButtons.add(subButton2);

        button1.setSubButton(subButtons);
        buttons.add(button);
        buttons.add(button1);
        createMenuRequest.setButton(buttons);
        log.info("CreateMenuRequest  back  :  " + JsonUtil.objectToJson(createMenuRequest));
    }

    @Test
    public void testButto() {
        CreateMenuRequest createMenuRequest = new CreateMenuRequest();
        List<Button> buttons = new ArrayList<>();
        Button button = new Button();
        button.setType("click");
        button.setName("今日歌曲");
        button.setKey("V1001_TODAY_MUSIC");
        buttons.add(button);

        Button button1 = new Button();
        button1.setName("菜单");

        List<SubButton> subButtons = new ArrayList<>();

        SubButton subButton = new SubButton();
        subButton.setName("搜索");
        subButton.setType("view");
        subButton.setUrl("http://www.soso.com/");

        SubButton subButton1 = new SubButton();
        subButton1.setName("wxa");
        subButton1.setType("miniprogram");
        subButton1.setUrl("http://mp.weixin.qq.com");
        subButton1.setAppid("wx286b93c14bbf93aa");
        subButton1.setPagepath("pages/lunar/index");

        SubButton subButton2 = new SubButton();
        subButton2.setName("搜索");
        subButton2.setType("view");
        subButton2.setKey("V1001_GOOD");

        subButtons.add(subButton);
        subButtons.add(subButton1);
        subButtons.add(subButton2);

        button1.setSubButton(subButtons);
        buttons.add(button1);
        createMenuRequest.setButton(buttons);
        log.info("CreateMenuRequest  back  :  " + JsonUtil.objectToJson(createMenuRequest));
    }

    @Test
    public void testXml() {
        String xml = "<xml>\n" +
                "    <ToUserName><![CDATA[gh_eb1569e1c6f1]]></ToUserName>\n" +
                "    <FromUserName><![CDATA[olh8O1tIkdxi9R6FiU6O3trNmeWc]]></FromUserName>\n" +
                "    <CreateTime>1510660475</CreateTime>\n" +
                "    <MsgType><![CDATA[event]]></MsgType>\n" +
                "    <Event><![CDATA[CLICK]]></Event>\n" +
                "    <EventKey><![CDATA[V1001_TODAY_MUSIC]]></EventKey>\n" +
                "    <Encrypt><![CDATA[qR6l58DxFqNPVfXq7g7Sxl/DmLtiACAWinQ6roy/AKA1pRi2dGFifwTepQS7lpnf2M+yCEgIMg2YKvxMzip5SHVFjMLUqPGs9ins2Ds0DEm7gbAVC+oYW7yjW1Bt1TGqeos2YgM6OhPhpmsEDN1gQp62B2Z9e/vGCvB0TDC0yJH7MxoF5ZoKelEPmS6uevyFtsP51JlqqAni7T+GUATHrIKvVq61w5a3iQd1XDAvcngqLQ3ARnHzgP2ZRzHyTh0j1/AbJaXGVvkHAUbrh6Y9uqmWL9oMxfSHUrYousuGg3CMaqoFyVZoYY+Q/6+SggjG2ok4m5HYGWagVWuMHo8N0Hd8ysoj0o2DsRDxBVi9C8nFdmX3PWZ1lcRElrjLQWtI5EFw2WaC0pZRi5FuM0hBgqfu4XUU5Uhbv2/rrVx4ObliFe753VCPwjBjebtnCpA86lc6Lxo8E8j6zuood74PTA==]]></Encrypt>\n" +
                "</xml>";
        XmlRequest deserialize = XmlUtil.deserialize(xml, XmlRequest.class);
        log.info("XmlRequest  is  " + JsonUtil.objectToJson(deserialize));
    }

//    @Test
//    public void testGetXml() throws DocumentException {
//        String xml = "<xml>\n" +
//                "    <ToUserName><![CDATA[gh_eb1569e1c6f1]]></ToUserName>\n" +
//                "    <FromUserName><![CDATA[olh8O1tIkdxi9R6FiU6O3trNmeWc]]></FromUserName>\n" +
//                "    <CreateTime>1510660475</CreateTime>\n" +
//                "    <MsgType><![CDATA[event]]></MsgType>\n" +
//                "    <Event><![CDATA[CLICK]]></Event>\n" +
//                "    <EventKey><![CDATA[V1001_TODAY_MUSIC]]></EventKey>\n" +
//                "    <Encrypt><![CDATA[qR6l58DxFqNPVfXq7g7Sxl/DmLtiACAWinQ6roy/AKA1pRi2dGFifwTepQS7lpnf2M+yCEgIMg2YKvxMzip5SHVFjMLUqPGs9ins2Ds0DEm7gbAVC+oYW7yjW1Bt1TGqeos2YgM6OhPhpmsEDN1gQp62B2Z9e/vGCvB0TDC0yJH7MxoF5ZoKelEPmS6uevyFtsP51JlqqAni7T+GUATHrIKvVq61w5a3iQd1XDAvcngqLQ3ARnHzgP2ZRzHyTh0j1/AbJaXGVvkHAUbrh6Y9uqmWL9oMxfSHUrYousuGg3CMaqoFyVZoYY+Q/6+SggjG2ok4m5HYGWagVWuMHo8N0Hd8ysoj0o2DsRDxBVi9C8nFdmX3PWZ1lcRElrjLQWtI5EFw2WaC0pZRi5FuM0hBgqfu4XUU5Uhbv2/rrVx4ObliFe753VCPwjBjebtnCpA86lc6Lxo8E8j6zuood74PTA==]]></Encrypt>\n" +
//                "</xml>";
//        String weChatUtilXML = WeChatUtil.getXML(xml);
////        Document doc = DocumentHelper.parseText(weChatUtilXML);
////        String asXML = doc.asXML();
////        log.info("asXML  is   " + asXML);
//        log.info("weChatUtilXML  is   " + weChatUtilXML);
//    }

    @Test
    public void testNew() {
        News news = new News();
        news.setTouser("olh8O1g0wz2rmRaOT6s-O_G_VXRo");
        news.setMsgtype("news");
        NewsInfo newsInfo = new NewsInfo();
        Articles articles = new Articles();
        articles.setDescription("Is Really A Happy Day");
        articles.setTitle("Happy Day");
        articles.setUrl("http://stagegw.vpclub.cn/shm/web/app/#/login?openId=olh8O1g0wz2rmRaOT6s-O_G_VXRo");
        articles.setPicurl("http://gscmimg.oss-cn-shenzhen.aliyuncs.com/upload/0/201711/13/201711132025074248.png");
        List<Articles> list = new ArrayList<>();
        list.add(articles);
        newsInfo.setArticles(list);
        news.setNews(newsInfo);
        log.info("news    is   " + JsonUtil.objectToJson(news));
    }


}
