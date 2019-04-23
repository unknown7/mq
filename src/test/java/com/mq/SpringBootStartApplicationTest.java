package com.mq;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.mq.base.GlobalConstants;
import com.mq.mapper.MenuMapper;
import com.mq.model.User;
import com.mq.service.MenuService;
import com.mq.vo.MenuTree;
import com.mq.vo.UserVo;
import com.mq.wx.base.WxAPI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
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
    private MenuService menuService;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private WxAPI wxAPI;

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
        UserVo userVo = new UserVo();
        userVo.setAvatarUrl("https://www.google.com");
        userVo.setId(1L);
        userVo.setGender(1);
        stringRedisTemplate.opsForValue().set("user", JSON.toJSONString(userVo), 10, TimeUnit.SECONDS);
        String value = stringRedisTemplate.opsForValue().get("user");
        System.err.println(value);
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

}

