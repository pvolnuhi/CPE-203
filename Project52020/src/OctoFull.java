import java.util.List;
import java.util.Optional;

import processing.core.PImage;

final class OctoFull extends Moveable {

    private int resourceLimit;
    private int resourceCount;

    public OctoFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod){
        super(position,images,id,actionPeriod,animationPeriod);
        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;
    }


//    private Point nextPositionOcto(WorldModel world, Point destPos)
//    {
//        int horiz = Integer.signum(destPos.x - getPosition().x);
//        Point newPos = new Point(getPosition().x + horiz, getPosition().y);
//
//        if (horiz == 0 || newPos.isOccupied(world))
//        {
//            int vert = Integer.signum(destPos.y - getPosition().y);
//            newPos = new Point(getPosition().x,
//                    getPosition().y + vert);
//
//            if (vert == 0 || newPos.isOccupied(world))
//            {
//                newPos = getPosition();
//            }
//        }
//
//        return newPos;
//    }

    private boolean moveToFull( WorldModel world,Entitys target, EventScheduler scheduler){
        if (this.position.adjacent(target.getposition())){
            return true;
        }else{
            Point nextPos = this.nextPosition( world, target.getposition());
            if (!(this.position.equals(nextPos))){
                Optional<Entitys> occupant = world.getOccupant( nextPos);
                if (occupant.isPresent()){
                    scheduler.unscheduleAllEvents( occupant.get());
                }
                world.moveEntity( this, nextPos);
            }
            return false;
        }
    }

    private void transformFull( WorldModel world, EventScheduler scheduler, ImageStore imageStore){
        OctoNotFull octo = (OctoNotFull)world.createOctoNotFull(this.id, this.resourceLimit, this.position, this.actionPeriod, this.animationPeriod, this.images);
        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);
        world.addEntity( octo);
        octo.scheduleActions( scheduler, world, imageStore);
    }


//    public void executeAnimation(EventScheduler scheduler, Animation animation)
//    {
//        nextImage();
//
//        if (animation.repeatCount != 1)
//        {
//            scheduler.scheduleEvent(this, this.createAnimationAction(Math.max(animation.repeatCount - 1, 0)), getAnimationPeriod());
//        }
//    }

    public void executeActivity( WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        Optional<Entitys> fullTarget = (world.findNearest(this.position,Atlantis.class));

        if (fullTarget.isPresent() && this.moveToFull( world, fullTarget.get(), scheduler)){
            //at atlantis trigger animation
            ((Atlantis)fullTarget.get()).scheduleActions(scheduler, world, imageStore);
            //transform to unfull
            this.transformFull( world, scheduler, imageStore);
        }else{
            scheduler.scheduleEvent( (Entitys)this,
                    (Action)this.createActivityAction( world, imageStore),
                    this.actionPeriod);
        }
    }

}
