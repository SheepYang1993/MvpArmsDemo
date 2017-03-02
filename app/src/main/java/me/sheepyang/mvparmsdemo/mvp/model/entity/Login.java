package me.sheepyang.mvparmsdemo.mvp.model.entity;

/**
 * Created by SheepYang on 2017/3/2.
 */

public class Login {

    /**
     * id : 49
     * name : 一群羊
     * username : yyq
     * password : e10adc3949ba59abbe56e057f20f883e
     * status : 1
     * create_time : 2017-03-02 10:29:35
     * company_id : 25
     * store_id : 27
     * telephone : 13067109465
     * sex : 1
     * status_desc : 启用
     */

    private int id;
    private String name;
    private String username;
    private String password;
    private int status;
    private String create_time;
    private String company_id;
    private String store_id;
    private String telephone;
    private String sex;
    private String status_desc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }
}
