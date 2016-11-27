package com.wteammall.iot.wteammall.Bean.GoodsBean;

/**
 * Created by I0T on 2016/11/24.
 */
public class Size {

    private String id;
    private String name;
    //顺序
    private int idx;

    public int getIdx()
    {
        return idx;
    }
    public void setIdx(int idx)
    {
        this.idx = idx;
    }
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

}
