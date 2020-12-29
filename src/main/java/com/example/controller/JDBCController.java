package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class JDBCController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    //查询
@GetMapping("/user")
    public List<Map<String,Object>> userList() {
    String sql = "select * from identity";
    List<Map<String, Object>> list_maps = jdbcTemplate.queryForList(sql);

    return list_maps;
}
//增加
@GetMapping("/addUser")
     public String addUser(){
     String sql = "insert into logistics.identity(roleid,role,roleflag)values (4,'sssss','123456')";
      jdbcTemplate.update(sql);
      return "addupdate-ok";
    }
//改
    @GetMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id")int id) {
        String sql = "update logistics.identity set role=? ,roleflag=? where roleid="+id ;
        Object[] objects = new Object[2];
        objects[0]="11111";
        objects[1]="112222111";
        jdbcTemplate.update(sql,objects);
        return "update-ok";
    }
    //删除=
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id")int id){
        String sql = "delete from logistics.identity where roleid=?";
        jdbcTemplate.update(sql,id);
        return "deleteupdate-ok";
    }



}
