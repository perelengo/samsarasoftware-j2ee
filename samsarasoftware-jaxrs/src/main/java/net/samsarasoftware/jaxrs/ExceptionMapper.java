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
