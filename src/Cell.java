import java.util.EnumMap;

class Cell{
    private int value;
    private EnumMap<String,Cell> neighbor;
    private boolean marqued;

    Cell(int v){
        this.value=v;
        this.marqued=false;
    }
}