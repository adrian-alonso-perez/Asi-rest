
package es.udc.asi.restexample.web.exceptions;

public class TitlePostExceedsTheMaximumAllowed extends ResourceException{

  public TitlePostExceedsTheMaximumAllowed(String title){
    super("The post created with "+title+ " exceeds the maximum allowed");
  }
}
