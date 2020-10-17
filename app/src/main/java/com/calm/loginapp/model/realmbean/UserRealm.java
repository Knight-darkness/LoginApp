package com.calm.loginapp.model.realmbean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @ProjectName: LoginApp
 * @ClassName: UserRealm
 * @Description: java类作用描述
 * @Author: DELL
 * @CreateDate: 2020/10/16
 * @UpdateUser: 王祎卓
 * @Version: 1.0
 */
public class UserRealm extends RealmObject {
    @PrimaryKey
    public int userId;
    public String sessionId;
}
