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

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Provider
@Consumes("application/json")
@Produces("application/json")
public class NullSerializationJacksonConfigurator implements ContextResolver<ObjectMapper> {

    public NullSerializationJacksonConfigurator() {
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    }

    public ObjectMapper getContext(Class<?> clazz) {
        return mapper;
    }

    private final ObjectMapper mapper = new ObjectMapper();
}
