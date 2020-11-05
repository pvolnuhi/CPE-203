/*
Entity ideally would includes functions for how all the entities in our virtual world might act...
 */

import processing.core.PImage;

import java.util.List;

abstract class Entity {

   private final String id;
   private Point position;
   private List<PImage> images;

   public Entity(String id, Point position, List<PImage> images) {
      this.id = id;
      this.position = position;
      this.images = images;

   }

   public String getId(){
      return id;
   }

   public Point getPosition(){
      return position;
   }

   public void setPosition(Point newPosition){
      position = newPosition;

   }

   public PImage getCurrentImage(){
      return images.get(0); //check
   }

   public List<PImage> getImages(){
      return images;
   }


}


