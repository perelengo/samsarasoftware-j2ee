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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;

@Path("/listResources")
public class ResourceListingResource
{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String showAll( @Context Application application)
    {
        String basePath = "/";
 
        ObjectNode root = JsonNodeFactory.instance.objectNode();
        ArrayNode resources = JsonNodeFactory.instance.arrayNode();
 
        root.put( "resources", resources );
 
        for ( Class<?> aClass : application.getClasses() )
        {
            if ( isAnnotatedResourceClass( aClass ) )
            {
               
            	Resource resource = Resource.from( aClass );
                processResource(resource, resources,basePath);
            	
                
 


            }
 
        }
 
        //return Base64.encodeAsString(root.toString()) ;
        return root.toString();
    }
 
    private void processResource(Resource resource, ArrayNode resources, String basePath) {
        ObjectNode resourceNode = JsonNodeFactory.instance.objectNode();
        String uriPrefix = resource.getPath();
        for ( ResourceMethod srm : resource.getResourceMethods() )
        {
            addTo( resourceNode, uriPrefix, srm, joinUri( basePath, uriPrefix ) );
        }
        if(resource.getResourceMethods().size()>0)
        	resources.add( resourceNode );
        
        for ( Resource srm : resource.getChildResources())
        {
            processResource(srm,resources,basePath);
        }
		
	}

	private void addTo( ObjectNode resourceNode, String uriPrefix, ResourceMethod srm, String path )
    {
            ObjectNode inner = JsonNodeFactory.instance.objectNode();
            inner.put("path", path);
            inner.put("verbs", JsonNodeFactory.instance.arrayNode());
            resourceNode.put( uriPrefix, inner );
 
        ((ArrayNode) resourceNode.get( uriPrefix ).get("verbs")).add( srm.getHttpMethod() );
    }
 
 
    public static String joinUri( String... parts )
    {
        StringBuilder result = new StringBuilder();
        for ( String part : parts )
        {
            if ( result.length() > 0 && result.charAt( result.length() - 1 ) == '/' )
            {
                result.setLength( result.length() - 1 );
            }
            if ( result.length() > 0 && !part.startsWith( "/" ) )
            {
                result.append( '/' );
            }
            result.append( part );
        }
        return result.toString();
    }


    private boolean isAnnotatedResourceClass( Class rc )
    {
        if ( rc.isAnnotationPresent( Path.class ) )
        {
            return true;
        }

        for ( Class i : rc.getInterfaces() )
        {
            if ( i.isAnnotationPresent( Path.class ) )
            {
                return true;
            }
        }

        return false;
    }
 
}
