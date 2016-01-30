package utils;

public class AWSUtils {

    int measurementInterval;
    String region;
    String clientID;
    String caPath;
    String certPath;
    String keyPath;
    String awsScript;

    public AWSUtils() {

        // Read property file
        PropertyFileReader reader = new PropertyFileReader("device.properties");

        keyPath = reader.getPropertyValue(reader.KEY_PATH);
        certPath = reader.getPropertyValue(reader.CERT_PATH);
        caPath = reader.getPropertyValue(reader.CA_PATH);
        clientID = reader.getPropertyValue(reader.CLIENT_ID);
        region = reader.getPropertyValue(reader.REGION);
        measurementInterval = Integer.parseInt(reader.getPropertyValue(reader.MEASUREMENT_INTERVAL));
        awsScript = reader.getPropertyValue(reader.AWS_SCRIPT);
    }

    public void sendDataToAWS(double temperature, double humidity, double pressure, double luminosity, double spl) {
        
        
        System.out.println(executeCommand(temperature, humidity, pressure, luminosity, spl));

    }

    private int executeCommand(double temperature, double humidity, double pressure, double luminosity, double spl) {

        String command = "node " + awsScript + " " + keyPath + " " + certPath + " " + caPath + " " + clientID + " " + region
                         + " " +temperature+ " " +humidity+ " " +pressure+ " " +luminosity+ " " +spl;
        ProcessBuilder pb = new ProcessBuilder(command.split(" "));
        try {
            Process p = pb.start();
            p.waitFor();
            return p.exitValue();

        } catch (Exception ex) {

            System.out.println(ex);
            ex.printStackTrace();
            return 1;

        }

    }

}
