package group1.unisim;


public class SatisfactionBar {

    private int satisfactionScore;
    
    public SatisfactionBar(){
        setScore();
    }

    private void setScore(){
        satisfactionScore = 50;
    }

    public int getScore(){
        return satisfactionScore;
    }

    public int updateScore(int modifier){
        //Calculate new score here, talk with team
        return satisfactionScore;
    }



    

}
