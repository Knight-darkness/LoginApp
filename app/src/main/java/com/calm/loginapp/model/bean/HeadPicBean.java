package com.calm.loginapp.model.bean;

/**
 * @ProjectName: LoginApp
 * @ClassName: HeadPicBean
 * @Description: java类作用描述
 * @Author: DELL
 * @CreateDate: 2020/10/17
 * @UpdateUser: 王祎卓
 * @Version: 1.0
 */
public class HeadPicBean {

    /**
     * headPath : http://172.17.8.100/images/small/head_pic/2020-10-17/20201017083033.png
     * message : 上传成功
     * status : 0000
     */

    private String headPath;
    private String message;
    private String status;

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
