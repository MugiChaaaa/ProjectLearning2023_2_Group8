import java.io.Serializable;
class Account implements Serializable {
    private String ID;
    private String PW;

    int []Genre=new int[9];
    Account(){
        //ジャンル分け
        Genre[0]=0;
        Genre[1]=0;
        Genre[2]=0;
        Genre[3]=0;
        Genre[4]=0;
        Genre[5]=0;
        Genre[6]=0;
        Genre[7]=0;
        Genre[8]=0;
    }
    public void setID(String ID) {
        this.ID=ID;
    }
    public void setPW(String PW) {
        this.PW=PW;
    }
    public String getID() {
        return ID;
    }
    public String getPW() {
        return PW;
    }
    public void record(int result) {
        if (Genre[result]!=-1) {
            Genre[result]++;
        }
    }
    public void setGenre(int[] Genre) {
        this.Genre=Genre;
    }
    public int[] getGenre() {
        return Genre;
    }
}