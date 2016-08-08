package com;

/**
 * Created by Administrator on 2016/5/12.
 */
public class Generic<T1, T2, H> implements geneInterface<T1>
{
    private T1 mT1;
    private T2 mT2;
    private H mH;

    public Generic(T1 t1, T2 t2, H h)
    {
        mT1 = t1;
        mT2 = t2;
        mH = h;
    }

    public T1 getT1()
    {
        return mT1;
    }

    public void setT1(T1 t1)
    {
        mT1 = t1;
    }

    public T2 getT2()
    {
        return mT2;
    }

    public void setT2(T2 t2)
    {
        mT2 = t2;
    }

    public H getH()
    {
        return mH;
    }

    public void setH(H h)
    {
        mH = h;
    }

    @Override
    public T1 getName()
    {
        return mT1;
    }
}
