package com.stbbd.spider.framewark.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by 朱国印 on 16-12-12.
 */
@Slf4j
public class HttpClientUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    public static String doPost(String url, Map<String, String> map, String charset) {
        logger.info("url:" + url + "   params:" + map);
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String doGet(String requestUrl, Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        String requestParams = null;
        for (String key : params.keySet()) {
            sb.append(key);
            sb.append("=");
            String keyStr = "";
            try {
                keyStr = params.get(key);
                keyStr = java.net.URLEncoder.encode(keyStr, "utf-8");
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            sb.append(keyStr);
            sb.append("&");
        }
        requestParams = sb.toString();
        String result = "";
        BufferedReader in = null;
        try {

            System.out.println("requestUrl:" + requestUrl + "?" + requestParams);
            if (StringUtils.isNotBlank(requestParams)) {
                requestUrl = requestUrl + "?" + requestParams;
            }
            URL url = new URL(requestUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url
                    .openConnection();
            // 设置连接属性
            httpConn.setConnectTimeout(30000);
            httpConn.setDoInput(true);
            httpConn.setRequestMethod("GET");
            // 获取相应码
            int respCode = httpConn.getResponseCode();
            if (respCode == 200) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                // 将输入流转移到内存输出流中
                while ((len = httpConn.getInputStream().read(buffer, 0, buffer.length)) != -1) {
                    out.write(buffer, 0, len);
                }
                // 将内存流转换为字符串
                result = new String(out.toByteArray(), "utf-8");
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 解析 返回值
     *
     * @param con
     * @return
     */
    private static String parseResponse(HttpURLConnection con) {
        InputStream is = null;
        BufferedReader br = null;
        try {
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                is = con.getInputStream();
            } else {
                is = con.getErrorStream();
            }
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer buf = new StringBuffer();
            String line;
            while (null != (line = br.readLine())) {
                buf.append(line).append("\n");
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            con.disconnect();
        }
        return null;
    }

    static TrustManager[] xtmArray = new MytmArray[]{new MytmArray()};

    static class MytmArray implements X509TrustManager {
        MytmArray() {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
    }

    ;

    /**
     * 信任所有主机-对于任何证书都不做检查
     */
    private static void trustAllHosts() {
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, xtmArray, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(DO_NOT_VERIFY);//
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };


    public static String get(String url, Map<String, String> paramMap) {
        try (CloseableHttpClient client = getHttpClient()) {
            StringBuilder paramStr = new StringBuilder();
            if (null != paramMap) {
                paramMap.entrySet().forEach(key -> {
                    paramStr.append(key.getKey() + "=" + key.getValue() + "&");
                });
            }
            url = url + "?" + paramStr.toString();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
            return EntityUtils.toString(client.execute(httpGet).getEntity(), UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }


    public static String post(String url, Map<String, String> paramMap) {
        try (CloseableHttpClient client = getHttpClient()) {
            HttpPost httpost = new HttpPost(url);
            List <NameValuePair> nvps = new ArrayList <>();
            if (null != paramMap) {
                paramMap.entrySet().forEach(key -> {
                    nvps.add(new BasicNameValuePair(key.getKey(), key.getValue()));
                });
            }
            httpost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
            httpost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
            return EntityUtils.toString(client.execute(httpost).getEntity(), UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }


    protected static final CloseableHttpClient getHttpClient() {
        final HttpClientBuilder hcBuilder = HttpClients.custom();


        // Using `StandardHttpRequestRetryHandler` replace `DefaultHttpRequestRetryHandler`
        hcBuilder.setRetryHandler(new StandardHttpRequestRetryHandler(3, true));

        // Add retry strategy when service unavailable
        hcBuilder.setServiceUnavailableRetryStrategy(new ServiceUnavailableRetryStrategy() {
            private final Set<Integer> serviceUnavailableStatusCodes = new HashSet<>(
                    Arrays.asList(new Integer[]{
                            HttpStatus.SC_INTERNAL_SERVER_ERROR,
                            HttpStatus.SC_BAD_GATEWAY,
                            HttpStatus.SC_SERVICE_UNAVAILABLE,
                            HttpStatus.SC_GATEWAY_TIMEOUT})
            );
            private static final int maxRetries = 3;
            private static final long retryInterval = 3_000;

            @Override
            public boolean retryRequest(HttpResponse response, int executionCount, HttpContext context) {
                return executionCount <= maxRetries &&
                        serviceUnavailableStatusCodes.contains(response.getStatusLine().getStatusCode());
            }

            @Override
            public long getRetryInterval() {
                return retryInterval;
            }
        });

        return hcBuilder.build();
    }

}
