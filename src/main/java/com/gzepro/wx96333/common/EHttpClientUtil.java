package com.gzepro.wx96333.common;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;



/**
 * @描述 网络请求类
 * @日期 2016年7月13日
 * @作者 asus-PC
 */
public class EHttpClientUtil {

	/**
     * post提交对象字符串
     * @param url
     * @param objectStr
     * @return
     * @throws Exception
     */
    public static String httpPostEntity(String url, String objectStr)
                    throws Exception {

            DefaultHttpClient httpClient = new DefaultHttpClient();

            HttpPost method = new HttpPost(url);

            StringEntity entity = new StringEntity(objectStr, "utf-8");// 解决中文乱码问题
            entity.setContentEncoding("UTF-8");
//          entity.setContentType("application/json");
            method.setEntity(entity);

            HttpResponse result = httpClient.execute(method);

            // 请求结束，返回结果
            String resData = EntityUtils.toString(result.getEntity(), "utf-8");

            return resData;
    }

    
    /**
     * post提交参数
     * @param url
     * @param paramsMap
     * @return
     */
    public static String httpPost(String url, Map<String, String> paramsMap) {

            List<NameValuePair> reqEntity = new ArrayList<NameValuePair>();
            if (paramsMap != null && paramsMap.size() > 0) {
                    for (String key : paramsMap.keySet()) {
                            reqEntity.add(new BasicNameValuePair(key, paramsMap.get(key)));
                    }
            }

            try {

                    return PostHttpClientService(url, reqEntity);

            } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
            return null;
    }

    /**
     * 
     * @param reqEntity
     * @param url
     * @param t
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * 
     *             List <NameValuePair> reqEntity = new ArrayList
     *             <NameValuePair>(); reqEntity.add(new
     *             BasicNameValuePair("goods_name", goods.getGoodsName()));
     */
    public static String PostHttpClientService(String url,
                    List<NameValuePair> reqEntity) throws ClientProtocolException,
                    IOException {
            HttpClient httpclient = new DefaultHttpClient();
            String responseBody = "";
            System.out.println("请求地址：" + url);
            
            // 请求超时
            // httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
            // 60000);
            // 读取超时
            // httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
            // 60000);

            // httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
            // "gzepro");
            
            try {
                    // 请求处理页面
                    HttpPost httppost = new HttpPost(url);
//                  httppost.setHeader("User-Agent","gzepro");
                    // 设置请求
                    if (reqEntity != null)
                            httppost.setEntity(new UrlEncodedFormEntity(reqEntity,
                                            HTTP.UTF_8));

                    // 执行
                    HttpResponse response = httpclient.execute(httppost);
                    if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                            HttpEntity entity = response.getEntity();
                            // 显示内容
                            if (entity != null) {
                                    responseBody = EntityUtils.toString(entity, HTTP.UTF_8);
                            }
                            if (entity != null) {
                                    entity.consumeContent();
                            }
                    } else {
                            responseBody = response.getStatusLine().getStatusCode() + "";
                    }
            } catch (RuntimeException ex) {
                    // 当发生意料之外的异常时，你希望通过终于http请求以立即关闭底层连接
                    ex.printStackTrace();
            } catch (Exception e) {
                    e.printStackTrace();
            } finally {
                    httpclient.getConnectionManager().shutdown();
            }

            return responseBody;
    }
    
    public static String postHttpClient(String url,
                    Map<String, String> paramsMap , Map<String , String > headerMap) throws ClientProtocolException,
                    IOException {
            
            List<NameValuePair> reqEntity = new ArrayList<NameValuePair>();
            if (paramsMap != null && paramsMap.size() > 0) {
                    for (String key : paramsMap.keySet()) {
                            reqEntity.add(new BasicNameValuePair(key, paramsMap.get(key)));
                    }
            }
            
            HttpClient httpclient = new DefaultHttpClient();
            String responseBody = "";
            System.out.println("请求地址：" + url);
            // 请求超时
            try {
                    // 请求处理页面
                    HttpPost httppost = new HttpPost(url);
                    if (headerMap != null && headerMap.size() > 0) {
                            for(Map.Entry<String, String> entry:headerMap.entrySet()){ 
                                    httppost.setHeader(entry.getKey(),entry.getValue());
                            } 
                    } 
                    // 设置请求
                    if (reqEntity != null)
                            httppost.setEntity(new UrlEncodedFormEntity(reqEntity,
                                            HTTP.UTF_8));

                    // 执行
                    HttpResponse response = httpclient.execute(httppost);
                    if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                            HttpEntity entity = response.getEntity();
                            // 显示内容
                            if (entity != null) {
                                    responseBody = EntityUtils.toString(entity, HTTP.UTF_8);
                            }
                            if (entity != null) {
                                    entity.consumeContent();
                            }
                    } else {
                            responseBody = response.getStatusLine().getStatusCode() + "";
                    }
            } catch (RuntimeException ex) {
                    // 当发生意料之外的异常时，你希望通过终于http请求以立即关闭底层连接
                    ex.printStackTrace();
            } catch (Exception e) {
                    e.printStackTrace();
            } finally {
                    httpclient.getConnectionManager().shutdown();
            }

            return responseBody;
    }

    /**
     * 不用带参数的请求方式
     * 
     * @param url
     * @param objectStr
     * @return
     */
    public static String httpClientRequest(String url, String objectStr) {
            String resData = null;
            DefaultHttpClient httpClient = new DefaultHttpClient();
            try {

                    HttpPost method = new HttpPost(url);

                    StringEntity entity = new StringEntity(objectStr, "utf-8");// 解决中文乱码问题
                    entity.setContentEncoding("UTF-8");
                    entity.setContentType("application/json");
                    method.setEntity(entity);

                    HttpResponse result = httpClient.execute(method);

                    // 请求结束，返回结果
                    resData = EntityUtils.toString(result.getEntity(), "utf-8");

                    System.out.println(resData);

            } catch (Exception e) {
                    e.printStackTrace();
            } finally {
                    httpClient.close();
            }

            return resData;
    }

    /**
     * 带证书的请求方式
     * 
     * @param url
     * @param objectStr
     * @return
     */
    public static String ClientCustomSSL(String url, String objectStr,
                    String weixin_p12_path, String weixin_mch_id) {
            String resData = null;
            try {
                    KeyStore keyStore = KeyStore.getInstance("PKCS12");
                    FileInputStream instream = new FileInputStream(new File(
                                    weixin_p12_path));
                    try {
                            keyStore.load(instream, weixin_mch_id.toCharArray());
                    } finally {
                            instream.close();
                    }

                    // Trust own CA and all self-signed certs
                    SSLContext sslcontext = SSLContexts.custom()
                                    .loadKeyMaterial(keyStore, weixin_mch_id.toCharArray())
                                    .build();
                    // Allow TLSv1 protocol only
                    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                                    sslcontext,
                                    new String[] { "TLSv1" },
                                    null,
                                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
                    CloseableHttpClient httpClient = HttpClients.custom()
                                    .setSSLSocketFactory(sslsf).build();
                    try {

                            // DefaultHttpClient httpClient = new DefaultHttpClient();

                            HttpPost method = new HttpPost(url);

                            StringEntity entity = new StringEntity(objectStr, "utf-8");// 解决中文乱码问题
                            entity.setContentEncoding("UTF-8");
                            entity.setContentType("application/json");
                            method.setEntity(entity);

                            HttpResponse result = httpClient.execute(method);

                            // 请求结束，返回结果
                            resData = EntityUtils.toString(result.getEntity(), "utf-8");

                            System.out.println("证书接口返回=================================");
                            System.out.println(resData);

                    } finally {
                            httpClient.close();
                    }

            } catch (Exception e) {
                    e.printStackTrace();
            }

            return resData;
    }

    /**
     * @Title: methodPost
     * @Description: httpclient方法中post提交数据的使用
     * @param @return
     * @param @throws Exception
     * @return String
     * @throws
     */
    public static String methodPost(String url, List<BasicNameValuePair> params)
                    throws Exception {
            String resData = null;
            DefaultHttpClient httpclient = new DefaultHttpClient();
            // // 代理的设置
            // HttpHost proxy = new HttpHost("10.60.8.20", 8080);
            // httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
            // proxy);

            // 目标地址
            HttpPost httppost = new HttpPost(url);
            System.out.println("请求: " + httppost.getRequestLine());

            // post 参数 传递
            // List<BasicNameValuePair> params = new
            // ArrayList<BasicNameValuePair>();
            // params.add(new BasicNameValuePair("content", "11111111")); // 参数
            // params.add(new BasicNameValuePair("path", "D:/file")); // 参数
            // params.add(new BasicNameValuePair("name", "8")); // 参数
            // params.add(new BasicNameValuePair("age", "9")); // 参数
            // params.add(new BasicNameValuePair("username", "wzt")); // 参数

            httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); // 设置参数给Post

            // 执行
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            System.out.println(response.getStatusLine());
            if (entity != null) {
//                  System.out.println("Response content length: "
//                                  + entity.getContentLength());
                    resData = EntityUtils.toString(entity, "utf-8");
            }
            // 显示结果
//          BufferedReader reader = new BufferedReader(new InputStreamReader(
//                          entity.getContent(), "UTF-8"));
//
//          String line = null;
//          while ((line = reader.readLine()) != null) {
//                  System.out.println(line);
//          }
            if (entity != null) {
                    entity.consumeContent();
            }
            return resData;

    }

    /**
     * @Title: methodGet
     * @Description: 以get方法提交数的使用
     * @param @return
     * @param @throws Exception
     * @return String
     * @throws
     */
    public static String methodGet(String url) throws Exception {
            String resData = null;
            DefaultHttpClient httpclient = new DefaultHttpClient();
            // // 代理的设置
            // HttpHost proxy = new HttpHost("10.60.8.20", 8080);
            // httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
            // proxy);

            // 目标地址
            HttpPost httpGet = new HttpPost(url);

            // 构造最简单的字符串数据
            // StringEntity reqEntity = new StringEntity("name=test&password=test");
            // 设置类型
            // reqEntity.setContentType("application/x-www-form-urlencoded");
            // 设置请求的数据
            // httpGet.setEntity(reqEntity);

            // 执行
            HttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
//          System.out.println(response.getStatusLine());

            if (entity != null) {
//                  System.out.println("Response content length: "
//                                  + entity.getContentLength()); // 得到返回数据的长度
                    resData = EntityUtils.toString(entity, "utf-8");
            }
            // 显示结果
            // BufferedReader reader = new BufferedReader(new
            // InputStreamReader(entity.getContent(), "UTF-8"));
            //
            // String line = null;
            // while ((line = reader.readLine()) != null) {
            // System.out.println(line);
            // }
            // if (entity != null) {
            // entity.consumeContent();
            // }
            return resData;

    }
    
    /**
     * 
     * @param url  "http://images.sohu.com/uiue/sohu_logo/beijing2008/2008sohu.gif"
     * @param filename "e:/2008sohu.gif"
     * @throws Exception
     */
    public static String downloadFile(String url ,String filename) {
//          System.out.println("httpclient download url :"+url);
            try{
                    
                    DefaultHttpClient httpClient = new DefaultHttpClient(); 
                    
                    HttpGet httpGet = new HttpGet(url);
                    HttpResponse response = httpClient.execute(httpGet);  
                    
                    String filepath = filename.substring(0,filename.lastIndexOf("/"));
                    File file = new File(filepath);         
                    if (!file.exists()) {           
                            file.mkdirs();          
                    }
                    
            File storeFile = new File(filename);  
            FileOutputStream output = new FileOutputStream(storeFile);  
            //得到网络资源的字节数组,并写入文件  
            InputStream inputStream = response.getEntity().getContent();
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(data)) != -1) {
                    output.write(data, 0, len);
            }
            inputStream.close();  
            output.close();
            
            }catch(Exception e ){
                    e.printStackTrace();
            }
             
    
    return "success";
    }
    
    
    public static String postHttpClientFile(String url,
                    Map<String, String> paramsMap , 
                    Map<String, String> fileMap , 
                    Map<String , String > headerMap) throws ClientProtocolException,
                    IOException {
            
            MultipartEntity reqEntity = new MultipartEntity(); 
            
            if (paramsMap != null && paramsMap.size() > 0) {
                    for (String key : paramsMap.keySet()) {
                            reqEntity.addPart(key, new StringBody(paramsMap.get(key)));
                    }
            }
            
            if (fileMap != null && fileMap.size() > 0) {
                    for (String key : fileMap.keySet()) {
                            reqEntity.addPart(key, new FileBody(new File(fileMap.get(key))));
                    }
            }
            
            
            HttpClient httpclient = new DefaultHttpClient();
            String responseBody = "";
            System.out.println("请求地址：" + url);
            // 请求超时
            try {
                    // 请求处理页面
                    HttpPost httppost = new HttpPost(url);
                    if (headerMap != null && headerMap.size() > 0) {
                            for(Map.Entry<String, String> entry:headerMap.entrySet()){ 
                                    httppost.setHeader(entry.getKey(),entry.getValue());
                            } 
                    }
                    // 设置请求
                    if (reqEntity != null)
                            httppost.setEntity(reqEntity);

                    // 执行
                    HttpResponse response = httpclient.execute(httppost);
                    if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                            HttpEntity entity = response.getEntity();
                            // 显示内容
                            if (entity != null) {
                                    responseBody = EntityUtils.toString(entity, HTTP.UTF_8);
                            }
                            if (entity != null) {
                                    entity.consumeContent();
                            }
                    } else {
                            responseBody = response.getStatusLine().getStatusCode() + "";
                    }
            } catch (RuntimeException ex) {
                    // 当发生意料之外的异常时，你希望通过终于http请求以立即关闭底层连接
                    ex.printStackTrace();
            } catch (Exception e) {
                    e.printStackTrace();
            } finally {
                    httpclient.getConnectionManager().shutdown();
            }

            return responseBody;
    }
    
    
}
