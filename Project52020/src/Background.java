import processing.core.PImage;

import java.util.List;

final class Background {
   private String id;
   private List<PImage> images;
   private int imageIndex;

   public Background(String id, List<PImage> images)
   {
      this.id = id;
      this.images = images;
   }

   public int getimageIndex(){
      return imageIndex;
   }

   public List<PImage> getimages(){
      return images;
   }

   public PImage getCurrentImage(){
      return this.getimages()
              .get(this.getimageIndex());
   }
}

