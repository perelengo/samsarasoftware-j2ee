package net.samsarasoftware.jaxrs;

/*-
 * #%L
 * samsarasoftware-jaxrs
 * %%
 * Copyright (C) 2014 - 2020 Pere Joseph Rodriguez
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
	
