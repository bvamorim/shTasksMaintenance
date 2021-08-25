package com.sh.tasks.api;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

/**
 * <h1>RequestHelper</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-24
 */
public class RequestHelper {

    public static final String LIST_TASK_URL = "task/list";
    public static final String LISTALL_TASK_URL = "task/listAll";
    public static final String ADD_TASK_URL = "task/add";
    public static final String FIND_TASK_URL = "task/find/%s";
    public static final String SETPERFORMED_TASK_URL = "task/setTaskPerformed/%s";
    public static final String UPDATE_TASK_URL = "task/update/%s";
    public static final String DELETE_TASK_URL = "task/delete/%s";

    public static final String AUTHENTICATE_URL = "authenticate";

    public static HttpEntity getPostDefaultHeader(String jwtToken, Object content){

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+ jwtToken);
        headers.set("Content-Type", "application/json");

        return new HttpEntity<>(content, headers);
    }

    public static HttpEntity getDefaultHeader(String jwtToken){

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+ jwtToken);
        headers.set("Content-Type", "application/json");

        return new HttpEntity<>(headers);
    }

    public static URI buildURI(int port, String url) throws URISyntaxException {
        return new URI("http://localhost:" + port +"/"+ url);
    }

    public static URI buildURI(int port, String url, String... params) throws URISyntaxException {

        String paramURL = String.format(url, params);

        return new URI("http://localhost:" + port +"/"+ paramURL);
    }
}
