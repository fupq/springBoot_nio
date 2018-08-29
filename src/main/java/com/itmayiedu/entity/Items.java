package com.itmayiedu.entity;

import java.util.Date;

//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

public class Items {
    private Integer id;

   
    private String name;

    private Float price;

    private String pic;

    private Date createtime;

    private String detail;

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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }
    
    public String printItemInfo(){
    	return "产品id:" + this.id + ",产品名称：" + this.name + ",产品价格：" + this.price + ",产品描述：" + this.detail + ",产品图片：" + this.pic + ",生产日期：" + this.createtime; 
    }
}