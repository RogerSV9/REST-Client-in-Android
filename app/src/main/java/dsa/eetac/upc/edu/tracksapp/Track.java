package dsa.eetac.upc.edu.tracksapp;

public class Track {

    int id;
    String title;
    String singer;

    public Track(int id, String title, String singer){
        this.id = id;
        this.title = title;
        this.singer = singer;
    }

    @Override
    public String toString() {
        return(title);
    }
}
