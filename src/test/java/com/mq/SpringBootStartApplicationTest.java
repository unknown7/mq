package com.mq;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.mq.base.GlobalConstants;
import com.mq.base.Http;
import com.mq.base.OrderNoGenerator;
import com.mq.base.RedisObjectHolder;
import com.mq.mapper.MenuMapper;
import com.mq.model.Menu;
import com.mq.util.MD5Util;
import com.mq.vo.MenuTree;
import com.mq.vo.UserVo;
import com.mq.wx.base.WxAPI;
import com.mq.wx.vo.auth.AuthResponse;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
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
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    @Resource
    private Http http;
    @Resource
    private OrderNoGenerator generator;

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
        scene.put("skey", "1");
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

    @Test
    public void unifiedorder() {
        String domain = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        Document doc = DocumentHelper.createDocument();
        Element xml = doc.addElement("xml");
        /**
         * appid
         */
        Element appid = xml.addElement("appid");
        appid.setData("wx153c1bb866bfece8");
        /**
         * mch_id
         */
        Element mch_id = xml.addElement("mch_id");
        mch_id.setData("1533502711");
        /**
         * nonce_str
         */
        Element nonce_str = xml.addElement("nonce_str");
        nonce_str.setData(MD5Util.getEncryption(UUID.randomUUID().toString()));
        /**
         * body
         */
        Element body = xml.addElement("body");
        body.setData("木荃孕产-产后系列-产后恢复");
        /**
         * out_trade_no
         */
        Element out_trade_no = xml.addElement("out_trade_no");
        out_trade_no.setData(generator.next());
        /**
         * total_fee
         */
        Element total_fee = xml.addElement("total_fee");
        total_fee.setData(1);
        /**
         * spbill_create_ip
         */
        Element spbill_create_ip = xml.addElement("spbill_create_ip");
        spbill_create_ip.setData("182.33.197.227");
        /**
         * notify_url
         */
        Element notify_url = xml.addElement("notify_url");
        notify_url.setData("https://www.unknown7.xyz");
        /**
         * trade_type
         */
        Element trade_type = xml.addElement("trade_type");
        trade_type.setData("");
        /**
         * sign
         */
        Element sign = xml.addElement("sign");
        Map<String, Object> signData = Maps.newHashMap();
        signData.put("appid", appid.getStringValue());
        signData.put("body", body.getStringValue());
        signData.put("mch_id", mch_id.getStringValue());
        signData.put("nonceStr", nonce_str.getStringValue());
        signData.put("notify_url", notify_url.getStringValue());
        signData.put("out_trade_no", out_trade_no.getStringValue());
        signData.put("spbill_create_ip", spbill_create_ip.getStringValue());
        signData.put("total_fee", total_fee.getStringValue());
        signData.put("trade_type", trade_type.getStringValue());
        sign.setData("");
    }

    @Test
    public void generate() {
        ExecutorService exec = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    System.err.println(generator.next());
                }
            });
        }

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

