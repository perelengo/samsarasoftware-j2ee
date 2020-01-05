package net.samsarasoftware.jaxrs;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
 
@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<ApplicationException> {
	@Context
	javax.ws.rs.core.HttpHeaders headers;

    public Response toResponse(ApplicationException ex) {
        List<MediaType> mediaType=headers.getAcceptableMediaTypes();
    	ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(ex.getMessage());
        StringWriter errorStackTrace = new StringWriter();
        ex.printStackTrace(new PrintWriter(errorStackTrace));
        errorMessage.setData(errorStackTrace.toString());
        errorMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        
        if(mediaType.isEmpty())
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).type("application/json").build();
        else
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).type(mediaType.get(0)).build();
    }
 
}