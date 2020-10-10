/*
Action: ideally what our various entities might do in our virtual world
 */
//attributes private, methods public

final class Action {
   private ActionKind kind;
   private Entity entity;
   public WorldModel world;
   public ImageStore imageStore;
   public int repeatCount;

   public Action(ActionKind kind, Entity entity, WorldModel world,
                 ImageStore imageStore, int repeatCount) {
      this.kind = kind;
      this.entity = entity;
      this.world = world;
      this.imageStore = imageStore;
      this.repeatCount = repeatCount;
   }

   public void executeAction(EventScheduler scheduler) //only method in action class
   {
      switch (kind) {
         case ACTIVITY:
            entity.executeActivityAction(this, scheduler); //CHECK scheduler, this
            break;

         case ANIMATION:
            entity.executeAnimationAction(this, scheduler); //CHECK scheduler, this
            break;
      }
   }

}
