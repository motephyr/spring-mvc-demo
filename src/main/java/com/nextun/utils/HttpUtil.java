package com.nextun.utils;

import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

/**
 * httpclient all settings
 * 
 * @author supertolove
 * 
 */
public class HttpUtil {

  private DefaultHttpClient client;
  private static final Log log = LogFactory.getLog(HttpUtil.class);
  
  public static final String CHACTER_UTF8 = "UTF-8";
  public static final String CHACTER_BIG5 = "big5";

  private static final class SingletonHolder {
    private static final HttpUtil instance = new HttpUtil();
  }

  public synchronized DefaultHttpClient getClient() {
    return client;
  }

  private HttpUtil() {
    configureClient();
  }

  public static HttpUtil getInstance() {
    return SingletonHolder.instance;
  }

  public synchronized String getResult(String url, String charSet) {

    String result = null;
    HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
    HttpGet httpget = null;
    
    try {
      httpget = new HttpGet(url);
      response = client.execute(httpget);
      HttpEntity entity = response.getEntity();
      result = EntityUtils.toString(entity);
    } catch (Exception e) {
      log.error(e.toString());
    } finally {
      httpget.abort();
      closeExpiredConns();
      closeIdleConns();
    }
    result = StringUtils.defaultIfEmpty(result, "");
    
    return result;

  }
  
  
  public synchronized InputStream getResultAsStream(String url, String charSet) {

    InputStream result = null;
    HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
    HttpGet httpget = null;
    
    try {
      httpget = new HttpGet(url);
      response = client.execute(httpget);
      HttpEntity entity = response.getEntity();
      result = entity.getContent();
    } catch (Exception e) {
      log.error(e.toString());
    } finally {
      httpget.abort();
      closeExpiredConns();
      closeIdleConns();
    }
    
    return result;

  }
  
  

  private void configureClient() {

    // 假的 ssl
    TrustManager easyTrustManager = new X509TrustManager() {
      public X509Certificate[] getAcceptedIssuers() {
        return null;
      }
      public void checkServerTrusted(X509Certificate[] arg0, String arg1)
          throws CertificateException {
      }
      public void checkClientTrusted(X509Certificate[] arg0, String arg1)
          throws CertificateException {
      }
    };
    SSLSocketFactory sf = null;
    try {
      SSLContext sslcontext = SSLContext.getInstance("TLS");
      sslcontext.init(null, new TrustManager[] { easyTrustManager }, null);
      sf = new SSLSocketFactory(sslcontext);
    } catch (NoSuchAlgorithmException e) {
      log.error(e.getMessage(), e);
    } catch (KeyManagementException e) {
      log.error(e.getMessage(), e);
    }

    // Create and initialize scheme registry
    Scheme http = new Scheme("http", 80, PlainSocketFactory.getSocketFactory());
    Scheme https = new Scheme("https", 443, sf);

    SchemeRegistry schemeRegistry = new SchemeRegistry();
    schemeRegistry.register(http);
    schemeRegistry.register(https);

    // Create an HttpClient with the ThreadSafeClientConnManager.
    // This connection manager must be used if more than one thread will
    // be using the HttpClient.
    ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(
        schemeRegistry);

    // Increase max connections for localhost:80 to 50
    HttpHost localhost = new HttpHost("localhost", 8080);
    cm.setMaxForRoute(new HttpRoute(localhost), 50);
    // Increase default max connection per route to 20
    cm.setDefaultMaxPerRoute(20);
    cm.setMaxTotal(200);
    client = new DefaultHttpClient(cm);
    client.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
  } 

  public void shutdownHttp() {
    client.getConnectionManager().shutdown();
  }

  public void closeExpiredConns() {
    client.getConnectionManager().closeExpiredConnections();
  }

  public void closeIdleConns() {
    client.getConnectionManager().closeIdleConnections(50L, TimeUnit.SECONDS);
  }

}
