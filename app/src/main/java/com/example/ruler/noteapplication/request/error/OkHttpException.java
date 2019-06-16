package com.example.ruler.noteapplication.request.error;

public class OkHttpException extends Exception{
    private static final long serialVersionUID=1L;

    private int ecode;

    private Object emsg;
    public OkHttpException(int ecode,Object emsg){
        this.ecode=ecode;
        this.emsg=emsg;
    }

    public OkHttpException(){

    }


    public int getEcode() {
        return ecode;
    }

    public void setEcode(int ecode) {
        this.ecode = ecode;
    }

    public Object getEmsg() {
        return emsg;
    }

    public void setEmsg(Object emsg) {
        this.emsg = emsg;
    }
}
