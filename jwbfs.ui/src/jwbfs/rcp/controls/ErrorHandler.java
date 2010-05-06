package jwbfs.rcp.controls;

public class ErrorHandler {

	public static boolean lineHasError(String line) {
	
		if(line.toLowerCase().contains("not a wii disc: Success")){
			return true;
		}

		if(line.toLowerCase().contains("error")){
			return true;
		}

		return false;
	}

	public static void processError(String line) throws WBFSException{

	      if(ErrorHandler.lineHasError(line)){
	    	  
	    	  throw new WBFSException(line);
	    	  
	      }

	}

}
