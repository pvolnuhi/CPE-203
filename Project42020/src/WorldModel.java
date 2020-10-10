import processing.core.PImage;

import java.util.*;

/*
WorldModel ideally keeps track of the actual size of our grid world and what is in that world
in terms of entities and background elements
 */

final class WorldModel {
   public int numRows;
   public int numCols;
   private final Background background[][];
   private final Entity occupancy[][];
   public final Set<Entity> entities;
//   public Set<ActivityEntity> activityEntities;
//   public Set<AnimationEntity> animationEntities;

   private static final int PROPERTY_KEY = 0;

   private static final String OBSTACLE_KEY = "obstacle";
   private static final int OBSTACLE_NUM_PROPERTIES = 4;
   private static final int OBSTACLE_ID = 1;
   private static final int OBSTACLE_COL = 2;
   private static final int OBSTACLE_ROW = 3;

   private static final String OCTO_KEY = "octo";
   private static final int OCTO_NUM_PROPERTIES = 7;
   private static final int OCTO_ID = 1;
   private static final int OCTO_COL = 2;
   private static final int OCTO_ROW = 3;
   private static final int OCTO_LIMIT = 4;
   private static final int OCTO_ACTION_PERIOD = 5;
   private static final int OCTO_ANIMATION_PERIOD = 6;

   public static final String FISH_KEY = "fish";
   private static final int FISH_NUM_PROPERTIES = 5;
   private static final int FISH_ID = 1;
   private static final int FISH_COL = 2;
   private static final int FISH_ROW = 3;
   private static final int FISH_ACTION_PERIOD = 4;

   public static final String QUAKE_ID = "quake";
   public static final int QUAKE_ACTION_PERIOD = 1100;
   public static final int QUAKE_ANIMATION_PERIOD = 100;

   private static final String ATLANTIS_KEY = "atlantis";
   private static final int ATLANTIS_NUM_PROPERTIES = 4;
   private static final int ATLANTIS_ID = 1;
   private static final int ATLANTIS_COL = 2;
   private static final int ATLANTIS_ROW = 3;

   public static final int FISH_REACH = 1;

   private static final String SGRASS_KEY = "seaGrass";
   private static final int SGRASS_NUM_PROPERTIES = 5;
   private static final int SGRASS_ID = 1;
   private static final int SGRASS_COL = 2;
   private static final int SGRASS_ROW = 3;
   private static final int SGRASS_ACTION_PERIOD = 4;

   private static final String BGND_KEY = "background";
   private static final int BGND_NUM_PROPERTIES = 4;
   private static final int BGND_ID = 1;
   private static final int BGND_COL = 2;
   private static final int BGND_ROW = 3;

   public Set<Entity> getentities(){ //Entitys
      return entities;  //not sure
   }

   private List<Point> path;


   public WorldModel(int numRows, int numCols, Background defaultBackground) {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();
//      this.activityEntities = new HashSet<>();
//      this.animationEntities = new HashSet<>();


      for (int row = 0; row < numRows; row++) {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }

   public void tryAddEntity( Entitys entity) {
      if (isOccupied(entity.getposition())) {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         //throw new IllegalArgumentException("position occupied");
      } else {
         this.addEntity(entity);
      }
   }

   public boolean neighbors(Point p1, Point p2) {
      return p1.x + 1 == p2.x && p1.y == p2.y ||
              p1.x - 1 == p2.x && p1.y == p2.y ||
              p1.x == p2.x && p1.y + 1 == p2.y ||
              p1.x == p2.x && p1.y - 1 == p2.y;
   }


   public boolean withinBounds( Point pos)
   {
      return pos.y >= 0 && pos.y < this.numRows &&
              pos.x >= 0 && pos.x < this.numCols;
   }

   public boolean isOccupied( Point pos)
   {
      return withinBounds( pos) &&
              this.getOccupancyCell( pos) != null;
   }

   /*
      Assumes that there is no entity currently occupying the
      intended destination cell.
   */
   public void addEntity( Entitys entity)
   {
      if (this.withinBounds( entity.getposition()))
      {
         this.setOccupancyCell( entity.getposition(), entity);
         this.entities.add((Entity) entity); //Check
      }
   }

   public Optional<Entitys> findNearest(Point pos, Class kind){
      List<Entitys> ofType = new LinkedList<>();
      for (Entitys entity : this.entities){
         if (entity.getClass() == kind){
            ofType.add(entity);
         }
      }
      return nearestEntity(ofType, pos);
   }

   public void moveEntity( Entitys entity, Point pos)
   {
      Point oldPos = entity.getposition();
      if (withinBounds( pos) && !pos.equals(oldPos))
      {
         this.setOccupancyCell( oldPos, null);
         this.removeEntityAt( pos);
         this.setOccupancyCell( pos, entity);
         entity.setposition(pos);
      }
   }

   public void removeEntity( Entitys entity)
   {
      this.removeEntityAt(entity.getposition());
   }

   public void removeEntityAt(Point pos)
   {
      if (this.withinBounds(pos)
              && this.getOccupancyCell(pos) != null)
      {
         Entitys entity = this.getOccupancyCell( pos);

        /* this moves the entity just outside of the grid for
           debugging purposes */
         entity.setposition(new Point(-1, -1));
         this.entities.remove(entity);
         this.setOccupancyCell( pos, null);
      }
   }

   public Optional<PImage> getBackgroundImage(
           Point pos)
   {
      if (this.withinBounds( pos))
      {
         return Optional.of(getBackgroundCell(pos).getCurrentImage());
      }
      else
      {
         return Optional.empty();
      }
   }

   public void setBackground( Point pos,
                              Background background)
   {
      if (this.withinBounds( pos))
      {
         this.setBackgroundCell(pos, background);
      }
   }

   public Optional<Entitys> getOccupant( Point pos)
   {
      if (this.isOccupied( pos))
      {
         return Optional.of(getOccupancyCell( pos));
      }
      else
      {
         return Optional.empty();
      }
   }

   public Entitys getOccupancyCell( Point pos)
   {
      return this.occupancy[pos.y][pos.x];
   }

   public void setOccupancyCell( Point pos,Entitys entity)
   {
      this.occupancy[pos.y][pos.x] = (Entity) entity;
   }

   public Background getBackgroundCell( Point pos)
   {
      return this.background[pos.y][pos.x];
   }

   public void setBackgroundCell( Point pos,
                                  Background background)
   {
      this.background[pos.y][pos.x] = background;
   }

   public Optional<Point> findOpenAround( Point pos)
   {
      for (int dy = -FISH_REACH; dy <= FISH_REACH; dy++)
      {
         for (int dx = -FISH_REACH; dx <= FISH_REACH; dx++)
         {
            Point newPt = new Point(pos.x + dx, pos.y + dy);
            if (this.withinBounds( newPt) &&
                    !this.isOccupied( newPt))
            {
               return Optional.of(newPt);
            }
         }
      }

      return Optional.empty();
   }



   public Entitys createAtlantis(String id, Point position, List<PImage> images){
      return (Entitys)(new Atlantis( id, position, images, 0, 0));
   }

   public Entitys createOctoFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images){
      return (Entitys)(new OctoFull( id, position, images, resourceLimit, resourceLimit, actionPeriod, animationPeriod));
   }

   public Entitys createOctoNotFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images){
      return (Entitys)(new OctoNotFull( id, position, images, resourceLimit, 0, actionPeriod, animationPeriod, path));
   }

   public Entitys createObstacle(String id, Point position, List<PImage> images){
      return (Entitys)(new Obstacle( id, position, images));
   }

   public Entitys createFish(String id, Point position, int actionPeriod, List<PImage> images){
      return (Entitys)(new Fish( id, position, images, actionPeriod));
   }

   public Entitys createCrab(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images){
      return (Entitys)(new Crab(id, position, images, actionPeriod, animationPeriod, path));
   }

   public Entitys createSgrass(String id, Point position, int actionPeriod, List<PImage> images){
      return (Entitys)(new Sgrass( id, position, images, actionPeriod));
   }

   public Entitys createQuake(Point position, List<PImage> images){
      return (Entitys)(new Quake( QUAKE_ID, position, images, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD));
   }

   public void load(Scanner in, ImageStore imageStore)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            if (!processLine(in.nextLine(), imageStore))
            {
               System.err.println(String.format("invalid entry on line %d",
                       lineNumber));
            }
         }
         catch (NumberFormatException e)
         {
            System.err.println(String.format("invalid entry on line %d",
                    lineNumber));
         }
         catch (IllegalArgumentException e)
         {
            System.err.println(String.format("issue on line %d: %s",
                    lineNumber, e.getMessage()));
         }
         lineNumber++;
      }
   }

   public boolean parseBackground(String [] properties,
                                  ImageStore imageStore)
   {
      if (properties.length == BGND_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                 Integer.parseInt(properties[BGND_ROW]));
         String id = properties[BGND_ID];
         this.setBackground( pt,
                 new Background(id, imageStore.getImageList(id)));
      }

      return properties.length == BGND_NUM_PROPERTIES;
   }

   public boolean parseOcto(String [] properties,
                            ImageStore imageStore)
   {
      if (properties.length == OCTO_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[OCTO_COL]),
                 Integer.parseInt(properties[OCTO_ROW]));
         Entitys entity = this.createOctoNotFull(properties[OCTO_ID],
                 Integer.parseInt(properties[OCTO_LIMIT]),
                 pt,
                 Integer.parseInt(properties[OCTO_ACTION_PERIOD]),
                 Integer.parseInt(properties[OCTO_ANIMATION_PERIOD]),
                 imageStore.getImageList(OCTO_KEY));
         this.tryAddEntity( entity);
      }

      return properties.length == OCTO_NUM_PROPERTIES;
   }

   public boolean parseObstacle(String [] properties,
                                ImageStore imageStore)
   {
      if (properties.length == OBSTACLE_NUM_PROPERTIES)
      {
         Point pt = new Point(
                 Integer.parseInt(properties[OBSTACLE_COL]),
                 Integer.parseInt(properties[OBSTACLE_ROW]));
         Entitys entity = this.createObstacle(properties[OBSTACLE_ID],
                 pt, imageStore.getImageList( OBSTACLE_KEY));
         this.tryAddEntity(entity);
      }

      return properties.length == OBSTACLE_NUM_PROPERTIES;
   }

   public boolean parseFish(String [] properties,
                            ImageStore imageStore)
   {
      if (properties.length == FISH_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[FISH_COL]),
                 Integer.parseInt(properties[FISH_ROW]));
         Entitys entity = this.createFish(properties[FISH_ID],
                 pt, Integer.parseInt(properties[FISH_ACTION_PERIOD]),
                 imageStore.getImageList( FISH_KEY));
         this.tryAddEntity(entity);
      }

      return properties.length == FISH_NUM_PROPERTIES;
   }

   public boolean parseAtlantis(String [] properties,
                                ImageStore imageStore)
   {
      if (properties.length == ATLANTIS_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[ATLANTIS_COL]),
                 Integer.parseInt(properties[ATLANTIS_ROW]));
         Entitys entity = this.createAtlantis(properties[ATLANTIS_ID],
                 pt, imageStore.getImageList( ATLANTIS_KEY));
         this.tryAddEntity(entity);
      }

      return properties.length == ATLANTIS_NUM_PROPERTIES;
   }

   public boolean parseSgrass(String [] properties,
                              ImageStore imageStore)
   {
      if (properties.length == SGRASS_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[SGRASS_COL]),
                 Integer.parseInt(properties[SGRASS_ROW]));
         Entitys entity = createSgrass(properties[SGRASS_ID],
                 pt,
                 Integer.parseInt(properties[SGRASS_ACTION_PERIOD]),
                 imageStore.getImageList( SGRASS_KEY));
         this.tryAddEntity(entity);
      }

      return properties.length == SGRASS_NUM_PROPERTIES;
   }

   public boolean processLine(String line,
                              ImageStore imageStore)
   {
      String[] properties = line.split("\\s");
      if (properties.length > 0)
      {
         switch (properties[PROPERTY_KEY])
         {
            case BGND_KEY:
               return this.parseBackground(properties, imageStore);
            case OCTO_KEY:
               return this.parseOcto(properties, imageStore);
            case OBSTACLE_KEY:
               return this.parseObstacle(properties, imageStore);
            case FISH_KEY:
               return this.parseFish(properties, imageStore);
            case ATLANTIS_KEY:
               return this.parseAtlantis(properties, imageStore);
            case SGRASS_KEY:
               return this.parseSgrass(properties, imageStore);
         }
      }

      return false;
   }
   public Optional<Entitys> nearestEntity(List<Entitys> entities,
                                          Point pos)
   {
      if (entities.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         Entitys nearest = entities.get(0);
         int nearestDistance = pos.distanceSquared(nearest.getposition());

         for (Entitys other : entities)
         {
            int otherDistance = pos.distanceSquared(other.getposition());

            if (otherDistance < nearestDistance)
            {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }

}