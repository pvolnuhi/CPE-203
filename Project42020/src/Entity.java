/*
Entity ideally would includes functions for how all the entities in our virtual world might act...
 */

import processing.core.PImage;
import processing.core.PImage;

import java.util.ArrayDeque;
import java.util.Random;


import java.util.List;

abstract class Entity implements Entitys {

   protected final String id;
   protected List<PImage> images;
   //private Point position;

   protected Point position;

   protected static final int CRAB_PERIOD_SCALE = 4;
   protected static final int CRAB_ANIMATION_MIN = 50;
   protected static final int CRAB_ANIMATION_MAX = 150;
   protected static final String CRAB_ID_SUFFIX = " -- crab";
   protected static final String CRAB_KEY = "crab";
   protected static final String QUAKE_KEY = "quake";
   protected static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;

   protected static final int FISH_CORRUPT_MIN = 20000;
   protected static final int FISH_CORRUPT_MAX = 30000;

   protected static final String FISH_ID_PREFIX = "fish -- ";

   protected static final String FISH_KEY = "fish";

   protected int imageIndex;

   protected static final Random rand = new Random();

   public Entity(Point position, List<PImage> images,String id){
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
      this.id = id;
   }




   public void setposition(Point position){this.position = position;}
   public Point getposition(){return position;}
   public int getimageIndex(){return imageIndex;}
   public List<PImage> getimages(){return images;}
   public PImage getCurrentImage(){return this.getimages().get(this.getimageIndex());

   }
}