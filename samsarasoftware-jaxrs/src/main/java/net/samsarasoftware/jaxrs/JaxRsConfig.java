package net.samsarasoftware.jaxrs;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;
public class JaxRsConfig extends Application {

     @Override
     public Set<Class<?>> getClasses() {
         Set<Class<?>> classes = new HashSet<Class<?>>();

         // your classes here
         classes.add(ISO8601DateJacksonConfigurator.class);
         classes.add(ISO8601DateProvider.class);
         classes.add(ExceptionMapper.class);
         return classes;
      }

}