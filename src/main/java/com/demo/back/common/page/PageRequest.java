package com.demo.back.common.page;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    

    //sql语句中要用的参数
    private Integer size;
    //sql语句中要用的参数
    private Integer startNum;

    //当前页,需要前端传参
    private Integer current = 1;
    //每页个数,需要前端传参
    private Integer offset = 10;
    public Integer getSize() {
        return getStartNum()+offset;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getStartNum() {
        if(current<=0){
           this.current = 1;
        }
        return (this.current - 1) * offset;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
