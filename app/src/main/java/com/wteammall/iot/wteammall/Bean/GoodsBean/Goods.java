package com.wteammall.iot.wteammall.Bean.GoodsBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by I0T on 2016/11/24.
 */
public class Goods {

    private String id;
    private String name;
    private BigDecimal prize;
    private BigDecimal currPrize;
    //上架时间
    private Date putawayTime;
    //运费
    private BigDecimal freight;
    private String brand;
    //发货地
    //private Address shipAddress;
    private String shipAddress;
    //商品总数量,0表示不上限制
    private int nums;
    //商品销量
    private int saledNums;
    //商品大图url
    private String limgUrl;
    //商品小图url
    private String simgUrl;
    //折扣
    private BigDecimal discount;
    //商品属性
    private Map<String,String> properties;
    //商品颜色
    private Set<Color> colors;
    //商品尺寸
    private Set<Size> sizes;





    public Set<Color> getColors()
    {
        return colors;
    }
    public void setColors(Set<Color> colors)
    {
        this.colors = colors;
    }
    public Set<Size> getSizes()
    {
        return sizes;
    }
    public void setSizes(Set<Size> sizes)
    {
        this.sizes = sizes;
    }





    public Map<String, String> getProperties()
    {
        return properties;
    }
    public void setProperties(Map<String, String> properties)
    {
        this.properties = properties;
    }





    private Category category;

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
    public BigDecimal getPrize()
    {
        return prize;
    }
    public void setPrize(BigDecimal prize)
    {
        this.prize = prize;
    }
    public BigDecimal getCurrPrize()
    {
        return currPrize;
    }
    public void setCurrPrize(BigDecimal currPrize)
    {
        this.currPrize = currPrize;
    }

    public Date getPutawayTime()
    {
        return putawayTime;
    }
    public void setPutawayTime(Date putawayTime)
    {
        this.putawayTime = putawayTime;
    }
    public BigDecimal getFreight()
    {
        return freight;
    }
    public void setFreight(BigDecimal freight)
    {
        this.freight = freight;
    }
    public String getBrand()
    {
        return brand;
    }
    public void setBrand(String brand)
    {
        this.brand = brand;
    }
    public String getShipAddress()
    {
        return shipAddress;
    }
    public void setShipAddress(String shipAddress)
    {
        this.shipAddress = shipAddress;
    }
    public int getNums()
    {
        return nums;
    }
    public void setNums(int nums)
    {
        this.nums = nums;
    }
    public int getSaledNums()
    {
        return saledNums;
    }
    public void setSaledNums(int saledNums)
    {
        this.saledNums = saledNums;
    }
    public String getLimgUrl()
    {
        return limgUrl;
    }
    public void setLimgUrl(String limgUrl)
    {
        this.limgUrl = limgUrl;
    }
    public String getSimgUrl()
    {
        return simgUrl;
    }
    public void setSimgUrl(String simgUrl)
    {
        this.simgUrl = simgUrl;
    }
    public Category getCategory()
    {
        return category;
    }
    public void setCategory(Category category)
    {
        this.category = category;
    }

    public BigDecimal getDiscount()
    {
        return discount;
    }
    public void setDiscount(BigDecimal discount)
    {
        this.discount = discount;
    }
    @Override
    public String toString()
    {
        return "Goods [id=" + id + ", name=" + name + ", prize=" + prize + ", currPrize=" + currPrize + ", putawayTime="
                + putawayTime + ", freight=" + freight + ", brand=" + brand + ", shipAddress=" + shipAddress + ", nums="
                + nums + ", saledNums=" + saledNums + ", limgUrl=" + limgUrl + ", simgUrl=" + simgUrl + ", discount="
                + discount + ", properties=" + properties + ", colors=" + colors + ", sizes=" + sizes + ", category="
                + category + "]";
    }

}
