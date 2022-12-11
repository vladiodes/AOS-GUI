package Backend;

public class AOSFacade {
    private boolean isServerUp;
    public AOSFacade(){
        this.isServerUp = false;
    }
    public void activateAOServer(){
        System.out.println("Server is up");
        isServerUp = true;
    }

    public boolean isServerUp(){
        return isServerUp;
    }
    public void shutDownAOServer(){
        System.out.println("Server is down");
        isServerUp = false;
    }
}
