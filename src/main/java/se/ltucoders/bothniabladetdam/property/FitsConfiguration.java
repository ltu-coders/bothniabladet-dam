package se.ltucoders.bothniabladetdam.property;

import edu.harvard.hul.ois.fits.Fits;
import edu.harvard.hul.ois.fits.exceptions.FitsConfigurationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FitsConfiguration {

    @Bean
    public Fits fits() {
        try {
            return new Fits("C:\\Users\\fredr\\Downloads\\fits-1.4.1\\");

        } catch (FitsConfigurationException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
