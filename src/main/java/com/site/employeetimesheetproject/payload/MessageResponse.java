package com.site.employeetimesheetproject.payload;

/**
 * ClassName: MessageResponse
 * Package: com.site.employeetimesheetproject.model
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
