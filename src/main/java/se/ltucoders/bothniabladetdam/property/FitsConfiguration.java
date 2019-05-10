package se.ltucoders.bothniabladetdam.property;

import edu.harvard.hul.ois.fits.Fits;
import edu.harvard.hul.ois.fits.exceptions.FitsConfigurationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FitsConfiguration {

    @Value("${fits.path}")
    private String pathToFits;

    @Bean
    public Fits fits() {
        try {
            return new Fits(pathToFits);

        } catch (FitsConfigurationException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
