package com.sh.tasks.api;

import static com.sh.tasks.api.RequestHelper.ADD_TASK_URL;
import static com.sh.tasks.api.RequestHelper.AUTHENTICATE_URL;
import static com.sh.tasks.api.RequestHelper.DELETE_TASK_URL;
import static com.sh.tasks.api.RequestHelper.FIND_TASK_URL;
import static com.sh.tasks.api.RequestHelper.LISTALL_TASK_URL;
import static com.sh.tasks.api.RequestHelper.LIST_TASK_URL;
import static com.sh.tasks.api.RequestHelper.SETPERFORMED_TASK_URL;
import static com.sh.tasks.api.RequestHelper.UPDATE_TASK_URL;
import static com.sh.tasks.api.RequestHelper.buildURI;
import static com.sh.tasks.api.RequestHelper.getDefaultHeader;
import static com.sh.tasks.api.RequestHelper.getPostDefaultHeader;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.http.HttpMethod.GET;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.sh.tasks.api.databuilder.TaskDataBuilder;
import com.sh.tasks.api.databuilder.UserDataBuilder;
import com.sh.tasks.api.model.JwtRequest;
import com.sh.tasks.api.model.JwtResponse;
import com.sh.tasks.api.model.Task;
import com.sh.tasks.api.model.datatransferobject.UserDto;

/**
 * <h1>TasksApplicationTest</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TasksApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String jwtTokenManager;
    private String jwtTokenTechnician;
    private String jwtTokenAnotherTechnician;

    private UserDto currentUserManager;
    private UserDto currentUserTechnician;
    private UserDto currentUserAnotherTechnician;

    @Before
    public void init() throws URISyntaxException {
    	currentUserManager = UserDataBuilder.getDefaultUserManager();
    	currentUserTechnician = UserDataBuilder.getDefaultUserTechnician();
    	currentUserAnotherTechnician = UserDataBuilder.getDefaultUserAnotherTechnician();
    	
        this.jwtTokenManager = getJWTToker(currentUserManager);
        this.jwtTokenTechnician = getJWTToker(currentUserTechnician);
        this.jwtTokenAnotherTechnician = getJWTToker(currentUserAnotherTechnician);
    }

    @After
    public void tearDown(){
        //Do something after
    }

    private String getJWTToker(UserDto user) throws URISyntaxException {

        JwtRequest authenticationRequest = new JwtRequest();
        authenticationRequest.setPassword(user.getPassword());
        authenticationRequest.setUsername(user.getUsername());
        ResponseEntity<JwtResponse> jwtResponseResponseEntity =
                this.restTemplate.postForEntity(buildURI(port, AUTHENTICATE_URL), authenticationRequest, JwtResponse.class);

        return jwtResponseResponseEntity.getBody().getJwttoken();
    }


    @Test
    public void deleteTask_ValidInput_ShouldReturnNoError() throws Exception {

        Task task = TaskDataBuilder.getTask();
        HttpEntity requestADD = getPostDefaultHeader(this.jwtTokenTechnician, task);

        //add task - technician
        ResponseEntity<Task> result = restTemplate.postForEntity(buildURI(port, ADD_TASK_URL), requestADD, Task.class);
        Task taskAdded = result.getBody();

        assertThat(result.getStatusCode(), equalTo(HttpStatus.CREATED));

        String taskId = String.valueOf(taskAdded.getId());

        //delete task - manager
        URI uri = buildURI(port, DELETE_TASK_URL, taskId);

        HttpEntity requestDELETE = getDefaultHeader(this.jwtTokenManager);
        ResponseEntity<String> resultDelete = restTemplate.exchange(uri, HttpMethod.DELETE, requestDELETE, String.class);

        assertThat(resultDelete.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));

        //find task
        ResponseEntity<Task> resultFind = restTemplate.exchange(buildURI(port, FIND_TASK_URL, taskId), GET, getDefaultHeader(this.jwtTokenTechnician), Task.class);
        Task taskActual = resultFind.getBody();

        assertThat(resultFind.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

        Assertions.assertThat(taskActual.getId()).isNull();
    }
    
    @Test
    public void deleteTask_TechnicianRoleInput_ShouldReturnNoError() throws Exception {

        Task task = TaskDataBuilder.getTask();
        HttpEntity requestADD = getPostDefaultHeader(this.jwtTokenTechnician, task);

        //add task - technician
        ResponseEntity<Task> result = restTemplate.postForEntity(buildURI(port, ADD_TASK_URL), requestADD, Task.class);
        Task taskAdded = result.getBody();

        assertThat(result.getStatusCode(), equalTo(HttpStatus.CREATED));

        String taskId = String.valueOf(taskAdded.getId());

        //delete task - manager
        URI uri = buildURI(port, DELETE_TASK_URL, taskId);

        HttpEntity requestDELETE = getDefaultHeader(this.jwtTokenTechnician);
        ResponseEntity<String> resultDelete = restTemplate.exchange(uri, HttpMethod.DELETE, requestDELETE, String.class);

        assertThat(resultDelete.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));

        //find task
        ResponseEntity<Task> resultFind = restTemplate.exchange(buildURI(port, FIND_TASK_URL, taskId), GET, getDefaultHeader(this.jwtTokenTechnician), Task.class);
        Task taskActual = resultFind.getBody();

        assertThat(resultFind.getStatusCode(), equalTo(HttpStatus.OK));

        Assertions.assertThat(taskActual.getId()).isNotNull();
    }

    @Test
    public void updateTask_ValidInput_ShouldReturnNoError() throws Exception {

        Task task = TaskDataBuilder.getTask();
        HttpEntity request = getPostDefaultHeader(this.jwtTokenTechnician, task);

        //add task - technician
        ResponseEntity<Task> result = restTemplate.postForEntity(buildURI(port, ADD_TASK_URL), request, Task.class);
        Task taskAdded = result.getBody();

        assertThat(result.getStatusCode(), equalTo(HttpStatus.CREATED));

        String taskId = String.valueOf(taskAdded.getId());

        //update task - technician
        URI uri = buildURI(port, UPDATE_TASK_URL, taskId);
        taskAdded.setName("TASK_NAME_UPDATED");
        taskAdded.setDescription("TASK_DESCRIPTION_UPDATED");

        HttpEntity requestPOST = getPostDefaultHeader(this.jwtTokenTechnician, taskAdded);

        Task taskActual = restTemplate.patchForObject(uri, requestPOST, Task.class);

        assertThat(taskAdded.getId(), equalTo(taskActual.getId()));
        assertThat(taskAdded.getName(), equalTo(taskActual.getName()));
        assertThat(taskAdded.getDescription(), equalTo(taskActual.getDescription()));
    }
    
    @Test
    public void updateTask_ValidInputManagerRole_ShouldReturnNotAuthorized() throws Exception {

        Task task = TaskDataBuilder.getTask();
        HttpEntity request = getPostDefaultHeader(this.jwtTokenTechnician, task);

        //add task - technician
        ResponseEntity<Task> result = restTemplate.postForEntity(buildURI(port, ADD_TASK_URL), request, Task.class);
        Task taskAdded = result.getBody();

        assertThat(result.getStatusCode(), equalTo(HttpStatus.CREATED));

        String taskId = String.valueOf(taskAdded.getId());

        //update task - technician
        URI uri = buildURI(port, UPDATE_TASK_URL, taskId);
        taskAdded.setName("TASK_NAME_UPDATED");
        taskAdded.setDescription("TASK_DESCRIPTION_UPDATED");

        HttpEntity requestPOST = getPostDefaultHeader(this.jwtTokenManager, taskAdded);

        Task taskActual = restTemplate.patchForObject(uri, requestPOST, Task.class);

        Assertions.assertThat(taskActual.getId()).isNull();
    }
    
    @Test
    public void setTaskPerformed_ValidInput_ShouldReturnNoError() throws Exception {

        Task task = TaskDataBuilder.getTask();
        HttpEntity request = getPostDefaultHeader(this.jwtTokenTechnician, task);

        //add task - technician
        ResponseEntity<Task> result = restTemplate.postForEntity(buildURI(port, ADD_TASK_URL), request, Task.class);
        Task taskAdded = result.getBody();

        assertThat(result.getStatusCode(), equalTo(HttpStatus.CREATED));

        String taskId = String.valueOf(taskAdded.getId());

        //update task - technician
        URI uri = buildURI(port, SETPERFORMED_TASK_URL, taskId);

        HttpEntity requestPOST = getPostDefaultHeader(this.jwtTokenTechnician, taskAdded);

        Task taskActual = restTemplate.patchForObject(uri, requestPOST, Task.class);

        assertThat(taskAdded.getId(), equalTo(taskActual.getId()));
        assertThat(LocalDate.now(), equalTo(taskActual.getDatePerformed()));
    }
    
    @Test
    public void setTaskPerformed_ValidInputManagerRole_ShouldReturnNotAuthorized() throws Exception {

        Task task = TaskDataBuilder.getTask();
        HttpEntity request = getPostDefaultHeader(this.jwtTokenTechnician, task);

        //add task - technician
        ResponseEntity<Task> result = restTemplate.postForEntity(buildURI(port, ADD_TASK_URL), request, Task.class);
        Task taskAdded = result.getBody();

        assertThat(result.getStatusCode(), equalTo(HttpStatus.CREATED));

        String taskId = String.valueOf(taskAdded.getId());

        //update task - technician
        URI uri = buildURI(port, SETPERFORMED_TASK_URL, taskId);

        HttpEntity requestPOST = getPostDefaultHeader(this.jwtTokenManager, taskAdded);

        Task taskActual = restTemplate.patchForObject(uri, requestPOST, Task.class);

        Assertions.assertThat(taskActual.getDatePerformed()).isNull();
    }


    @Test
    public void findTask_ValidInput_ShouldReturnNoError() throws Exception {

        Task task = TaskDataBuilder.getTask();

        HttpEntity requestGET = getDefaultHeader(this.jwtTokenTechnician);
        HttpEntity request = getPostDefaultHeader(this.jwtTokenTechnician, task);

        //add task - technician
        ResponseEntity<Task> result = restTemplate.postForEntity(buildURI(port, ADD_TASK_URL), request, Task.class);
        Task taskAdded = result.getBody();

        assertThat(result.getStatusCode(), equalTo(HttpStatus.CREATED));

        String taskId = String.valueOf(taskAdded.getId());

        //find task - technician
        URI uri = buildURI(port, FIND_TASK_URL, taskId);

        ResponseEntity<Task> resultFind = restTemplate.exchange(uri, GET, requestGET, Task.class);
        Task taskActual = resultFind.getBody();

        assertThat(taskAdded.getId(), equalTo(taskActual.getId()));
    }
    
    @Test
    public void findTask_ValidInputAnotherTechnician_ShouldReturnNotAuthorized() throws Exception {

        Task task = TaskDataBuilder.getTask();

        HttpEntity requestGET = getDefaultHeader(this.jwtTokenAnotherTechnician);
        HttpEntity request = getPostDefaultHeader(this.jwtTokenTechnician, task);

        //add task - technician 01
        ResponseEntity<Task> result = restTemplate.postForEntity(buildURI(port, ADD_TASK_URL), request, Task.class);
        Task taskAdded = result.getBody();

        assertThat(result.getStatusCode(), equalTo(HttpStatus.CREATED));

        String taskId = String.valueOf(taskAdded.getId());

        //find task - technician 02
        URI uri = buildURI(port, FIND_TASK_URL, taskId);

        ResponseEntity<Task> resultFind = restTemplate.exchange(uri, GET, requestGET, Task.class);
        assertThat(resultFind.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
    }

    
    @Test
    public void findTask_InvalidIdInput_ShouldReturnNotFound() throws Exception {

        HttpEntity requestGET = getDefaultHeader(this.jwtTokenTechnician);

        String taskId = "0";

        //find task - technician
        URI uri = buildURI(port, FIND_TASK_URL, taskId);

        ResponseEntity<Task> resultFind = restTemplate.exchange(uri, GET, requestGET, Task.class);
        assertThat(resultFind.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void addTask_ValidInput_ShouldReturnNoError() throws Exception {

        Task task = TaskDataBuilder.getTask();

        HttpEntity request = getPostDefaultHeader(this.jwtTokenTechnician, task);

        ResponseEntity<Task> result = restTemplate.postForEntity(buildURI(port, ADD_TASK_URL), request, Task.class);
        Task taskActual = result.getBody();

        assertThat(result.getStatusCode(), equalTo(HttpStatus.CREATED));

        assertThat(taskActual.getId(), notNullValue());
        assertThat(taskActual.getName(), equalTo(task.getName()));
        assertThat(taskActual.getDescription(), equalTo(task.getDescription()));
    }

    @Test
    public void addTask_ManagerRoleInput_ShouldReturnUnauthorized() throws Exception {

        Task task = TaskDataBuilder.getTask();

        HttpEntity request = getPostDefaultHeader(this.jwtTokenManager, task);

        ResponseEntity<Task> result = restTemplate.postForEntity(buildURI(port, ADD_TASK_URL), request, Task.class);
        Task taskActual = result.getBody();

        assertThat(result.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void authorizedAccess_BothRoles_ShouldReturnNoError() throws Exception {

        HttpEntity<String> requestT = getDefaultHeader(this.jwtTokenTechnician);
        ResponseEntity<String> resultT = restTemplate.exchange(buildURI(port, LIST_TASK_URL), GET, requestT, String.class);
        assertThat(resultT.getStatusCode(), equalTo(HttpStatus.OK));
        
        HttpEntity<String> requestM = getDefaultHeader(this.jwtTokenManager);
        ResponseEntity<String> resultM = restTemplate.exchange(buildURI(port, LISTALL_TASK_URL), GET, requestM, String.class);
        assertThat(resultM.getStatusCode(), equalTo(HttpStatus.OK));
    }
    
    @Test
    public void listTask_ManagerRoleInput_ShouldReturnUnauthorized() throws Exception {

        HttpEntity<String> requestT = getDefaultHeader(this.jwtTokenManager);
        ResponseEntity<String> resultT = restTemplate.exchange(buildURI(port, LIST_TASK_URL), GET, requestT, String.class);
        assertThat(resultT.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
        
    }
    
    @Test
    public void listAllTasks_TechnicianRoleInput_ShouldReturnUnauthorized() throws Exception {

        HttpEntity<String> requestT = getDefaultHeader(this.jwtTokenTechnician);
        ResponseEntity<String> resultT = restTemplate.exchange(buildURI(port, LISTALL_TASK_URL), GET, requestT, String.class);
        assertThat(resultT.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
        
    }

    @Test
    public void unauthorizedAccess_EmptyInput_ShouldReturnUnauthorized() throws Exception {

        ResponseEntity<String> serverResponse = this.restTemplate.getForEntity(buildURI(port, LIST_TASK_URL), String.class);
        assertThat(serverResponse.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));

    }

}
