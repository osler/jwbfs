package jwbfs.model.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedHashMap;

import jwbfs.model.IContainsModel;
import jwbfs.model.Model;

public abstract class ModelObject implements PropertyChangeListener,IContainsModel {
	
	public static final String  INDEX = "modelObject";
	
	protected abstract ModelObject getBean();
	
	protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	public void addPropertyChangeListener(
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener( listener);
	}
	
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println(evt.getPropertyName() + " changed to "+evt.getNewValue());
//		System.out.println("Writing settings to ini file...");
		//TODO
	}

	@Override
	public LinkedHashMap<String, Object> getModel() {
		return Model.getBeans();
		
	}

}
