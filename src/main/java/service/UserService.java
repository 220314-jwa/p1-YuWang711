package service;

import exception.IncorrectCredentialExcception;
import exception.UsernameAlreadyExistsException;

public interface UserService<UserType> {
	public int register(UserType userType) throws UsernameAlreadyExistsException;
	public UserType logIn(UserType userType) throws IncorrectCredentialExcception;

}
