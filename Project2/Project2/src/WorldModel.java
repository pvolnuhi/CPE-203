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
   public Set<ActivityEntity> activityEntities;
   public Set<AnimationEntity> animationEntities;

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

   private static final String ATLANTIS_KEY = "atlantis";
   private static final int ATLANTIS_NUM_PROPERTIES = 4;
   private static final int ATLANTIS_ID = 1;
   private static final int ATLANTIS_COL = 2;
   private static final int ATLANTIS_ROW = 3;

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


   public WorldModel(int numRows, int numCols, Background defaultBackground) {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();
      this.activityEntities = new HashSet<>();
      this.animationEntities = new HashSet<>();


      for (int row = 0; row < numRows; row++) {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }

   public void setBackgroundCell(Point pos, Background background)
   {
      this.background[pos.y][pos.x] = background;
   }

   public Background getBackgroundCell(Point pos)
   {
      return background[pos.y][pos.x];
   }

   public void setOccupancyCell(Point pos, Entity entity)
   {
      occupancy[pos.y][pos.x] = entity; //CHECK why not this.occupancy?
   }

   public Entity getOccupancyCell(Point pos)
   {
      return occupancy[pos.y][pos.x];
   }

   public Optional<Entity> getOccupant(Point pos)
   {
      if (pos.isOccupied(this)) {
         return Optional.of(this.getOccupancyCell(pos));
      } else {
         return Optional.empty();
      }
   }

   public void setBackground(Point pos, Background background)
   {
      if (pos.withinBounds(this)) {
         this.setBackgroundCell(pos, background);
      }
   }

   public Optional<PImage> getBackgroundImage(Point pos)
   {
      if (pos.withinBounds(this)) {
         return Optional.of(this.getBackgroundCell(pos).getCurrentImage()); //CHECK
      } else {
         return Optional.empty();
      }
   }

   public void removeEntityAt(Point pos)
   {
      if (pos.withinBounds(this)
              && this.getOccupancyCell(pos) != null) {
         Entity entity = this.getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.setPosition(new Point(-1, -1));
         entities.remove(entity);
         this.setOccupancyCell(pos, null);
      }
   }

   public void removeEntity(Entity entity)
   {
      this.removeEntityAt(entity.getPosition());
   }

   public void moveEntity(Entity entity, Point pos)
   {
      Point oldPos = entity.getPosition();
      if (pos.withinBounds(this) && !pos.equals(oldPos))
      {
         this.setOccupancyCell(oldPos, null);
         this.removeEntityAt(pos);
         this.setOccupancyCell(pos, entity);
         entity.setPosition(pos);
      }
   }

   public void addEntity(Entity entity)
   {
      if (entity.getPosition().withinBounds(this))
      {
         this.setOccupancyCell(entity.getPosition(), entity);
         entities.add(entity);
      }
      if (entity instanceof ActivityEntity)
      {
         ActivityEntity ActivE = (ActivityEntity)entity;
         activityEntities.add(ActivE);
      }
      if (entity instanceof AnimationEntity)
      { //CHECK OVER
         AnimationEntity AnimE = (AnimationEntity)entity;
         animationEntities.add(AnimE);
      }

   }

   public void tryAddEntity(Entity entity)
      {
      if (entity.getPosition().isOccupied(this)) {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      this.addEntity(entity);
   }
   public boolean processLine(String line, ImageStore imageStore) {
      String[] properties = line.split("\\s");
      if (properties.length > 0) {
         switch (properties[PROPERTY_KEY]) {
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
   public void load(Scanner in, ImageStore imageStore)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            if (!this.processLine(in.nextLine(), imageStore))
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

   public boolean parseSgrass(String[] properties, ImageStore imageStore)
   {
      if (properties.length == SGRASS_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[SGRASS_COL]),
                 Integer.parseInt(properties[SGRASS_ROW]));
         Sgrass sgrass = new Sgrass(properties[SGRASS_ID],
                 pt, Integer.parseInt(properties[SGRASS_ACTION_PERIOD]),
                 imageStore.getImageList(SGRASS_KEY));
         this.tryAddEntity(sgrass);
      }

      return properties.length == SGRASS_NUM_PROPERTIES;
   }

   public boolean parseAtlantis(String[] properties, ImageStore imageStore) {
      if (properties.length == ATLANTIS_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[ATLANTIS_COL]),
                 Integer.parseInt(properties[ATLANTIS_ROW]));
         Atlantis atlantis = new Atlantis(properties[ATLANTIS_ID],
                 pt, imageStore.getImageList(ATLANTIS_KEY));
         this.tryAddEntity(atlantis);
      }

      return properties.length == ATLANTIS_NUM_PROPERTIES;
   }

   public boolean parseFish(String[] properties, ImageStore imageStore) {
      if (properties.length == FISH_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[FISH_COL]),
                 Integer.parseInt(properties[FISH_ROW]));
         Fish fish = new Fish(properties[FISH_ID], pt, Integer.parseInt(properties[FISH_ACTION_PERIOD]),
                 imageStore.getImageList(FISH_KEY));
         this.tryAddEntity(fish);
      }

      return properties.length == FISH_NUM_PROPERTIES;
   }

   public boolean parseObstacle(String[] properties, ImageStore imageStore) {
      if (properties.length == OBSTACLE_NUM_PROPERTIES) {
         Point pt = new Point(
                 Integer.parseInt(properties[OBSTACLE_COL]),
                 Integer.parseInt(properties[OBSTACLE_ROW]));
         Obstacle obstacle = new Obstacle(properties[OBSTACLE_ID],
                 pt, imageStore.getImageList(imageStore, OBSTACLE_KEY));
         this.tryAddEntity(obstacle);
      }

      return properties.length == OBSTACLE_NUM_PROPERTIES;
   }

   public boolean parseOcto(String[] properties, ImageStore imageStore) { //CHECK OVER
      if (properties.length == OCTO_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[OCTO_COL]),
                 Integer.parseInt(properties[OCTO_ROW]));
         OctoNotFull octonotfull = new OctoNotFull(properties[OCTO_ID],
                 Integer.parseInt(properties[OCTO_LIMIT]),
                 pt,
                 Integer.parseInt(properties[OCTO_ACTION_PERIOD]),
                 Integer.parseInt(properties[OCTO_ANIMATION_PERIOD]),
                 imageStore.getImageList(OCTO_KEY));
         this.tryAddEntity(octonotfull);
      }

      return properties.length == OCTO_NUM_PROPERTIES;
   }

   public boolean parseBackground(String[] properties, ImageStore imageStore) {
      if (properties.length == BGND_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                 Integer.parseInt(properties[BGND_ROW]));
         String id = properties[BGND_ID];
         this.setBackground(pt, new Background(id, imageStore.getImageList(id)));
      }

      return properties.length == BGND_NUM_PROPERTIES;
   }

}



























































