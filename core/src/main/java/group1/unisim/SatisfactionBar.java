package group1.unisim;


public class SatisfactionBar {

    private int satisfactionScore;
    
    public SatisfactionBar(){
        resetScore();
    }

    private void resetScore(){
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
