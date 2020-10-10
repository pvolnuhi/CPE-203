import java.util.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.LinkedList;

/*
EventScheduler: ideally our way of controlling what happens in our virtual world
 */

final class EventScheduler
{
   private PriorityQueue<Event> eventQueue;
   private Map<Entity, List<Event>> pendingEvents;
   private double timeScale;

   public EventScheduler(double timeScale)
   {
      this.eventQueue = new PriorityQueue<>(new EventComparator());
      this.pendingEvents = new HashMap<>();
      this.timeScale = timeScale;
   }

   public void unscheduleAllEvents(Entitys entity)
   {
      List<Event> pending = this.pendingEvents.remove(entity);

      if (pending != null)
      {
         for (Event event : pending)
         {
            this.eventQueue.remove(event);
         }
      }
   }

   public void removePendingEvent(Event event)
   {
      List<Event> pending = this.pendingEvents.get(event.getentity());

      if (pending != null)
      {
         pending.remove(event);
      }
   }

   public void updateOnTime( long time)
   {
      while (!this.eventQueue.isEmpty() &&
              this.eventQueue.peek().gettime() < time)
      {
         Event next = this.eventQueue.poll();

         this.removePendingEvent(next);

         ((Action)(next.getaction())).executeAction(this);
      }
   }

   public void scheduleEvent(Entitys entity, Action action, long afterPeriod)
   {
      long time = System.currentTimeMillis() +
              (long)(afterPeriod * this.timeScale);
      Event event = new Event(action, time, entity);

      this.eventQueue.add(event);

      // update list of pending events for the given entity
      List<Event> pending = this.pendingEvents.getOrDefault(entity, new LinkedList<>());
      pending.add(event);
      this.pendingEvents.put((Entity) entity, pending); //CHECK
   }
}
