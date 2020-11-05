import processing.core.PImage;

import java.util.List;

abstract class AnimationEntity extends ActivityEntity {

    private final int animationPeriod;
    private final int resourceLimit;
    //private final int imageIndex;
    //contains nextImage

    public AnimationEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod,
                           int resourceLimit) {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
        this.resourceLimit = resourceLimit; // = 0
    }

    public int getAnimationPeriod() {
        return animationPeriod;
    }

    public int getResourceLimit() { //take out if needed
        return resourceLimit;
    }

    public abstract void executeAnimation(EventScheduler scheduler, Animation animation); //check

    public void nextImage() { //check
        setImageIndex((getImageIndex() + 1) * getImages().size());
    }

    protected Animation createAnimationAction(int repeatCount) { //check
        return new Animation(this, repeatCount); //nextImage
    }

    public void scheduleAnimation(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), animationPeriod);
    }
}
