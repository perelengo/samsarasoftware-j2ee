package net.samsarasoftware.xadisk.servlet;

/*-
 * #%L
 * samsarasoftware-xadisk-servlet
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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import net.samsarasoftware.xadisk.XADiskBootstrap;

public class XADiskBootstrapServlet implements ServletContextListener {

	protected static Logger  log=Logger.getLogger(XADiskBootstrapServlet.class);
	
	protected XADiskBootstrap instance;
	
	public void contextDestroyed(ServletContextEvent event) {
		if(event.getServletContext().getInitParameter("XAInstanceJndi")==null){ 
			String instanceId=getValueFromVars(event.getServletContext().getInitParameter("XAInstanceId"));
			instance.shutdown(instanceId);
		}
	}

	public void contextInitialized(ServletContextEvent event) {
		Map<String,String> options=new HashMap<String, String>();
		
		Enumeration paramsEnum = event.getServletContext().getInitParameterNames();
		while(paramsEnum.hasMoreElements()) {
			try{
				
				String param = (String) paramsEnum.nextElement(); 
				if(param.startsWith("XA")){
					String xaParam=param.substring(2,3).toLowerCase()+param.substring(3);
					options.put(xaParam,getValueFromVars(event.getServletContext().getInitParameter(param)));
				}
			}catch(Exception t){
				log.debug(t.getMessage()+" : "+event.getServletContext().getInitParameter("XAInstanceId")+" : at "+t.getStackTrace()[0].toString());
			}
		}
		
		instance=new XADiskBootstrap();
		try {
			instance.start(options);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
	}
	
	protected String getValueFromVars(String val){
		if(val==null) return val;
		
		Pattern sysprop=Pattern.compile("\\$\\{([^\\}]*)\\}");
		Matcher m=sysprop.matcher(val);
		StringBuffer sb=null;
		int last=-1;
		while(m.find()){
			if(sb==null) sb=new StringBuffer();
			String pName=m.group(1);
			m.appendReplacement(sb, System.getProperty(pName));
			last=m.end();
		}
		if(sb!=null && last!=val.length())
			sb.append(val.substring(last));
		
		if (sb==null){
			return val;
		}else{
			return sb.toString();
		}
	}

	

}
