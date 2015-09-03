package come.rest.menuforyou.util;

import java.util.List;

import com.rest.menuforyou.domain.Typedish;

public class MenuInMemory {

	private final List<Typedish> typedishes;

	public List<Typedish> getTypedishes() {
		return typedishes;
	}

	public MenuInMemory(List<Typedish> typedishes) {
		super();
		this.typedishes = typedishes;
	}

}
