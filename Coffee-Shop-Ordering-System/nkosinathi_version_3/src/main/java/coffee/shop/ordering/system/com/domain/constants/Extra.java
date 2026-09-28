package coffee.shop.ordering.sytem.com.domain.constants;

public enum Extra {

  EXTRA_SHOT("extra shot"),
  OAT_MILK("oat milk"),
  ALMOND_MILK("almond milk"),
  SOY_MILK("soy milk"),       // was "soy_mil"
  DECAF("decaf"),             // was "defac"
  EXTRA_LARGE_SIZE("extra large");

  private final String name;

  Extra(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
