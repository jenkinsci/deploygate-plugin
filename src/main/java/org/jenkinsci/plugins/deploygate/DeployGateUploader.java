package org.jenkinsci.plugins.deploygate;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class DeployGateUploader {
	private String UrlString;
	private String fileName;
	private StringBody message;
	private StringBody token;
	public DeployGateUploader(String Url){
		UrlString =Url;
	}
	public DeployGateUploader(String Url,String message,String token,String fileName){
		UrlString =Url;
		try {
			this.message = new StringBody(message);
			this.token = new StringBody(token);
			this.fileName = fileName;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void upload(){
		try{
	      HttpClient httpClient = new DefaultHttpClient();
	      HttpPost httpPost = new HttpPost(UrlString);
	      ResponseHandler<String> responseHandler =
	        new BasicResponseHandler();
	      MultipartEntity multipartEntity =
	        new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	      
	      File file = new File(fileName);
	      FileBody fileBody = new FileBody(file, "application/vnd.android.package-archive");
	      multipartEntity.addPart("file", fileBody);
	      multipartEntity.addPart("message",message);
	      multipartEntity.addPart("token",token);
	      httpPost.setEntity(multipartEntity);
	      httpClient.execute(httpPost, responseHandler);
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
	@Override
	public String toString() {
		return "DeployGateUploader [UrlString=" + UrlString + ", fileName="
				+ fileName + ", message=" + message + ", token=" + token + "]";
	}

}
