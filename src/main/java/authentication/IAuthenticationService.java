package authentication;
public interface IAuthenticationService {
    User signUp(String username, String password, String userType);
    User logIn(String username, String password);
}