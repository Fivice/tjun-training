package tjuninfo.training.task.util.httpClient;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description 网建短信通sms.webchinese.com.cn 登录模拟，以及登录后修改短信签名
 * @date 2019年6月27日15:33:02
 * @author wubin
 */

public final class SmsUtils {
    private volatile static SmsUtils smsUtils;
    private CloseableHttpClient httpClient;
    private RequestConfig requestConfig;
    private static Logger logger = LoggerFactory.getLogger(SmsUtils.class);

    /**
     * 私有化构造函数
     */
    private SmsUtils() {
        CookieStore cookieStore = new BasicCookieStore();
        this.requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
        this.httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
    }

    /**
     * 采用双检索方案保障线程安全和性能
     * 获取对象实例
     * @return SmsUtils实例对象
     */
    public static SmsUtils getInstance(){
        if (smsUtils == null){
            synchronized (SmsUtils.class){
                if (smsUtils == null){
                    smsUtils = new SmsUtils();
                }
            }
        }
        return smsUtils;
    }

    public void init(){

    }
    public String login(String userName,String pwd) throws IOException {
        /*
         * action=Login&UserName=fivice&Pass=67321015wb
         */
        String LOGIN_URL = "http://sms.webchinese.com.cn/program/Login.asp";
        String USER_AGENT = "User-Agent";
        String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0";
        String responseContent = null;
        String responseContent2 = null;
        HttpPost httpPost = new HttpPost(LOGIN_URL);
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("t",new Date().toString()));
        list.add(new BasicNameValuePair("UserName",userName));
        list.add(new BasicNameValuePair("Pass",pwd));
        list.add(new BasicNameValuePair("action","Login"));

        HttpEntity entity = new UrlEncodedFormEntity (list,"UTF-8");

        httpPost.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == 200){
            HttpEntity httpEntity = response.getEntity();
            responseContent = EntityUtils.toString(httpEntity,"UTF-8");

        }
        response.close();

        HttpGet httpGet = new HttpGet("http://sms.webchinese.com.cn/User/?action=pass");
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response2 = httpClient.execute(httpGet);
        if (response2.getStatusLine().getStatusCode() == 200){
            HttpEntity httpEntity = response2.getEntity();
            responseContent2 = EntityUtils.toString(httpEntity,"UTF-8");
            Document document = Jsoup.parse(responseContent2);
            document.getElementById("User_QM").val();
            String User_Name = document.getElementById("User_Name").val();
            String Email = document.getElementById("Email").val();

            String res = changeQM(User_Name,Email);
        }
        response2.close();


        return responseContent;
    }

    public void close(){
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String changeQM(String User_Name,String Email) throws IOException {
        /**
         * Email: wubin_oct@163.com
         * User_Name: wu
         * User_QM: 武斌
         */
        String url = "http://sms.webchinese.com.cn/User/Edit_MobSave.asp";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        List<NameValuePair> list = new ArrayList<>();
        String responseContent = null;
        list.add(new BasicNameValuePair("t",new Date().toString()));
        list.add(new BasicNameValuePair("User_Name",User_Name));
        list.add(new BasicNameValuePair("User_QM","wb01"));
        list.add(new BasicNameValuePair("Email",Email));
        list.add(new BasicNameValuePair("action","User_save"));

        HttpEntity entity = new UrlEncodedFormEntity (list,"UTF-8");

        httpPost.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == 200){
            HttpEntity httpEntity = response.getEntity();
            responseContent = EntityUtils.toString(httpEntity,"UTF-8");

        }
        response.close();

        return responseContent;
    }

    public static void main(String[] args) throws IOException {
        SmsUtils smsUtils = new SmsUtils();
        String respContent = smsUtils.login("fivice","67321015wb");
        smsUtils.close();

    }
}
