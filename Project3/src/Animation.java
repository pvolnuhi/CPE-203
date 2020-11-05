import processing.core.PApplet;

class Animation implements Action{

    public AnimationEntity entity;
    public int repeatCount;

    public Animation(AnimationEntity entity, int repeatCount)
    {
        this.entity = entity;
        this.repeatCount = repeatCount;
    }

    public void execute(EventScheduler scheduler)
    {
        entity.executeAnimation(scheduler, this);
    }
}
