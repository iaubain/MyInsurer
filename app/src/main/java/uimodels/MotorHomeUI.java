package uimodels;

/**
 * Created by Hp on 11/12/2016.
 */
public class MotorHomeUI {
    private int thumb;
    private String service;
    private String serviceDesc;
    private int serviceCode;

    public MotorHomeUI(int thumb, String service, String serviceDesc, int serviceCode) {
        this.setThumb(thumb);
        this.setService(service);
        this.setServiceDesc(serviceDesc);
        this.setServiceCode(serviceCode);
    }

    public MotorHomeUI() {

    }

    public int getThumb() {
        return thumb;
    }

    public void setThumb(int thumb) {
        this.thumb = thumb;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
    }
}
