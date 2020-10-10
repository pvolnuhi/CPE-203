import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import processing.core.PImage;

final class Background {
   private String id;
   private List<PImage> images;
   private int imageIndex;

   public Background(String id, List<PImage> images) {
      this.id = id;
      this.images = images;
   }

   public PImage getCurrentImage() {
      return images.get(imageIndex);
   }
}