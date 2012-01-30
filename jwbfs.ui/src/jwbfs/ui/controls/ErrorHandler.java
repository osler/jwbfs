package jwbfs.ui.controls;

import java.io.File;

import jwbfs.ui.exceptions.NotCorrectDiscFormatException;
import jwbfs.ui.exceptions.WBFSException;
import jwbfs.ui.exceptions.WBFSFileExistsException;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.operation.IRunnableWithProgress;

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

	public static void processError(String line, IRunnableWithProgress operation) throws WBFSException, WBFSFileExistsException{

	      if(ErrorHandler.lineHasError(line)){
		      
	    	  if(ErrorHandler.fileExist(line)){
		    	
	    		  String filePath = line.split("exists:")[1].trim();;
	    		  File file = new File(filePath);
	    		  
		    	  throw new WBFSFileExistsException(line,file,operation);
		      }
    
	    	  throw new WBFSException(line);
	    	  
	      }

	}

	public static void processError(String line) throws WBFSException, WBFSFileExistsException{

	      if(ErrorHandler.lineHasError(line)){
		      
	    	  if(ErrorHandler.fileExist(line)){
		    	
	    		  String filePath = line.split("exists:")[1].trim();;
	    		  File file = new File(filePath);
	    		  
		    	  throw new WBFSFileExistsException(line,file);
		      }
  
	    	  throw new WBFSException(line);
	    	  
	      }

	}
	
	public static void processError(String line, Job operation) throws WBFSException, WBFSFileExistsException, NotCorrectDiscFormatException{

	      if(ErrorHandler.lineHasError(line)){
		      
	    	  if(ErrorHandler.fileExist(line)){
		    	
	    		  String filePath = line.split("exists:")[1].trim();;
	    		  File file = new File(filePath);
	    		  
		    	  throw new WBFSFileExistsException(line,file,operation);
		      }
	    	  
	    	  if(ErrorHandler.wrongFormat(line)){
	    		  throw new NotCorrectDiscFormatException();
		      }
	    	  
	    	  
  
	    	  throw new WBFSException(line);
	    	  
	      }

	}

	private static boolean wrongFormat(String line) {
		if(line.toLowerCase().contains("not a wii disc:")){
			return true;
		}
		
		if(line.toLowerCase().contains("converting")){
			return true;
		}
		
		return false;
	}
	
	private static boolean fileExist(String line) {
		if(line.toLowerCase().contains("file already exists")){
			return true;
		}
		
		return false;
	}

}
