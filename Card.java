public class Card {
    private String number;
    private boolean isVip;
    private int duration;
    public Card(boolean isVip){
        this.isVip = isVip;
        int tempNumber = (int) (Math.random() * 1000000);
        if(isVip){
            number = "Vip" + tempNumber; 
        } 
        else{
            number = "Regular" + tempNumber;
        }
    }
    public void setDuration(int duration){
        this.duration = duration;
    }
    public boolean getIsVip(){
        return isVip;
    }
    public void setIsVip(boolean isVip){
        this.isVip = isVip;
    }
    public String getNumber(){
        return number;
    }
    public int getDuration(){
        return duration;
    }
}
