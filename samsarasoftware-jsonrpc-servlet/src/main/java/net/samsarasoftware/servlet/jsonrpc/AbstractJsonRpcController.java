package net.samsarasoftware.servlet.jsonrpc;

/*-
 * #%L
 * samsarasoftware-jsonrpc-servlet
 * %%
 * Copyright (C) 2014 - 2017 Pere Joseph Rodriguez
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 * #L%
 */


import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.stefaniuk.json.service.JsonServiceRegistry;


public abstract class AbstractJsonRpcController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private JsonServiceRegistry service = new JsonServiceRegistry();

	private String lock;

	private static int lockCounter=0;
	
	public AbstractJsonRpcController() {
		lock=this.getClass().getName()+"_lock_"+getNextLockInt();
	}
	
	private int getNextLockInt() {
		return ++lockCounter;
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		synchronized (lock) {
			Object proxy=getProxy();
			service.register(proxy);
			service.getServiceMap(proxy.getClass(), response);
			service.unregister(proxy);
		}
		
		response.setStatus(200);
	}

	protected abstract Object getProxy();

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try{
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			
			synchronized (lock){
    			
    			Object proxy=getProxy();
    			service.register(proxy);
        		service.handle(request, response, proxy.getClass());
        		service.unregister(proxy);


    		}
        } catch (Exception t) {
        	ByteArrayOutputStream str=new ByteArrayOutputStream();
        	PrintWriter pw=new PrintWriter(str,true);
            try {
            	t.printStackTrace(pw);
        		response.getOutputStream().write(new String("{\"jsonrpc\": \"2.0\", \"error\": {\"code\": -1, \"message\": \""+URLEncoder.encode(t.getMessage(),"UTF-8")+"\",\"data\":\""+URLEncoder.encode(str.toString(),"UTF-8")+"\"}, \"id\": \"1\"}").getBytes("UTF-8"));
            	response.setStatus(500);
			} catch (IOException e) {
			}finally{
				if(pw!=null){try{pw.close();}catch(Exception e){}}
				if(str!=null){try{str.close();}catch(Exception e){}}
			}
        }    
	}
}
