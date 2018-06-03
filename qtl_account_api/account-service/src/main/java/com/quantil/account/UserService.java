package com.quantil.account;

import com.zoe.snow.auth.AccountViewModel;
import com.zoe.snow.crud.Result;

/**
 * UserService for user itself
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/10/11
 */
public interface UserService {
    /**
     *
     *
     * @return
     */
    Result login(String ip, AccountViewModel accountViewModel);

    Result logout(String token);

    Result<Token> verify(String token);

    // change itself
    Result updateUser(UpdateUserViewModel userViewModel, String token);

    Result getUser(String token);

    Result changePassword(PasswordViewModel passwordViewModel, String token);
}
