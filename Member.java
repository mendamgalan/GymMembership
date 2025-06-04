public class Member {
    private String userName;
    private Card card;
    public Member(String userName){
        this.userName = userName;
    }
    public void vipRegister(){
        card = new Card(true);
        card.setDuration(3);
    } 
    public void regularRegister(){
        card = new Card(false);
        card.setDuration(1);
    } 
    public void changePlan(){
        if(card.getIsVip()){
            card.setIsVip(false);
        }
        else{
            card.setIsVip(true);
        }
    }
    public void renew(){
        if(card.getIsVip()){
            card.setDuration(3);
        }
        else{
            card.setDuration(1);
        }
    }
    public String displayInfo(){
        return userName+"картын дугааар:"+card.getNumber();
    }
    public String displayDuration(){
        return "Дуусах Хугацаа: "+card.getDuration()+"Сар";
    }
}