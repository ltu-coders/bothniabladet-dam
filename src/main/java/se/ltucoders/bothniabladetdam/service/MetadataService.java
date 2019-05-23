package se.ltucoders.bothniabladetdam.service;


import edu.harvard.hul.ois.fits.Fits;
import edu.harvard.hul.ois.fits.FitsMetadataElement;
import edu.harvard.hul.ois.fits.FitsOutput;
import edu.harvard.hul.ois.fits.exceptions.FitsException;
import edu.harvard.hul.ois.fits.identity.ExternalIdentifier;
import edu.harvard.hul.ois.fits.identity.FitsIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ltucoders.bothniabladetdam.db.entity.Image;

import java.io.File;
import java.time.LocalDateTime;

@Service
public class MetadataService {

    private Fits fits;
    private DataParsingService dataParsingService;
    private GpsLocationService gpsLocationService;

    @Autowired
    public MetadataService(Fits fits, DataParsingService dataParsingService, GpsLocationService gpsLocationService) {
            this.fits = fits;
            this.dataParsingService = dataParsingService;
            this.gpsLocationService = gpsLocationService;
    }

    void extractMetadata(Image image, File theFile) {
        FitsOutput fitsOutput;
        try {
            fitsOutput = fits.examine(theFile);
        } catch (FitsException e) {
            e.printStackTrace();
            return;
        }

        extractResolution(image, fitsOutput);

        extractWidth(image, fitsOutput);

        extractHeight(image, fitsOutput);

        extractFileSize(image, fitsOutput);

        extractDateTime(image, fitsOutput);

        extractMake(image, fitsOutput);

        extractModel(image, fitsOutput);

        extractLocation(image, fitsOutput);

        extractPuid(image, fitsOutput);
    }

    private void extractPuid(Image image, FitsOutput fitsOutput) {
        for (FitsIdentity fitsIdentity : fitsOutput.getIdentities()) {
            for (ExternalIdentifier externalIdentifier : fitsIdentity.getExternalIdentifiers()) {
                if (externalIdentifier.getName().equals("puid")) {
                    image.setPuid(externalIdentifier.getValue());
                    return;
                }
            }
        }
        image.setPuid("Pronom is not defined.");
    }

    private void extractLocation(Image image, FitsOutput fitsOutput) {
        FitsMetadataElement element;
        element = fitsOutput.getMetadataElement("gpsLongitude");
        String gpsLongitude = element != null ? element.getValue() : "";
        element = fitsOutput.getMetadataElement("gpsLatitude");
        String gpsLatitude = element != null ? element.getValue() : "";
        image.setLocation(gpsLocationService.getLocation(gpsLatitude.replace("+", ""),
                gpsLongitude.replace("+", "")));
    }

    private void extractModel(Image image, FitsOutput fitsOutput) {
        FitsMetadataElement element;
        element = fitsOutput.getMetadataElement("digitalCameraModelName");
        image.setModel(element != null ? element.getValue() : "Camera model is not defined.");
    }

    private void extractMake(Image image, FitsOutput fitsOutput) {
        FitsMetadataElement element;
        element = fitsOutput.getMetadataElement("digitalCameraManufacturer");
        image.setMake( element != null ? element.getValue() : "Camera manufacturer is not defined.");
    }

    private void extractDateTime(Image image, FitsOutput fitsOutput) {
        FitsMetadataElement element;
        element = fitsOutput.getMetadataElement("created");
        image.setDateTime(element != null
                ? dataParsingService.parseDateTime(element.getValue(), "yyyy:MM:dd HH:mm:ss")
                : LocalDateTime.of(1970, 1,1,0,0));
    }

    private void extractFileSize(Image image, FitsOutput fitsOutput) {
        FitsMetadataElement element;
        element = fitsOutput.getMetadataElement("size");
        image.setFileSize(element != null ? element.getValue() : "Size is not defined.");
    }

    private void extractHeight(Image image, FitsOutput fitsOutput) {
        FitsMetadataElement element;
        element = fitsOutput.getMetadataElement("imageHeight");
        image.setHeight(element != null ? Integer.parseInt(element.getValue()) : 0);
    }

    private void extractWidth(Image image, FitsOutput fitsOutput) {
        FitsMetadataElement element;
        element = fitsOutput.getMetadataElement("imageWidth");
        image.setWidth(element != null ? Integer.parseInt(element.getValue()) : 0);
    }

    private void extractResolution(Image image, FitsOutput fitsOutput) {
        FitsMetadataElement element = fitsOutput.getMetadataElement("xSamplingFrequency");
        image.setResolution(element != null ? element.getValue() + " dpi" : "");
    }
} // End class
