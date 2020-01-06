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
import java.io.Serializable;
 
/**
 * @author raidentrance
 *
 */
public class ErrorMessage implements Serializable {
    private String data;
    private String message;
    private Integer code;
 
    private static final long serialVersionUID = 1L;
 
    public ErrorMessage() {
    }
 
    public ErrorMessage(String message, String data, Integer code) {
        this.message = message;
        this.code = code;
        this.data=data;
    }

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
 
    
}
