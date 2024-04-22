
class Auto {
    private String brand;
    private float fuelUsage;
    public float getFuelUsage() {
        return fuelUsage;
    }

    Auto (String brand, float fuelUsage) {
        this.brand = brand;
        this.fuelUsage = fuelUsage;
    }
    public static void main(String[] args) {

        List < Auto > autos = new ArrayList < > ();
        Auto bmw = new Auto("BMW" ,10);
        Auto vw = new Auto("Volkswagen", 6, "Error here");   //ERROR IS HERE -> Constructor is defined with 2 variables
        Auto porsche = new Auto("Porsche", 16);
        Auto mercedes = new Auto("Mercedes - Benz", 14);
        Auto tesla = new Auto("Tesla", 1);
        autos.add(bmw);
        autos.add(vw);
        autos.add(porsche);
        autos.add(mercedes);
        autos.add(tesla);
        autos.add(tesla);
        for (Auto auto : autos) {
            System.out.println(auto);
        }
        Scanner keyboard = new Scanner(System.in);
        float desiderEconomy = keyboard.nextFloat();
        for (Auto auto : autos) {
            float x = auto.getFuelUsage();
            if (x < desiderEconomy) {
                System.out.println(auto);
            }
        }
    }
}
