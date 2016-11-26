package com.wteammall.iot.wteammall.Bean.GoodsBean;

/**
 * Created by I0T on 2016/11/24.
 */
public class Category {

    private String id;
    private String name;
    private Category father;


    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Category getFather()
    {
        return father;
    }
    public void setFather(Category father)
    {
        this.father = father;
    }
    @Override
    public String toString()
    {
        return "Category [id=" + id + ", name=" + name + ", father=" + father + "]";
    }

}
