package com.mq;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.mq.base.GlobalConstants;
import com.mq.base.RedisObjectHolder;
import com.mq.mapper.MenuMapper;
import com.mq.model.Menu;
import com.mq.model.User;
import com.mq.service.MenuService;
import com.mq.vo.MenuTree;
import com.mq.vo.UserVo;
import com.mq.wx.base.WxAPI;
import com.mq.wx.vo.auth.AuthResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.mq.mapper")
public class SpringBootStartApplicationTest {
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private WxAPI wxAPI;
    @Resource
    private RedisObjectHolder redisObjectHolder;

    @Test
    public void contextLoads() {
        List<MenuTree> menuTrees = menuMapper.selectMenuTree(2L);
        System.err.println(JSON.toJSONString(menuTrees));
    }

    @Test
    public void getAccessToken() {
        StringBuffer uri = new StringBuffer();
        uri.append("https://api.weixin.qq.com/cgi-bin/token")
                .append("?appid=").append(GlobalConstants.APP_ID)
                .append("&secret=").append(GlobalConstants.APP_SECRET)
                .append("&grant_type=client_credential")
        ;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity(headers);
        String result = restTemplate.exchange(
                uri.toString(),
                HttpMethod.GET,
                entity,
                String.class
        ).getBody();
        System.err.println(result);

        /**
         * {"access_token":"20_ZaB9mSJHCASCZAiLoyRYpl7gm9j1SWLfYNFqm_9tdiivhyO1QpqHhJbW0fFPRREsTO4zt8N7ZNPPfG0S74Cn9yIDEYb9P4Wgd6oSQYeUWg3BGW2PEFsV87kR1VwNehnrxUdAxuvhPScLoSVlCSYfAJAXNT","expires_in":7200}
         * {"access_token":"20_nurFUJwmV_63mKVzzGNkA112IfEoFOrLoyN6Z7KbekawvmeDM4MhzMcGyPQQhO3O0PJO6N_3yx5UV1YQR_CLd6jKa4J9O8xge2mnVwoXfmkm1E6CoKL1EUeVkQ3TBp3VyEY04rYTTKjD0QxLSROjAEAHGD","expires_in":7200}
         */
    }

    @Test
    public void redis() {
        Object user_info = stringRedisTemplate.opsForHash().get("user_info", "oTt2W5DiQU-M7w8LD7EskU-9IjXk");
        System.err.println(JSON.toJSONString(user_info));
    }

    @Test
    public void getUnlimited() {
//        String page = "pages/home/index";
        String page = "";
        Map<String, Object> scene = Maps.newHashMap();
        scene.put("userId", 1L);
        scene.put("videoId", 1L);
        wxAPI.getUnlimited(page, scene);
    }

    @Test
    public void getForEntry() {
        AuthResponse authResponse = wxAPI.jscode2session("071GNxaM17ks681arv9M19okaM1GNxa4");
        System.err.println(JSON.toJSONString(authResponse));
    }

    @Test
    public void redisMap() {
        UserVo userVo = new UserVo();
        userVo.setId(1L);
        userVo.setGender(1);
        userVo.setAvatarUrl("image");
        userVo.setCity("weifang");
        userVo.setCountry("china");
        userVo.setDelFlag(0);
        redisObjectHolder.setUserInfo("abc", userVo);
    }

    @Test
    public void jscode2session() {
        StringBuffer uri = new StringBuffer();
        uri.append("https://api.weixin.qq.com/sns/jscode2session")
                .append("?appid=").append(GlobalConstants.APP_ID)
                .append("&secret=").append(GlobalConstants.APP_SECRET)
                .append("&grant_type=").append(GlobalConstants.GrantType.AUTHORIZATION_CODE.getKey())
                .append("&js_code=").append("071GNxaM17ks681arv9M19okaM1GNxa4")
        ;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity(headers);
        String result = restTemplate.exchange(
                uri.toString(),
                HttpMethod.GET,
                entity,
                String.class
        ).getBody();
        System.err.println(result);
    }

    @Test
    public void redisList() {
        for (int i = 0; i < 5; i++) {
            stringRedisTemplate.opsForList().leftPush("test", i + "");
        }
        stringRedisTemplate.opsForList().rightPop("test");
    }

    @Test
    public void generateQrcode() {
//        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=20_pIO2oULM73oOchBOs44Ojh7l0Lf6SsffOklTlyOTip9vpL9plYyJ02uMUM9SQ6wTU1S2aLS3aYASSCpr5K2FsXJTqNUOaeUXiigRgzoyY5zcK2Gz15ciTSfOCheG4uOsDk83bnS6Hi7zVAuZDPUiAFAKWB";
//        Map<String, Object> params = Maps.newHashMap();
//        params.put("page", "pages/index/index");
//        params.put("scene", "userId=1&videoId=1");
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        HttpEntity<String> entity = new HttpEntity(params, headers);
//        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//        System.err.println(exchange.getBody());


        Map<String, Object> scene = Maps.newHashMap();
        scene.put("skey", "995d28909F6a68a33f1bbd4348e61490");
        scene.put("videoId", "16");
        String qrcodePath = wxAPI.getUnlimited("pages/index/index", scene);
        System.err.println(qrcodePath);
    }

    @Test
    public void insert() {
        Menu menu = new Menu();
        menu.setmName("test");
        menu.setPid(111L);
        menu.setDelFlag(0);
        menuMapper.insertSelective(menu);
        System.err.println(JSON.toJSONString(menu));
    }

    @Test
    public void redisExpire() {
        stringRedisTemplate.opsForValue().set("a", "a", 10, TimeUnit.SECONDS);
    }

}

