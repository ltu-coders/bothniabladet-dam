package se.ltucoders.bothniabladetdam.service;


import edu.harvard.hul.ois.fits.Fits;
import edu.harvard.hul.ois.fits.FitsMetadataElement;
import edu.harvard.hul.ois.fits.FitsOutput;
import edu.harvard.hul.ois.fits.exceptions.FitsException;
import edu.harvard.hul.ois.fits.identity.ExternalIdentifier;
import edu.harvard.hul.ois.fits.identity.FitsIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MetadataService {

    private Fits fits;
    DataParsingService dataParsingService;

    @Autowired
    public MetadataService(Fits theFits, DataParsingService dataParsingService) {
            this.fits = theFits;
            this.dataParsingService = dataParsingService;
    }


    public String extractFileName(File file) {
        try {
            FitsOutput fitsOutput = fits.examine(file);
            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("filename");
            if (fitsMetadataElement != null) {
                return fitsMetadataElement.getValue();
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return "File name is not defined";
    }


    public String extractResolution(File file) {
        try {
            FitsOutput fitsOutput = fits.examine(file);
            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("xSamplingFrequency");
            if (fitsMetadataElement != null) {
                return fitsMetadataElement.getValue() + " dpi";
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return "Resolution is not defined";
    }


    public int extractWidth(File file) {
        try {
            FitsOutput fitsOutput = fits.examine(file);
            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("imageWidth");
            if (fitsMetadataElement != null) {
                return Integer.parseInt(fitsMetadataElement.getValue());
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return 0;
    }


    public int extractHeight(File file) {
        try {
            FitsOutput fitsOutput = fits.examine(file);
            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("imageHeight");
            if (fitsMetadataElement != null) {
                return Integer.parseInt(fitsMetadataElement.getValue());
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return 0;
    }


    public String extractSize(File file) {
        try {
            FitsOutput fitsOutput = fits.examine(file);
            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("size");
            if (fitsMetadataElement != null) {
                return fitsMetadataElement.getValue();
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return "Size is not defined.";
    }


    // TODO: Should it return now() or null?
    public LocalDateTime extractDateTime(File file) {
        try {
            FitsOutput fitsOutput = fits.examine(file);

            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("created");
            if (fitsMetadataElement != null) {
                return dataParsingService.parseDateTime(fitsMetadataElement.getValue(),
                        "yyyy:MM:dd HH:mm:ss");

            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return LocalDateTime.now();
    }


    public String extractCameraManufacturer(File file) {
        try {
            FitsOutput fitsOutput = fits.examine(file);
            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("digitalCameraManufacturer");
            if (fitsMetadataElement != null) {
                return fitsMetadataElement.getValue();
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return "Camera manufacturer is not defined.";
    }


    //digitalCameraModelName
    public String extractCameraModelName(File file) {
        try {
            FitsOutput fitsOutput = fits.examine(file);
            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("digitalCameraModelName");
            if (fitsMetadataElement != null) {
                return fitsMetadataElement.getValue();
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return "Camera model is not defined.";
    }


    public String extractProducer(File file) {
        try {
            FitsOutput fitsOutput = fits.examine(file);
            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("imageProducer");
            if (fitsMetadataElement != null) {
                return fitsMetadataElement.getValue();
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }

        return "Image producer is not defined.";
    }


    public String extractLocation(File file) {
        try {
            FitsOutput fitsOutput = fits.examine(file);
            FitsMetadataElement gpsLatitude = fitsOutput.getMetadataElement("gpsLatitude");
            FitsMetadataElement gpsLongitude = fitsOutput.getMetadataElement("gpsLongitude");
            if (gpsLatitude != null && gpsLongitude != null) {
                String gpsLocation = gpsLatitude.getValue() + " " + gpsLongitude.getValue();
                return gpsLocation;
            }
        } catch (FitsException ex) {
            ex.printStackTrace();
        }
        return "GPS location is not defined.";
    }


    public String extrcatPronom(File file) {
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
