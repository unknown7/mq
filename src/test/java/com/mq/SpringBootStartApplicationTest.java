package com.mq;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mq.base.Enums;
import com.mq.base.GlobalConstants;
import com.mq.base.Http;
import com.mq.mapper.OrderMapper;
import com.mq.mapper.RewardPointsMapper;
import com.mq.mapper.VideoClassificationMapper;
import com.mq.model.Order;
import com.mq.model.ShareCard;
import com.mq.model.VideoClassification;
import com.mq.query.VideoClassificationQuery;
import com.mq.util.DateUtil;
import com.mq.util.MapUtil;
import com.mq.util.OrderNoGenerator;
import com.mq.base.RedisObjectHolder;
import com.mq.mapper.MenuMapper;
import com.mq.model.Menu;
import com.mq.util.MD5;
import com.mq.vo.MenuTree;
import com.mq.vo.UserVo;
import com.mq.wx.base.WxAPI;
import com.mq.wx.vo.auth.AuthResponse;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.*;
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
    @Resource
    private GlobalConstants globalConstants;
    @Resource
    private RewardPointsMapper rewardPointsMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private VideoClassificationMapper videoClassificationMapper;

    @Test
    public void contextLoads() {
        List<MenuTree> menuTrees = menuMapper.selectMenuTree(2L);
        System.err.println(JSON.toJSONString(menuTrees));
    }

    @Test
    public void getAccessToken() {
        StringBuffer uri = new StringBuffer();
        uri.append("https://api.weixin.qq.com/cgi-bin/token")
                .append("?appid=").append(globalConstants.getAppId())
                .append("&secret=").append(globalConstants.getAppSecret())
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
//        wxAPI.getUnlimited(page, scene);
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
                .append("?appid=").append(globalConstants.getAppId())
                .append("&secret=").append(globalConstants.getAppSecret())
                .append("&grant_type=").append(Enums.GrantType.AUTHORIZATION_CODE.getKey())
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
//        String qrcodePath = wxAPI.getUnlimited("pages/index/index", scene);
//        System.err.println(qrcodePath);
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
    public void unifiedorder() throws Exception {

        String domain = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        Document doc = DocumentHelper.createDocument();
        Element xml = doc.addElement("xml");
        /**
         * appid
         */
        Element appid = xml.addElement("appid");
        appid.setText("wx153c1bb866bfece8");
        /**
         * mch_id
         */
        Element mch_id = xml.addElement("mch_id");
        mch_id.setText("1533502711");
        /**
         * nonce_str
         */
        Element nonce_str = xml.addElement("nonce_str");
        nonce_str.setText(MD5.generate(UUID.randomUUID().toString()));
        /**
         * body
         */
        Element body = xml.addElement("body");
        body.setText("木荃孕产-产后系列-产后恢复");
        /**
         * out_trade_no
         */
        Element out_trade_no = xml.addElement("out_trade_no");
        out_trade_no.setText(generator.next());
        /**
         * total_fee
         */
        Element total_fee = xml.addElement("total_fee");
        total_fee.setText("1");
        /**
         * spbill_create_ip
         */
        Element spbill_create_ip = xml.addElement("spbill_create_ip");
        spbill_create_ip.setText("182.33.197.227");
        /**
         * notify_url
         */
        Element notify_url = xml.addElement("notify_url");
        notify_url.setText("https://www.unknown7.xyz");
        /**
         * trade_type
         */
        Element trade_type = xml.addElement("trade_type");
        trade_type.setText("JSAPI");
        /**
         * openid
         */
        Element openid = xml.addElement("openid");
        openid.setText("JSAPI");
        /**
         * sign
         */
        Element sign = xml.addElement("sign");
        Map<String, Object> signData = Maps.newLinkedHashMap();
        signData.put("appid", appid.getStringValue());
        signData.put("body", body.getStringValue());
        signData.put("mch_id", mch_id.getStringValue());
        signData.put("nonce_str", nonce_str.getStringValue());
        signData.put("notify_url", notify_url.getStringValue());
        signData.put("out_trade_no", out_trade_no.getStringValue());
        signData.put("spbill_create_ip", spbill_create_ip.getStringValue());
        signData.put("total_fee", total_fee.getStringValue());
        signData.put("trade_type", trade_type.getStringValue());
        signData.put("key", "a02ec5baf0987e3857dd1980c5602252");
        System.err.println(MapUtil.map2str(signData));
        String signStr = MD5.generate(MapUtil.map2str(signData)).toUpperCase();
        System.err.println(signStr);
        sign.setText(signStr);
        OutputFormat format = OutputFormat.createCompactFormat();
        StringWriter writer = new StringWriter();
        XMLWriter output = new XMLWriter(writer, format);
        output.write(doc);
        writer.close();
        output.close();
        System.err.println("requestXml: " + writer.toString());
        String params = "<xml><appid>wx153c1bb866bfece8</appid><mch_id>1533502711</mch_id><nonce_str>673cfe544bb2b18b2baacf42e4f6be76</nonce_str><body>木荃孕产-产后系列-产后恢复</body><out_trade_no>20190430030838000252</out_trade_no><total_fee>1</total_fee><spbill_create_ip>182.33.197.227</spbill_create_ip><notify_url>https://www.unknown7.xyz</notify_url><trade_type>JSAPI</trade_type><sign>E5E7139E4479C3B121161EEE2827A255</sign></xml>";
        ResponseEntity<String> responseEntity = http.postForEntity(domain, writer.toString(), String.class, MediaType.APPLICATION_XML);
        System.err.println(responseEntity.getBody());

    }

    @Test
    public void generate() {
        ExecutorService exec = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
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

    @Test
    public void postXML() {
        String xml = "<xml><return_code>SUCCESS</return_code><xml>";
        HttpEntity<String> entity = new HttpEntity(xml);
        restTemplate.postForEntity("http://127.0.0.1:8080/mq/wx/paymentResult", entity, String.class);
    }

    @Test
    public void dom4j() throws DocumentException {
        String xml = "<xml><appid>123</appid><mch_id>123456</mch_id><d>d</d><c>c</c></xml>";
        Document doc = DocumentHelper.parseText(xml);
        Element root = doc.getRootElement();
        Map<String, String> signData = Maps.newTreeMap();
        Iterator<Element> iterator = root.elementIterator();
        while (iterator.hasNext()) {
            Element next = iterator.next();
            signData.put(next.getName(), next.getStringValue());
        }
        System.err.println(JSON.toJSONString(signData));
    }

    @Test
    public void getUnusedPointsBefore() {
        List<Long> before = rewardPointsMapper.getUnusedPointsBefore(8L, DateUtil.stringToDate("20190509023900", "yyyyMMddHHmmss"));
        System.err.println(JSON.toJSONString(before));
    }

    @Test
    public void orderInsert() {
        Date now = new Date();
        Order order = new Order();
        order.setOrderNo(generator.next());
        order.setOrderStatus(Enums.OrderStatus.UNPAID.getKey());
        order.setGoodsId(1L);
        order.setGoodsType(Enums.PurchaseType.VIDEO.getKey());
        order.setTotalAmount(new BigDecimal("0.2"));
        order.setWxAmount(new BigDecimal("0.2"));
        order.setPoints(BigDecimal.ZERO);
        order.setGoodsPrice(new BigDecimal("0.2"));
        order.setUserId(8L);
        order.setCreateTime(now);
        order.setUpdateTime(now);
        order.setDelFlag(0);
        orderMapper.insertSelective(order);
    }

    @Test
    public void pagination() {
        VideoClassificationQuery query = new VideoClassificationQuery();
        query.setDelFlag(0);
        PageHelper.startPage(1, 10);
        List<VideoClassification> videoClassifications = videoClassificationMapper.selectByQuery(query);
        PageInfo<VideoClassification> pageInfo = new PageInfo<>(videoClassifications);
        System.err.println(pageInfo);
    }

    public static void main(String[] args) throws Exception {
        System.err.println(MD5.generate("123456"));
        System.err.println(MD5.generate("xiaoxiao900529"));
        System.err.println(MD5.generate("x1tVKvwXVwIHwX79puI="));
        System.err.println(new BigDecimal("12.30").multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP));
        System.err.println(System.currentTimeMillis());
        String a = new StringBuilder("t").toString();
        String b = "t";
        String c = "t";
        String d = "t";
        String e = "t";
        String f = "t";
        String[] ss = {a, b, c, d, e, f};
        ExecutorService exec = Executors.newCachedThreadPool();
        Notify notify = new Notify();
        for (int i = 0; i < 6; i++) {
            final int ii = i;
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    notify.handle(ss[ii]);
                }
            });
        }
        TimeUnit.SECONDS.sleep(20);
    }

}

class Notify {
    public void handle(String s) {
        synchronized ("t") {
            System.err.println("in..");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}