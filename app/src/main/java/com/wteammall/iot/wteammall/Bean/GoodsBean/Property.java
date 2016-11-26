package com.wteammall.iot.wteammall.Bean.GoodsBean;

/**
 * Created by I0T on 2016/11/24.
 */
public class Property {

    private String id;
    //属性名称
    private String name;
    //属性值
    private String value;
    private Goods goods;
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
    public String getValue()
    {
        return value;
    }
    public void setValue(String value)
    {
        this.value = value;
    }
    public Goods getGoods()
    {
        return goods;
    }
    public void setGoods(Goods goods)
    {
        this.goods = goods;
    }

}
