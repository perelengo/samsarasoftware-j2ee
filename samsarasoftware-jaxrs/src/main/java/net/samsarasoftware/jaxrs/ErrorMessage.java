package net.samsarasoftware.jaxrs;
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