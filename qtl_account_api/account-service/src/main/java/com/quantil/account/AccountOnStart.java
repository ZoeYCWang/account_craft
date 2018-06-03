package com.quantil.account;

import com.quantil.account.role.RoleModel;

/**
 * AccountOnStart
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/10/2
 */
@FunctionalInterface
public interface AccountOnStart {
    RoleModel getSupperAdmin();
}
