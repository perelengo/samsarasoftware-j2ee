package net.samsarasoftware.jaxrs;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

@Provider
@Consumes("application/json")
@Produces("application/json")
public class ISO8601DateJacksonConfigurator implements ContextResolver<ObjectMapper> {

    public ISO8601DateJacksonConfigurator() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        mapper.setDateFormat(dateFormat);
        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public ObjectMapper getContext(Class<?> clazz) {
        return mapper;
    }

    private final ObjectMapper mapper = new ObjectMapper();
}