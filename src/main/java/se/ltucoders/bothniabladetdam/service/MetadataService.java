package se.ltucoders.bothniabladetdam.service;


import edu.harvard.hul.ois.fits.Fits;
import edu.harvard.hul.ois.fits.FitsMetadataElement;
import edu.harvard.hul.ois.fits.FitsOutput;
import edu.harvard.hul.ois.fits.exceptions.FitsConfigurationException;
import edu.harvard.hul.ois.fits.exceptions.FitsException;
import edu.harvard.hul.ois.fits.identity.ExternalIdentifier;
import edu.harvard.hul.ois.fits.identity.FitsIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MetadataService {

    private Fits fits;

    @Autowired
    public MetadataService(Fits theFits) {
            this.fits = theFits;
    }


    public String extractFileName(File file) {
        try {
            FitsOutput fitsOutput = fits.examine(file);
            FitsMetadataElement resolution = fitsOutput.getMetadataElement("filename");
            if (resolution != null) {
                return resolution.getValue();
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return "File name is not defined";
    }


    public String extractResolution(File file) {
        try {
            FitsOutput fitsOutput = fits.examine(file);
            FitsMetadataElement resolution = fitsOutput.getMetadataElement("xSamplingFrequency");
            if (resolution != null) {
                return resolution.getValue() + " dpi";
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return "Resolution is not defined";
    }


    public int extractWidth(File file) {
        try {
            FitsOutput fitsOutput = fits.examine(file);
            FitsMetadataElement width = fitsOutput.getMetadataElement("imageWidth");
            if (width != null) {
                return Integer.parseInt(width.getValue());
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return 0;
    }


    public int extractHeight(File file) {
        try {
            FitsOutput fitsOutput = fits.examine(file);
            FitsMetadataElement height = fitsOutput.getMetadataElement("imageHeight");
            if (height != null) {
                return Integer.parseInt(height.getValue());
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return 0;
    }


    public String extractSize(File file) {
        try {
            FitsOutput fileSize = fits.examine(file);
            FitsMetadataElement size = fileSize.getMetadataElement("size");
            if (size != null) {
                return size.getValue();
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return "Size is not defined.";
    }


    public LocalDateTime extractDateTime(File file) {
        try {
            FitsOutput fitsOutput = fits.examine(file);
            FitsMetadataElement dateTime = fitsOutput.getMetadataElement("created");
            if (dateTime != null) {
                // TODO: move the pattern to the properties file
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
                return LocalDateTime.parse(dateTime.getValue(), formatter);
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return LocalDateTime.now();
    }


    public String extractCameraManufacturer(File file) {
        try {
            FitsOutput fileSize = fits.examine(file);
            FitsMetadataElement cameraManufacturer = fileSize.getMetadataElement("digitalCameraManufacturer");
            if (cameraManufacturer != null) {
                return cameraManufacturer.getValue();
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return "Camera manufacturer is not defined.";
    }


    //digitalCameraModelName
    public String extractCameraModelName(File file) {
        try {
            FitsOutput fileSize = fits.examine(file);
            FitsMetadataElement cameraModelName = fileSize.getMetadataElement("digitalCameraModelName");
            if (cameraModelName != null) {
                return cameraModelName.getValue();
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return "Camera model is not defined.";
    }


    public String extractProducer(File file) {
        try {
            FitsOutput fileSize = fits.examine(file);
            FitsMetadataElement producer = fileSize.getMetadataElement("imageProducer");
            if (producer != null) {
                return producer.getValue();
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }

        return "Image producer is not defined.";
    }


    public String extractLocation(File file) {
        try {
            FitsOutput fileSize = fits.examine(file);
            FitsMetadataElement gpsLatitude = fileSize.getMetadataElement("gpsLatitude");
            FitsMetadataElement gpsLongitude = fileSize.getMetadataElement("gpsLongitude");
            if (gpsLatitude != null && gpsLongitude != null) {
                String gpsLocation = gpsLatitude.getValue() + " " + gpsLongitude.getValue();
                return gpsLocation;
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return "GPS location is not defined.";
    }


    public String extrcatPronom(File file) throws FitsException {
        try {
            FitsOutput fitsOutput = fits.examine(file);
            List<FitsIdentity> fitsIdentityList = fitsOutput.getIdentities();
            for (FitsIdentity fitsIdentity : fitsIdentityList) {
                List<ExternalIdentifier> externalIdentifierList = fitsIdentity.getExternalIdentifiers();
                for (ExternalIdentifier externalIdentifier : externalIdentifierList) {
                    if (externalIdentifier.getName().equals("puid")) {
                        return externalIdentifier.getValue();
                    }
                }
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return "Pronom is not defined.";
    }


} // End class
