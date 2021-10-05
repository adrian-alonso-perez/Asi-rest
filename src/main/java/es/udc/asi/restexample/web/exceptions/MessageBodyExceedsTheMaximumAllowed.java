package es.udc.asi.restexample.web.exceptions;



public class MessageBodyExceedsTheMaximumAllowed extends ResourceException {



  public MessageBodyExceedsTheMaximumAllowed() {

    super("The post created exceeds the maximum allowed");

  }

}
