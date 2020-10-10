import java.util.List;
import java.util.*;
import java.util.Optional;
import java.lang.Math;
import processing.core.PImage;

/*
Entity ideally would includes functions for how all the entities in our virtual world might act...
 */


final class Entity {
   public EntityKind kind;
   private String id;
   public Point position;
   private List<PImage> images;
   private int imageIndex;
   private final int resourceLimit;
   private int resourceCount;
   private final int actionPeriod;
   private final int animationPeriod;

   private static final String FISH_ID_PREFIX = "fish -- ";
   private static final int FISH_CORRUPT_MIN = 20000;
   private static final int FISH_CORRUPT_MAX = 30000;


   private static final String CRAB_KEY = "crab";
   private static final String CRAB_ID_SUFFIX = " -- crab";
   private static final int CRAB_PERIOD_SCALE = 4;
   private static final int CRAB_ANIMATION_MIN = 50;
   private static final int CRAB_ANIMATION_MAX = 150;

   private static final String ATLANTIS_KEY = "atlantis"; //move atlantis somewhere else?
   private static final int ATLANTIS_NUM_PROPERTIES = 4;
   private static final int ATLANTIS_ID = 1;
   private static final int ATLANTIS_COL = 2;
   private static final int ATLANTIS_ROW = 3;
   private static final int ATLANTIS_ANIMATION_PERIOD = 70;
   private static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;

   private static final String QUAKE_ID = "quake";
   private static final int QUAKE_ACTION_PERIOD = 1100;
   private static final int QUAKE_ANIMATION_PERIOD = 100;
   private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;
   private static final String QUAKE_KEY = "quake";


   public Entity(EntityKind kind, String id, Point position,
                 List<PImage> images, int resourceLimit, int resourceCount,
                 int actionPeriod, int animationPeriod) {
      this.kind = kind;
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
      this.resourceLimit = resourceLimit;
      this.resourceCount = resourceCount;
      this.actionPeriod = actionPeriod;
      this.animationPeriod = animationPeriod;

   }

   public static Entity createAtlantis(String id, Point position,
                                       List<PImage> images) {
      return new Entity(EntityKind.ATLANTIS, id, position, images,
              0, 0, 0, 0);
   }

   public static Entity createObstacle(String id, Point position,
                                       List<PImage> images) {
      return new Entity(EntityKind.OBSTACLE, id, position, images,
              0, 0, 0, 0);
   }

   public static Entity createCrab(String id, Point position,
                                   int actionPeriod, int animationPeriod, List<PImage> images) {
      return new Entity(EntityKind.CRAB, id, position, images,
              0, 0, actionPeriod, animationPeriod);
   }

   public static Entity createQuake(Point position, List<PImage> images) {
      return new Entity(EntityKind.QUAKE, QUAKE_ID, position, images,
              0, 0, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
   }

   public static Entity createSgrass(String id, Point position, int actionPeriod,
                                     List<PImage> images) {
      return new Entity(EntityKind.SGRASS, id, position, images, 0, 0,
              actionPeriod, 0);
   }
   public static Entity createOctoFull(String id, int resourceLimit,
                                       Point position, int actionPeriod, int animationPeriod,
                                       List<PImage> images) {
      return new Entity(EntityKind.OCTO_FULL, id, position, images,
              resourceLimit, resourceLimit, actionPeriod, animationPeriod);
   }

   public static Entity createOctoNotFull(String id, int resourceLimit,
                                          Point position, int actionPeriod, int animationPeriod,
                                          List<PImage> images) {
      return new Entity(EntityKind.OCTO_NOT_FULL, id, position, images,
              resourceLimit, 0, actionPeriod, animationPeriod);
   }
   public static Entity createFish(String id, Point position, int actionPeriod,
                                   List<PImage> images) {
      return new Entity(EntityKind.FISH, id, position, images, 0, 0,
              actionPeriod, 0);
   }


   public void scheduleActions(EventScheduler scheduler,
      WorldModel world, ImageStore imageStore)
   {
      switch (this.kind)
      {
      case OCTO_FULL:
         scheduler.scheduleEvent(this,
            this.createActivityAction(world, imageStore),
            actionPeriod);
         scheduler.scheduleEvent(this, this.createAnimationAction(0),
            getAnimationPeriod());
         break;

      case OCTO_NOT_FULL:
         scheduler.scheduleEvent(this,
            this.createActivityAction(world, imageStore),
            actionPeriod);
         scheduler.scheduleEvent(this, this.createAnimationAction(0), getAnimationPeriod());
         break;

      case FISH:
         scheduler.scheduleEvent(this,
                 this.createActivityAction(world, imageStore),
            actionPeriod);
         break;

      case CRAB:
         scheduler.scheduleEvent(this,
            this.createActivityAction(world, imageStore),
            actionPeriod);
         scheduler.scheduleEvent(this, this.createAnimationAction(0), getAnimationPeriod());
         break;

      case QUAKE:
         scheduler.scheduleEvent(this,
            this.createActivityAction(world, imageStore),
            actionPeriod);
         scheduler.scheduleEvent(this, this.createAnimationAction(QUAKE_ANIMATION_REPEAT_COUNT),
            getAnimationPeriod());
         break;

      case SGRASS:
         scheduler.scheduleEvent(this,
            this.createActivityAction(world, imageStore),
            actionPeriod);
         break;

      case ATLANTIS:
         scheduler.scheduleEvent(this, this.createAnimationAction(ATLANTIS_ANIMATION_REPEAT_COUNT),
                    getAnimationPeriod());
            break;

      default:
      }
   }

   public int getAnimationPeriod()
   {
      switch (this.kind)
      {
         case OCTO_FULL:
         case OCTO_NOT_FULL:
         case CRAB:
         case QUAKE:
         case ATLANTIS:
            return animationPeriod;
         default:
            throw new UnsupportedOperationException(
                    String.format("getAnimationPeriod not supported for %s", kind));
      }
   }
   public void nextImage()
   {
      imageIndex = (imageIndex + 1) % images.size();
   }

   public void executeAnimationAction(Action action, EventScheduler scheduler) //creatAc
   {
      nextImage();

      if (action.repeatCount != 1)
      {
         scheduler.scheduleEvent(this, this.createAnimationAction(Math.max(action.repeatCount - 1, 0)),
                 getAnimationPeriod());
      }
   }
   public void executeActivityAction(Action action, EventScheduler scheduler)
   {
      switch (this.kind)
      {
         case OCTO_FULL:
            this.executeOctoFullActivity(action.world,
                    action.imageStore, scheduler);
            break;

         case OCTO_NOT_FULL:
            this.executeOctoNotFullActivity(action.world,
                    action.imageStore, scheduler);
            break;

         case FISH:
            this.executeFishActivity(action.world, action.imageStore,
                    scheduler);
            break;

         case CRAB:
            this.executeCrabActivity(action.world,
                    action.imageStore, scheduler);
            break;

         case QUAKE:
            this.executeQuakeActivity(action.world, action.imageStore,
                    scheduler);
            break;

         case SGRASS:
            this.executeSgrassActivity(action.world, action.imageStore,
                    scheduler);
            break;

         case ATLANTIS:
            this.executeAtlantisActivity(action.world, action.imageStore,
                    scheduler);
            break;

         default:
            throw new UnsupportedOperationException(
                    String.format("executeActivityAction not supported for %s", kind));
      }
   }
   private Action createActivityAction(WorldModel world, ImageStore imageStore)
   {
      return new Action(ActionKind.ACTIVITY, this, world, imageStore, 0);
   }
   private Action createAnimationAction(int repeatCount)
   {
      return new Action(ActionKind.ANIMATION, this, null, null, repeatCount);
   }
   private Point nextPositionCrab(WorldModel world, Point destPos)
   {
      int horiz = Integer.signum(destPos.x - position.x);
      Point newPos = new Point(position.x + horiz, position.y);

      Optional<Entity> occupant = world.getOccupant(newPos); //CHECK

      if (horiz == 0 ||
              (occupant.isPresent() && !(occupant.get().kind == EntityKind.FISH)))
      {
         int vert = Integer.signum(destPos.y - position.y);
         newPos = new Point(position.x, position.y + vert);
         occupant = world.getOccupant(newPos);

         if (vert == 0 ||
                 (occupant.isPresent() && !(occupant.get().kind == EntityKind.FISH)))
         {
            newPos = position;
         }
      }

      return newPos;
   }
   private Point nextPositionOcto(WorldModel world, Point destPos)
   {
      int horiz = Integer.signum(destPos.x - position.x);
      Point newPos = new Point(position.x + horiz,
              position.y);

      if (horiz == 0 || newPos.isOccupied(world))
      {
         int vert = Integer.signum(destPos.y - position.y);
         newPos = new Point(position.x,
                 position.y + vert);

         if (vert == 0 || newPos.isOccupied(world))
         {
            newPos = position;
         }
      }

      return newPos;
   }
   private void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
   {
      Entity octo = createOctoNotFull(id, resourceLimit, position, actionPeriod, animationPeriod, images);

      world.removeEntity(this); //'this' refers to current object 'entity'
      scheduler.unscheduleAllEvents(this);

      world.addEntity(octo);
      octo.scheduleActions(scheduler, world, imageStore);
   }
   private boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
   {
      if (resourceCount >= resourceLimit)
      {
         Entity octo = createOctoFull(id, resourceLimit, position, actionPeriod, animationPeriod, images);

         world.removeEntity(this);
         scheduler.unscheduleAllEvents(this);

         world.addEntity(octo);
         octo.scheduleActions(scheduler, world, imageStore);

         return true;
      }

      return false;
   }
   private void executeSgrassActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) //CHECK
   {
      Optional<Point> openPt = position.findOpenAround(world);

      if (openPt.isPresent())
      {
         Entity fish = createFish(FISH_ID_PREFIX + id,
                 openPt.get(), FISH_CORRUPT_MIN + Functions.rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN),
                 imageStore.getImageList(WorldModel.FISH_KEY));
         world.addEntity(fish);
         fish.scheduleActions(scheduler, world, imageStore);
      }

      scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), actionPeriod);
   }
   public void executeQuakeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) //private?
   {
      scheduler.unscheduleAllEvents(this);
      world.removeEntity(this);
   }

   public void executeAtlantisActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
   {
      scheduler.unscheduleAllEvents(this);
      world.removeEntity(this);
   }

   private void executeCrabActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> crabTarget = position.findNearest(world, EntityKind.SGRASS);
      long nextPeriod = actionPeriod;

      if (crabTarget.isPresent())
      {
         Point tgtPos = crabTarget.get().position;

         if (this.moveToCrab(world, crabTarget.get(), scheduler))
         {
            Entity quake = createQuake(tgtPos, imageStore.getImageList(QUAKE_KEY));

            world.addEntity(quake);
            nextPeriod += actionPeriod;
            quake.scheduleActions(scheduler, world, imageStore);
         }
      }

      scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore),
              nextPeriod);
   }
   private boolean moveToCrab(WorldModel world, Entity target, EventScheduler scheduler)
   {
      if (position.adjacent(target.position))
      {
         world.removeEntity(target);
         scheduler.unscheduleAllEvents(target);
         return true;
      }
      else
      {
         Point nextPos = nextPositionCrab(world, target.position);

         if (!position.equals(nextPos))
         {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(this, nextPos);
         }
         return false;
      }
   }

   private void executeFishActivity( WorldModel world, ImageStore imageStore, EventScheduler scheduler)
   {
      Point pos = position;  // store current position before removing

      world.removeEntity(this);
      scheduler.unscheduleAllEvents(this);

      Entity crab = createCrab(id + CRAB_ID_SUFFIX,
              pos, actionPeriod / CRAB_PERIOD_SCALE,
              CRAB_ANIMATION_MIN +
                      Functions.rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN),
              imageStore.getImageList(CRAB_KEY));

      world.addEntity(crab);
      crab.scheduleActions(scheduler, world, imageStore);
   }
   private void executeOctoNotFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> notFullTarget = position.findNearest(world, EntityKind.FISH);

      if (!notFullTarget.isPresent() ||
              !this.moveToNotFull(world, notFullTarget.get(), scheduler) ||
              !this.transformNotFull(world, scheduler, imageStore))
      {
         scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
      }
   }
   private void executeOctoFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
   {
      Optional<Entity> fullTarget = position.findNearest(world, EntityKind.ATLANTIS);

      if (fullTarget.isPresent() && this.moveToFull(world, fullTarget.get(), scheduler))
      {
         //this.transformFull(world, scheduler, imageStore); //transform to unfull (only this line)
         //at atlantis trigger animation
         //scheduleActions(fullTarget.get(), scheduler, world, imageStore);



         //at atlantis trigger animation!!!!!!!!!!
         //VirtualWorld.scheduleActions(world, scheduler, imageStore);

         //transform to unfull
         this.transformFull( world, scheduler, imageStore);
      }
      else
      {
         scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
      }
   }
   public PImage getCurrentImage()
   {
      return images.get(imageIndex);
   }

   private boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler)
   {
      if (position.adjacent(target.position))
      {
         return true;
      }
      else
      {
         Point nextPos = this.nextPositionOcto(world, target.position); //CHECK

         if (!position.equals(nextPos))
         {
            Optional<Entity> occupant = world.getOccupant(nextPos); //do this for get methods
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(this, nextPos);
         }
         return false;
      }
   }
   private boolean moveToNotFull(WorldModel world, Entity target, EventScheduler scheduler)
   {
      if (position.adjacent(target.position))
      {
         resourceCount += 1;
         world.removeEntity(target);
         scheduler.unscheduleAllEvents(target);

         return true;
      }
      else
      {
         Point nextPos = this.nextPositionOcto(world, target.position);

         if (!position.equals(nextPos))
         {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(this, nextPos);
         }
         return false;
      }
   }



}

