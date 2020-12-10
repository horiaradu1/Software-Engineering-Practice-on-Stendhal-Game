package games.stendhal.client.actions;

import java.util.List;
import java.util.Map;

import games.stendhal.client.ClientSingletonRepository;
import marauroa.common.game.RPAction;

public class BaseAction implements SlashAction {
	
	private int minimumParameters;
	private int maximumParameters;
	
	private boolean remainderRequired = false;
	private boolean remainderNonEmpty = false;
	private boolean remainderNotNull = false;
	
	private boolean paramsNotNull = false;
	private boolean paramsMinLength = false;
	
	private Map<String, String> parameters, staticParameters;
	private List<String> aliases;
	
	protected BaseAction(int minParams, 
	                     int maxParams, 
	                     Map<String, String> parameters, 
	                     Map<String, String> staticParameters,
	                     List<String> aliases
			) {
		minimumParameters = minParams;
		maximumParameters = maxParams;
		this.parameters = parameters;
		this.staticParameters = staticParameters;
		this.aliases = aliases;
	}

	@Override
	public boolean execute(String[] params, String remainder) {
		
		if (remainderNotNull && remainder == null) {
			return false;
		}
		
		if (remainderRequired && remainder.isEmpty()) {
			return false;
		}
		
		if (paramsNotNull && params == null) {
			return false;
		}
		
		if (paramsMinLength && params.length < minimumParameters) {
			return false;
		}
		
		final RPAction action = new RPAction();
		
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			 String key = entry.getKey();
			 String value = entry.getValue();

			 if (value.equals("remainder")) {
                 if (!(remainderNonEmpty && remainder.isEmpty())) {
                     action.put(key, remainder);
                 }
             } else {
                 int index = Integer.parseInt(value);
                 if (index >= minimumParameters) {
                     if ((params.length > index) && (params[index] != null)) {
                         action.put(key, params[index]);
                     }
                 } else {
                     action.put(key, params[index]);
                 }
             }
		}
		
		for (Map.Entry<String, String> entry : staticParameters.entrySet()) {
			 String key = entry.getKey();
			 String value = entry.getValue();

			 action.put(key, value);
		}


		ClientSingletonRepository.getClientFramework().send(action);

		return true;
	}
	
	

	@Override
	public int getMaximumParameters() {
		return maximumParameters;
	}

	@Override
	public int getMinimumParameters() {
		return minimumParameters;
	}
	
	public void setRemainderRequired(boolean b) {
		remainderRequired = b;
	}
	
	public void setRemainderNonEmpty(boolean b) {
		remainderNonEmpty = b;
	}
	
	public void setRemainderNotNull(boolean b) {
		remainderNotNull = b;
	}
	
	public void setParamsNotNull(boolean b) {
		paramsNotNull = b;
	}
	
	public void setParamsMinLength(boolean b) {
		paramsMinLength = b;
	}
	
	public List<String> getAliases(){
		return aliases;
	}
}
