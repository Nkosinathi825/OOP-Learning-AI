package coffee.shop.ordering.system.com.domain.constants;

public enum Extra {

  EXTRA_SHOT("extra_shot"),
  OAT_MILK("oat_milk"),
  ALMOND_MILK("almond_milk"),
  SOY_MILK("soy_mil"),
  DECAF("defac"),
  EXTRA_LARGE_SIZE("extra_large_size");

  private final String name;
  Extra(String name){
    this.name = name;
  }

  public String getName(){
    return this.name;
  }
}
