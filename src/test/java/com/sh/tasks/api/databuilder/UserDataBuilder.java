package com.sh.tasks.api.databuilder;

import com.sh.tasks.api.model.datatransferobject.UserDto;

/**
 * <h1>UserDataBuilder</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-24
 */
public class UserDataBuilder {

    public static UserDto getDefaultUserManager(){
    	
        return new UserDto("manager","manager@sh.com", "manager2021");
    }
    
    public static UserDto getDefaultUserTechnician(){

        return new UserDto("technician","technician@sh.com", "tech2021");
    }
    
    public static UserDto getDefaultUserAnotherTechnician(){

        return new UserDto("john","johntechnician@sh.com", "john2021");
    }

}
