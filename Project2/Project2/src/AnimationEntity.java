import processing.core.PImage;

public interface AnimationEntity {

    void executeAnimation(EventScheduler scheduler, Animation animation);
    int getAnimationPeriod();
    void nextImage();

}
