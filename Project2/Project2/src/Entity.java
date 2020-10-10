import processing.core.PImage;

import java.util.List;
import java.util.Optional;

/*
Entity ideally would includes functions for how all the entities in our virtual world might act...
 */


interface Entity {

   Point getPosition();
   void setPosition(Point newPosition);
   PImage getCurrentImage();

}


