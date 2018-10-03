package kevinlamcs.android.com.yummlydummy.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Flavors {

    @SerializedName("salty")
    @Expose
    private Float salty;
    @SerializedName("sour")
    @Expose
    private Float sour;
    @SerializedName("sweet")
    @Expose
    private Float sweet;
    @SerializedName("bitter")
    @Expose
    private Float bitter;
    @SerializedName("meaty")
    @Expose
    private Float meaty;
    @SerializedName("piquant")
    @Expose
    private Float piquant;

    public Float getSalty() {
        return salty;
    }

    public void setSalty(Float salty) {
        this.salty = salty;
    }

    public Float getSour() {
        return sour;
    }

    public void setSour(Float sour) {
        this.sour = sour;
    }

    public Float getSweet() {
        return sweet;
    }

    public void setSweet(Float sweet) {
        this.sweet = sweet;
    }

    public Float getBitter() {
        return bitter;
    }

    public void setBitter(Float bitter) {
        this.bitter = bitter;
    }

    public Float getMeaty() {
        return meaty;
    }

    public void setMeaty(Float meaty) {
        this.meaty = meaty;
    }

    public Float getPiquant() {
        return piquant;
    }

    public void setPiquant(Float piquant) {
        this.piquant = piquant;
    }

}
