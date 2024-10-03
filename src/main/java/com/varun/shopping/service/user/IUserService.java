package com.varun.shopping.service.user;

import com.varun.shopping.model.User;
import com.varun.shopping.request.CreateUserRequest;
import com.varun.shopping.request.UpdateUserRequest;

public interface IUserService {

    User getUserById(Integer userId);

    User createUser(CreateUserRequest request);

    User updateUser(UpdateUserRequest request, Integer userId);

    void deleteUserById(Integer userId);


}
