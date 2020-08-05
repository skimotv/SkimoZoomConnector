package tv.skimo.connector.lib;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL; 
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import org.slf4j.Logger;

public class JGet 
{  
  public static void go(String url, String fileName, Logger log)
  {
    TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() 
    {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() 
        {
          return null;
        }
        public void checkClientTrusted(X509Certificate[] certs, String authType) 
        {
        }
        public void checkServerTrusted(X509Certificate[] certs, String authType) 
        {
        }
      }
    };
    SSLContext sc = null;
	
    try 
	{
		sc = SSLContext.getInstance("SSL");
	} 
	catch (NoSuchAlgorithmException e1) 
	{
		e1.printStackTrace();
	}

	try 
    {
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
	} 
    catch (KeyManagementException e1) 
	{
    	e1.printStackTrace();
	}
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    
    HostnameVerifier allHostsValid = new HostnameVerifier() 
    {
      public boolean verify(String hostname, SSLSession session) 
      {
        return true;
      }
    };
    try (BufferedInputStream inputStream = new BufferedInputStream(new URL(url).openStream());
    		 FileOutputStream fileOS = new FileOutputStream(fileName)) 
    {
    	byte data[] = new byte[1024];
    	int byteContent;
    	while ((byteContent = inputStream.read(data, 0, 1024)) != -1) 
    	{
    		fileOS.write(data, 0, byteContent);
    	}
    } 
    catch (IOException e) 
    {
    	log.info(e.toString());
    }
  }
}