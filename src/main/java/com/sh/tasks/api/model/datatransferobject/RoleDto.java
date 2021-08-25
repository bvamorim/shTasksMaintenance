package com.sh.tasks.api.model.datatransferobject;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.sh.tasks.api.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>RoleDto</h1>
 *
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
@Getter
@Setter
@AllArgsConstructor
public class RoleDto {

    private @NotNull Long id;
    private @NotNull String role;
    private @NotNull List<User> users;

}