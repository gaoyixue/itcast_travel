package gyx.travel.domain;
import java.io.Serializable;

public class ResultInfo implements Serializable {
    private boolean flag;//后端返回结果正常true,发送异常false
    private Object data;//后端返回结果数据对象
    private String errorMsg;

    public ResultInfo(){

    }
    public ResultInfo(boolean flag){
        this.flag=flag;
    }

    public ResultInfo(boolean flag,String errorMsg){
        this.flag=flag;
        this.errorMsg=errorMsg;
    }

    public ResultInfo(boolean flag,Object data,String errorMsg){
        this.flag=flag;
        this.data=data;
        this.errorMsg=errorMsg;
    }
    public boolean isFlag(){
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}

