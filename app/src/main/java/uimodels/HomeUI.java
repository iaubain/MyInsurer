package uimodels;

/**
 * Created by Hp on 11/7/2016.
 */
public class HomeUI {
    private String name;
    private int numOfHits;
    private int serviceCode;
    private int thumbnail;

    public HomeUI(String name, int numOfHits, int serviceCode, int thumbnail) {
        this.setName(name);
        this.setNumOfHits(numOfHits);
        this.setServiceCode(serviceCode);
        this.setThumbnail(thumbnail);
    }

    public HomeUI() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfHits() {
        return numOfHits;
    }

    public void setNumOfHits(int numOfHits) {
        this.numOfHits = numOfHits;
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
