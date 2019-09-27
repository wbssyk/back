//package com.demo.back.shiro.req;
//
//import java.io.Serializable;
//
//
//public class CustomUser implements Serializable {
//    //实现序列化，不然redis找不到缓存位置
//    private static final long serialVersionUID = 1L;
//
//    private Integer id;
//    private String username;
//    private String password;
//
//    private Integer roleId;
//    private String doctorids;
//    private String roledesc;
//
//    private Integer userenable;
//
//    public Integer getUserenable() {
//        return userenable;
//    }
//
//    public void setUserenable(Integer userenable) {
//        this.userenable = userenable;
//    }
//
//    public String getRoledesc() {
//        return roledesc;
//    }
//
//    public void setRoledesc(String roledesc) {
//        this.roledesc = roledesc;
//    }
//
//    public String getDoctorids() {
//        return doctorids;
//    }
//
//    public void setDoctorids(String doctorids) {
//        this.doctorids = doctorids;
//    }
//
//    public Integer getRoleId() {
//        return roleId;
//    }
//
//    public void setRoleId(Integer roleId) {
//        this.roleId = roleId;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//}
