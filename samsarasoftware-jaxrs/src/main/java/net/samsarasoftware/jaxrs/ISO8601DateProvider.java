package net.samsarasoftware.jaxrs;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

@Provider
public class ISO8601DateProvider implements ParamConverterProvider {

    public <T> ParamConverter<T> getConverter(final Class<T> rawType, 
                                              Type genericType, 
                                              Annotation[] annotations) {

        if (rawType != Date.class) return null; 

        return new ParamConverter<T>() {

        	public T fromString(String value) {
                try {
                	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    return rawType.cast(dateFormat.parse(value));
                } catch (Exception ex) {
                    throw new WebApplicationException(
                        Response.status(400).entity("Can't convert input string to java.util.Date: "+value).build());
                }
            }

            public String toString(T value) {
                return value.toString();
            }  
        };
    }
}
	