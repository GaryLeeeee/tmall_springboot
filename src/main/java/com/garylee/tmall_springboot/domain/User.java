package com.garylee.tmall_springboot.domain;

public class User {
    private Integer id;

    private String name;

    private String password;

    private String salt;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    //非数据库字段

    private String anonymousName;

    public void setAnonymousName(String anonymousName) {
        this.anonymousName = anonymousName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
    //用于产品页显示评价者的时候匿名显示
    //abc->a*c
    //ab ->a*
    //a  ->a
    public String getAnonymousName(){
        if(null==name)
            return null;
        if(name.length()<=1)
            return "*";

        if(name.length()==2)
            return name.substring(0,1) +"*";

        char[] cs =name.toCharArray();
        for (int i = 1; i < cs.length-1; i++) {
            cs[i]='*';
        }
        return new String(cs);
    }

    public static void main(String[] args) {
        User user = new User();
        user.setName("adf");
        System.out.println(user.getAnonymousName());
    }
}