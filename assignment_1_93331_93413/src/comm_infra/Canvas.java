package comm_infra;

public class Canvas {
    private boolean isStolen;
    public Canvas(int id){

        isStolen = false;
    }

    public boolean isStolen() {
        return isStolen;
    }

    public void setStolen(boolean stolen) {
        isStolen = stolen;
    }

}
