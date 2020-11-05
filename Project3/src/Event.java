final class Event
{
   public long time;
   public Entity entity;
   public Action action;

   public Event(Action action, long time, Entity entity)
   {
      this.action = action;
      this.time = time;
      this.entity = entity;
   }
}
