package come.rest.menuforyou.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ParameterMap {

	private static final String DISPLAY_FEEDBACK = "DISPLAY_FEEDBACK";

	public static final Map<String, String> initConfiguration;
	static {
		Map<String, String> aMap = new HashMap<String, String>();
		aMap.put(DISPLAY_FEEDBACK, "true");
		initConfiguration = Collections.unmodifiableMap(aMap);
	}

	public Map<String, String> loadedConfiguration;

}
