/*
Action: ideally what our various entities might do in our virtual world
 */
//attributes private, methods public

interface Action {
   void execute(EventScheduler schedule);
}
