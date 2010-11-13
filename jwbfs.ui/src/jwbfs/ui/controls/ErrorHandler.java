package jwbfs.ui.controls;

import jwbfs.ui.exceptions.WBFSException;

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
		      
	    	  if(ErrorHandler.fileExist(line)){
		    	  
		    	  throw new WBFSException(line,WBFSException.FILE_EXISTS);
		    	  
		      }
		      
	    	  throw new WBFSException(line);
	    	  
	      }

	}

	private static boolean fileExist(String line) {
		if(line.toLowerCase().contains("exists")){
			return true;
		}
		
		return false;
	}

}
