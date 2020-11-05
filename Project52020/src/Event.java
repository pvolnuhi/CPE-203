final class Event
{
   public long time;
   public Entitys entity;
   public Action action;

   public Action getaction(){
      return action;
   }
   public long gettime(){
      return time;
   }
   public Entitys getentity(){
      return entity;
   }

   public Event(Action action, long time, Entitys entity)
   {
      this.action = action;
      this.time = time;
      this.entity = entity;
   }
}

