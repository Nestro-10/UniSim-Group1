package group1.unisim;
public abstract class Event {
    public int duration;


    public abstract void Start();

    public abstract void Update();

    public abstract void End();

}
